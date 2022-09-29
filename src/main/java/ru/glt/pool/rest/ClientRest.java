package ru.glt.pool.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glt.pool.client.Client;
import ru.glt.pool.client.ClientBase;
import ru.glt.pool.client.ClientService;

import java.util.List;


@RestController
@RequestMapping(path="/client")
public class ClientRest {
    @Autowired
    private ClientService clientService;

    @GetMapping(path = "all")
    public ResponseEntity<List<ClientBase>> getClients() {
        return new ResponseEntity(clientService.getClients(), HttpStatus.OK);
    }

    @GetMapping(path = "get")
    public ResponseEntity<Client> getClient(@RequestParam(name = "id") Integer id) {
        return new ResponseEntity(clientService.getClient(id), HttpStatus.OK);
    }

    @PostMapping(path = "add")
    public ResponseEntity addClient(@RequestBody Client client) {
        clientService.addClient(client);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "update")
    public ResponseEntity updateClient(@RequestBody Client client) {
        clientService.updateClient(client);
        return new ResponseEntity(HttpStatus.OK);
    }
}