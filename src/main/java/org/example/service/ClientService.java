package org.example.service;

import lombok.RequiredArgsConstructor;
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
 * Service for working with clients.
 */
@Service
@RequiredArgsConstructor
public class ClientService {
    private final PostgresUtil postgresUtil;
    private final GenericValidator genericValidator;

    /**
     * Returns the client by its ID.
     * @param id client ID
     * @return client object
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public Client getClientByID(int id) throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Client client = null;

        String sqlQuery = "SELECT * FROM client WHERE id = ?";

        try {
            if (genericValidator.isClientExists(id)) {
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    client = Client.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .email(resultSet.getString("email"))
                            .build();
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
        return client;
    }

    /**
     * Returns a list of all clients.
     * @return list of client objects
     * @throws SQLException if an error occurred while executing an SQL-query
     */
    public List<Client> getClients() throws SQLException {
        Connection connection = postgresUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Client> clients = new ArrayList<>();

        String sqlQuery = "SELECT * FROM client";

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client client = Client.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .build();
                clients.add(client);
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
        return clients;
    }

}
