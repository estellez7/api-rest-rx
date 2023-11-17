package com.clients.demo.service.impl;

import com.clients.demo.model.repository.ClientRepository;
import com.clients.demo.model.dto.ClientDTO;
import com.clients.demo.model.entity.ClientEntity;
import com.clients.demo.model.entity.ERole;
import com.clients.demo.model.entity.RoleEntity;
import com.clients.demo.service.ClientService;
import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//TODO: Methods Reactive
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Single<List<ClientDTO>> getClients() {
        //return clientRepositoryDemo.findAll().toList().map(this::toClientResponseList);
        return findAllClientsInRepository()
                .map(this::toClientResponseList);
    }

    private Single<List<ClientEntity>> findAllClientsInRepository() {
        return Single.create(singleSubscriber -> {
            List<ClientEntity> clients = clientRepository.findAll();
            singleSubscriber.onSuccess(clients);
        });
    }

    private List<ClientDTO> toClientResponseList(List<ClientEntity> clientList) {
        return clientList
                .stream()
                .map(this::toClientResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Single<ClientDTO> update(ClientDTO client) {
        return updateClientToRepository(client)
                .map(this::toClientResponse);
    }

    private Single<ClientEntity> updateClientToRepository(ClientDTO client) {
        return Single.create(singleSubscriber -> {
            Optional<ClientEntity> optionalClient = clientRepository.findById(client.getIdClient());
            if (!optionalClient.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                singleSubscriber.onSuccess(clientRepository.save(toClientUpdate(client, optionalClient.get())));
            }
        });
    }

    @Transactional
    @Override
    public Single<ClientDTO> add(ClientDTO client) {
        return saveClientToRepository(client)
                .map(this::toClientResponse);
    }

    //TODO: Check
    private Single<ClientEntity> saveClientToRepository(ClientDTO client) {
        return Single.create(singleSubscriber -> {
            ClientEntity clientEntity = clientRepository.save(toClientAdded(client));
            singleSubscriber.onSuccess(clientEntity);
        });
        /*return Single.create(singleSubscriber -> {
            Optional<ClientEntity> optionalAuthor = clientRepository.findById(client.getIdClient());
            if (!optionalAuthor.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                String addedClientId = String.valueOf(clientRepository.save(toClient(client)).getIdClient());
                singleSubscriber.onSuccess(addedClientId);
            }
        });*/
    }

    //TODO: Check
    private ClientEntity toClientAdded(ClientDTO clientRequest) {

        Set<RoleEntity> roles = clientRequest.getRoles()
                .stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        return ClientEntity.builder()
                .idClient(clientRequest.getIdClient())
                .name(clientRequest.getName())
                .last_name(clientRequest.getLast_name())
                .email(clientRequest.getEmail())
                .date(clientRequest.getDate())
                .username(clientRequest.getUsername())
                .password(clientRequest.getPassword())
                .roles(roles)
                .build();

        /*ClientEntity client = new ClientEntity();
        BeanUtils.copyProperties(clientRequest, client);*/

        //return client;
    }

    //TODO: Check
    private ClientEntity toClientUpdate(ClientDTO clientRequest, ClientEntity clientEntity) {
        return ClientEntity.builder()
                .idClient(clientRequest.getIdClient())
                .name(clientRequest.getName())
                .last_name(clientRequest.getLast_name())
                .email(clientRequest.getEmail())
                .date(clientRequest.getDate())
                .username(clientRequest.getUsername())
                .password(clientRequest.getPassword())
                .roles(clientEntity.getRoles())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Single<ClientDTO> findById(Integer id) {
        //return clientRepositoryDemo.findById(id).toSingle().map(this::toClientResponse);
        return findClientRepository(id);
    }

    private Single<ClientDTO> findClientRepository(Integer id) {
        return Single.create(singleSubscriber -> {
            Optional<ClientEntity> optionalClient = clientRepository.findById(id);
            if (!optionalClient.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                ClientDTO clientResponse = toClientResponse(optionalClient.get());
                singleSubscriber.onSuccess(clientResponse);
            }
        });
    }

    private ClientDTO toClientResponse(ClientEntity clientEntity) {
        ClientDTO clientResponse = new ClientDTO();
        BeanUtils.copyProperties(clientEntity, clientResponse);
        return clientResponse;
    }

    @Override
    public Completable delete(Integer id) {
        return deleteClientInRepository(id);
    }

    private Completable deleteClientInRepository(Integer id) {
        return Completable.create(completableSubscriber -> {
            Optional<ClientEntity> optionalClient = clientRepository.findById(id);
            if (!optionalClient.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                clientRepository.delete(optionalClient.get());
                completableSubscriber.onComplete();
            }
        });
    }

    /*@Override
    public boolean existsById(Integer id) {
        return clientRepository.existsById(id);
    }*/
}
