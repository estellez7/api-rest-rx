package com.clients.demo.service;

import com.clients.demo.model.dto.ClientDTO;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

public interface ClientService {

    Single<List<ClientDTO>> getClients();

    Single<ClientDTO> update(ClientDTO clientDTO);

    Single<ClientDTO> addClient(ClientDTO client);

    Single<ClientDTO> findById(Integer id);

    Completable delete(Integer id);

    //boolean existsById(Integer id);
}
