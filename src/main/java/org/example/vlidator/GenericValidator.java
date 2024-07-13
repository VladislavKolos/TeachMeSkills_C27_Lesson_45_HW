package org.example.vlidator;

import lombok.RequiredArgsConstructor;
import org.example.util.PostgresUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Validator for checking the existence of various entities in the database.
 */
@Component
@RequiredArgsConstructor
public class GenericValidator {
    private final PostgresUtil postgresUtil;

    /**
     * Checks whether a client with the given ID exists.
     * @param id client ID
     * @return true if the client exists, false otherwise
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public boolean isClientExists(int id) throws SQLException {
        String sqlQuery = "SELECT COUNT(*) FROM client WHERE id = ?";

        return isIDExists(sqlQuery, id);
    }

    /**
     * Checks whether a card with the given ID exists.
     * @param id card ID
     * @return true if the card exists, false otherwise
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public boolean isCardExistsByID(int id) throws SQLException {
        String sqlQuery = "SELECT COUNT(*) FROM card WHERE id = ?";

        return isIDExists(sqlQuery, id);
    }

    /**
     * Checks whether an account with the given ID exists.
     * @param id account ID
     * @return true if the account exists, false otherwise
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public boolean isAccountExists(int id) throws SQLException {
        String sqlQuery = "SELECT COUNT(*) FROM account WHERE id = ?";

        return isIDExists(sqlQuery, id);
    }

    /**
     * Checks whether a card with the given card number exists.
     * @param cardNumber card number
     * @return true if the card exists, false otherwise
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public boolean isCardExistsByCardNumber(String cardNumber) throws SQLException {
        String sqlQuery = "SELECT COUNT(*) FROM card WHERE card_number = ?";

        return isCardNumberExists(sqlQuery, cardNumber);
    }

    /**
     * Checks whether an entity with a given ID exists by executing the specified SQL-query.
     * @param sqlQuery SQL-query to check the existence of an entity
     * @param id entity ID
     * @return true if the entity exists, false otherwise
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    private boolean isIDExists(String sqlQuery, int id) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        boolean exist = false;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exist = resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception!!!");

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return exist;
    }

    /**
     * Checks whether a card with the given card number exists by executing the specified SQL-query.
     * @param sqlQuery SQL-query to check the existence of a card
     * @param cardNumber card number
     * @return true if the card exists, false otherwise
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    private boolean isCardNumberExists(String sqlQuery, String cardNumber) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        boolean exist = false;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, cardNumber);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exist = resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception!!!");

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return exist;
    }
}
