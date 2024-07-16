package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Account;
import org.example.model.Card;
import org.example.model.Client;
import org.example.util.PostgresUtil;
import org.example.vlidator.ModelValidator;
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
    private final ModelValidator genericValidator;

    /**
     * Returns a card by ID.
     *
     * @param id card ID
     * @return card object
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public Card getCardByID(int id) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Card card = null;

        String sqlQuerySelectCard = "SELECT * FROM card WHERE id = ?";

        try {
            if (genericValidator.isCardExistsByID(id)) {
                preparedStatement = connection.prepareStatement(sqlQuerySelectCard);
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    card = Card.builder()
                            .id(resultSet.getInt("id"))
                            .cardNumber(resultSet.getInt("card_number"))
                            .account(Account.builder().id(resultSet.getInt("account_id")).build())
                            .client(Client.builder().id(resultSet.getInt("client_id")).build())
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
        if (card == null) {
            throw new SQLException("The card does not exist");
        }
        return card;
    }

    /**
     * Returns a list of cards by account ID.
     *
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
                            .cardNumber(resultSet.getInt("card_number"))
                            .account(Account.builder().id(resultSet.getInt("account_id")).build())
                            .client(Client.builder().id(resultSet.getInt("client_id")).build())
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
        if (cards.isEmpty()) {
            throw new SQLException("There are no cards");
        }
        return cards;
    }

}
