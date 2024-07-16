package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Client;
import org.example.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller for working with clients.
 */
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientByID(@PathVariable int id) throws SQLException {
        Client client = clientService.getClientByID(id);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() throws SQLException {
        List<Client> clients = clientService.getClients();

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
