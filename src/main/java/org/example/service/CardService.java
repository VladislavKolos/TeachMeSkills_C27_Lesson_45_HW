package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Account;
import org.example.model.Card;
import org.example.model.Client;
import org.example.util.PostgresUtil;
import org.example.vlidator.GenericValidator;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for working with cards.
 */
@Service
@RequiredArgsConstructor
public class CardService {
    private final PostgresUtil postgresUtil;
    private final GenericValidator genericValidator;

    /**
     * Returns a card by ID.
     * @param id card ID
     * @return card object
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public Card getCardByID(int id) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Card card = null;

        String sqlQuery = "SELECT * FROM card WHERE id = ?";

        try {
            if (genericValidator.isCardExistsByID(id)) {
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    card = Card.builder()
                            .id(resultSet.getInt("id"))
                            .cardNumber(resultSet.getString("cardNumber"))
                            .account(Account.builder().id(resultSet.getInt("accountId")).build())
                            .client(Client.builder().id(resultSet.getInt("clientId")).build())
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception");
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
        return card;
    }

    /**
     * Returns a list of cards by account ID.
     * @param accountId account ID
     * @return list of card objects
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public List<Card> getCardsByAccountID(int accountId) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Card> cards = new ArrayList<>();

        String sqlQuery = "SELECT * FROM card WHERE account_id = ?";

        try {
            if (genericValidator.isAccountExists(accountId)) {
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, accountId);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Card card = Card.builder()
                            .id(resultSet.getInt("id"))
                            .cardNumber(resultSet.getString("cardNumber"))
                            .account(Account.builder().id(resultSet.getInt("accountId")).build())
                            .client(Client.builder().id(resultSet.getInt("clientId")).build())
                            .build();
                    cards.add(card);
                }
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
        return cards;
    }

}
