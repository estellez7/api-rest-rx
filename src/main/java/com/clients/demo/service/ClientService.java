package com.clients.demo.service;

import com.clients.demo.model.dto.ClientDTO;
import com.clients.demo.model.dto.ClientRoleDTO;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

public interface ClientService {

    Single<List<ClientDTO>> getClients();

    Single<ClientDTO> updateClient(ClientDTO clientDTO);

    Single<ClientDTO> updateRole(ClientRoleDTO clientRoleDTO);

    Single<ClientDTO> addClient(ClientDTO client);

    Single<ClientDTO> findById(Integer id);

    Completable delete(Integer id);

    //boolean existsById(Integer id);
}
