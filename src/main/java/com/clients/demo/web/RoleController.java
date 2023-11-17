package com.clients.demo.web;

import com.clients.demo.model.dto.ClientDTO;
import com.clients.demo.model.dto.ClientRoleDTO;
import com.clients.demo.model.dto.RoleDTO;
import com.clients.demo.service.RoleService;
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
public class RoleController {

    private final RoleService roleService;

    @PostMapping(
            value = "/role",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<ClientDTO>> create(@RequestBody ClientRoleDTO clientRoleDTO) {
        return roleService.add(clientRoleDTO)
                .subscribeOn(Schedulers.io())
                .map(clientSaved -> new ResponseEntity<>(clientSaved, HttpStatus.CREATED));
    }

    @PutMapping(
            value = "/role",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<ClientDTO>> update(@RequestBody ClientRoleDTO clientRoleDTO) {
        return roleService.updateRole(clientRoleDTO)
                .subscribeOn(Schedulers.io())
                .map(clientUpdated -> new ResponseEntity<>(clientUpdated, HttpStatus.CREATED));
    }

    @GetMapping(
            value = "/roles",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<List<RoleDTO>>> showAll() {
        return roleService.getRoles()
                .subscribeOn(Schedulers.io())
                .map(ResponseEntity::ok);
    }

    @GetMapping(
            value = "/role/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<RoleDTO>> showById(@PathVariable Integer id) {
        return roleService.findById(id)
                .subscribeOn(Schedulers.io())
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(
            value = "/role/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<?>> delete(@PathVariable Integer id) {
        return roleService.delete(id)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
