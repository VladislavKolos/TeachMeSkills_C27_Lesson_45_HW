package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.util.PostgresUtil;
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

    /**
     * Transfers the specified amount from one card to another.
     *
     * @param fromCardNumber sender's card number
     * @param toCardNumber   recipient's card number
     * @param amount         transfer amount
     * @return final balance of the sender's card after the transfer
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public BigDecimal transferAmount(long fromCardNumber, long toCardNumber, BigDecimal amount) throws SQLException {
        Connection connection = postgresUtil.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatementSelectBalanceBeforeTransfer = null;
        PreparedStatement preparedStatementUpdateFromAccount = null;
        PreparedStatement preparedStatementUpdateToAccount = null;
        PreparedStatement preparedStatementSelectBalanceAfterTransfer = null;
        ResultSet resultSet = null;
        ResultSet finalResultSet = null;

        BigDecimal finalBalance = null;

        String sqlQuerySelectBalance = "SELECT balance FROM account WHERE id = (SELECT account_id FROM card WHERE card_number = ?)";
        String sqlQueryUpdateFromAccount = "UPDATE account SET balance = balance - ? WHERE id = (SELECT account_id FROM card  WHERE card_number = ?)";
        String sqlQueryUpdateToAccount = "UPDATE account SET balance = balance + ? WHERE id = (SELECT account_id FROM card  WHERE card_number = ?)";

        try {
            preparedStatementSelectBalanceBeforeTransfer = connection.prepareStatement(sqlQuerySelectBalance);
            preparedStatementSelectBalanceBeforeTransfer.setLong(1, fromCardNumber);

            resultSet = preparedStatementSelectBalanceBeforeTransfer.executeQuery();

            if (resultSet.next()) {
                BigDecimal currentBalance = resultSet.getBigDecimal("balance");
                if (currentBalance.compareTo(amount) < 0) {
                    throw new SQLException("Insufficient funds");
                }

                preparedStatementUpdateFromAccount = connection.prepareStatement(sqlQueryUpdateFromAccount);
                preparedStatementUpdateFromAccount.setBigDecimal(1, amount);
                preparedStatementUpdateFromAccount.setLong(2, fromCardNumber);
                preparedStatementUpdateFromAccount.executeUpdate();

                preparedStatementUpdateToAccount = connection.prepareStatement(sqlQueryUpdateToAccount);
                preparedStatementUpdateToAccount.setBigDecimal(1, amount);
                preparedStatementUpdateToAccount.setLong(2, toCardNumber);
                preparedStatementUpdateToAccount.executeUpdate();
            }
            preparedStatementSelectBalanceAfterTransfer = connection.prepareStatement(sqlQuerySelectBalance);
            preparedStatementSelectBalanceAfterTransfer.setLong(1, fromCardNumber);

            finalResultSet = preparedStatementSelectBalanceAfterTransfer.executeQuery();

            if (finalResultSet.next()) {
                finalBalance = finalResultSet.getBigDecimal("balance");
            }
            connection.commit();

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

            if (preparedStatementSelectBalanceAfterTransfer != null) {
                preparedStatementSelectBalanceAfterTransfer.close();
            }

            if (preparedStatementUpdateToAccount != null) {
                preparedStatementUpdateToAccount.close();
            }

            if (preparedStatementUpdateFromAccount != null) {
                preparedStatementUpdateFromAccount.close();
            }

            if (preparedStatementSelectBalanceBeforeTransfer != null) {
                preparedStatementSelectBalanceBeforeTransfer.close();
            }

            connection.setAutoCommit(true);
            connection.close();
        }
        if (finalBalance == null) {
            throw new SQLException("Check that the information you entered is correct");
        }
        return finalBalance;
    }
}
