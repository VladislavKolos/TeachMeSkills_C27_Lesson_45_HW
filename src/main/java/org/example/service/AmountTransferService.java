package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.util.PostgresUtil;
import org.example.vlidator.GenericValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service for transferring funds between accounts using card numbers.
 */
@Service
@RequiredArgsConstructor
public class AmountTransferService {
    private final PostgresUtil postgresUtil;
    private final GenericValidator genericValidator;

    /**
     * Transfers the specified amount from one card to another.
     * @param fromCardNumber sender's card number
     * @param toCardNumber recipient's card number
     * @param amount transfer amount
     * @return final balance of the sender's card after the transfer
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public BigDecimal transferAmount(String fromCardNumber, String toCardNumber, BigDecimal amount) throws SQLException {
        Connection connection = postgresUtil.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        ResultSet resultSet = null;
        ResultSet finalResultSet = null;

        BigDecimal finalBalance = null;

        String sqlQuerySelectBalance = "SELECT balance FROM account WHERE id = (SELECT account_id FROM card WHERE card_number = ?)";
        String sqlQueryUpdateAccount = "UPDATE account SET balance = balance - ? WHERE id = (SELECT account_id FROM card  WHERE card_number = ?)";

        try {
            if (genericValidator.isCardExistsByCardNumber(fromCardNumber)
                    && genericValidator.isCardExistsByCardNumber(toCardNumber)) {
                preparedStatement1 = connection.prepareStatement(sqlQuerySelectBalance);
                preparedStatement1.setString(1, fromCardNumber);

                resultSet = preparedStatement1.executeQuery();

                if (resultSet.next()) {
                    BigDecimal currentBalance = resultSet.getBigDecimal("balance");
                    if (currentBalance.compareTo(amount) < 0) {
                        throw new SQLException("Insufficient funds");
                    }

                    preparedStatement2 = connection.prepareStatement(sqlQueryUpdateAccount);
                    preparedStatement2.setBigDecimal(1, amount);
                    preparedStatement2.setString(2, fromCardNumber);
                    preparedStatement2.executeUpdate();

                    preparedStatement3 = connection.prepareStatement(sqlQueryUpdateAccount);
                    preparedStatement3.setBigDecimal(1, amount);
                    preparedStatement3.setString(2, toCardNumber);
                    preparedStatement3.executeUpdate();
                }
                preparedStatement4 = connection.prepareStatement(sqlQuerySelectBalance);
                preparedStatement4.setString(1, fromCardNumber);

                finalResultSet = preparedStatement4.executeQuery();

                if (finalResultSet.next()) {
                    finalBalance = finalResultSet.getBigDecimal("balance");
                }
                connection.commit();
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            System.out.println("SQL Exception!!!");

        } finally {
            if (finalResultSet != null) {
                finalResultSet.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement4 != null) {
                preparedStatement4.close();
            }

            if (preparedStatement3 != null) {
                preparedStatement3.close();
            }

            if (preparedStatement2 != null) {
                preparedStatement2.close();
            }

            if (preparedStatement1 != null) {
                preparedStatement1.close();
            }

            connection.setAutoCommit(true);
            connection.close();
        }
        return finalBalance;
    }
}
