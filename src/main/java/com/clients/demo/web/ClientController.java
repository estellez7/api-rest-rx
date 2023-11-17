package com.clients.demo.web;

import com.clients.demo.model.dto.ClientDTO;
import com.clients.demo.service.ClientService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
//TODO: Exceptions in methods
public class ClientController {

    private final ClientService clientService;

    @PostMapping(
            value = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<ClientDTO>> create(@RequestBody ClientDTO clientDto) {
        return clientService.add(clientDto)
                .subscribeOn(Schedulers.io())
                .map(clientSaved -> new ResponseEntity<>(clientSaved, HttpStatus.CREATED));
    }

    @PutMapping(
            value = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<ClientDTO>> update(@RequestBody ClientDTO clientDto) {
        return clientService.update(clientDto)
                .subscribeOn(Schedulers.io())
                .map(clientUpdated -> new ResponseEntity<>(clientUpdated, HttpStatus.CREATED));
    }

    @DeleteMapping(
            value = "/client/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<?>> delete(@PathVariable Integer id) {
        return clientService.delete(id)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping(
            value = "/client/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<ClientDTO>> showById(@PathVariable Integer id) {
        return clientService.findById(id)
                .subscribeOn(Schedulers.io())
                .map(ResponseEntity::ok);
    }

    @GetMapping(
            value = "/clients",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<List<ClientDTO>>> showAll() {
        return clientService.getClients()
                .subscribeOn(Schedulers.io())
                .map(ResponseEntity::ok);
    }
}
