package com.clients.demo.service.impl;

import com.clients.demo.model.dto.ClientDTO;
import com.clients.demo.model.dto.ClientRoleDTO;
import com.clients.demo.model.dto.RoleDTO;
import com.clients.demo.model.entity.ClientEntity;
import com.clients.demo.model.entity.ClientRoleEntity;
import com.clients.demo.model.entity.ERole;
import com.clients.demo.model.entity.RoleEntity;
import com.clients.demo.model.repository.ClientRepository;
import com.clients.demo.model.repository.ClientRoleRepository;
import com.clients.demo.model.repository.RoleRepository;
import com.clients.demo.service.RoleService;
import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//TODO: Methods Reactive
public class RoleServiceImpl implements RoleService {

    private final ClientRepository clientRepository;

    private final ClientRoleRepository clientRoleRepository;

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Single<ClientDTO> add(ClientRoleDTO clientRoleDTO) {
        return saveClientRoleToRepository(clientRoleDTO)
                .map(this::toClientResponse);
    }

    //TODO: Check
    private Single<ClientEntity> saveClientRoleToRepository(ClientRoleDTO clientRoleDTO) {
        return Single.create(singleSubscriber -> {
            Optional<ClientEntity> optionalAuthor = clientRepository.findById(clientRoleDTO.getClientId());
            if (!optionalAuthor.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                RoleEntity roleEntity = roleRepository.save(RoleEntity.builder()
                        .name(ERole.valueOf(clientRoleDTO.getNewRole()))
                        .build());
                clientRoleRepository.save(toClientRoleUpdate(
                        ClientRoleEntity.builder().build(), clientRoleDTO, roleEntity.getId()));
                singleSubscriber.onSuccess(clientRepository.findById(clientRoleDTO.getClientId()).get());
            }
        });
    }

    @Override
    public Single<List<RoleDTO>> getRoles() {
        //return clientRepositoryDemo.findAll().toList().map(this::toClientResponseList);
        return findAllRolesInRepository()
                .map(this::toRoleResponseList);
    }

    private Single<List<RoleEntity>> findAllRolesInRepository() {
        return Single.create(singleSubscriber -> {
            List<RoleEntity> roles = roleRepository.findAll();
            singleSubscriber.onSuccess(roles);
        });
    }

    private List<RoleDTO> toRoleResponseList(List<RoleEntity> roleList) {
        return roleList
                .stream()
                .map(this::toRoleResponse)
                .collect(Collectors.toList());
    }

    private RoleDTO toRoleResponse(RoleEntity roleEntity) {
        return RoleDTO.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName().name())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Single<RoleDTO> findById(Integer id) {
        //return clientRepositoryDemo.findById(id).toSingle().map(this::toClientResponse);
        return findRoleRepository(id);
    }

    private Single<RoleDTO> findRoleRepository(Integer id) {
        return Single.create(singleSubscriber -> {
            Optional<RoleEntity> optionalRole = roleRepository.findById(id);
            if (!optionalRole.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                RoleDTO roleResponse = toRoleResponse(optionalRole.get());
                singleSubscriber.onSuccess(roleResponse);
            }
        });
    }

    @Override
    public Single<ClientDTO> updateRole(ClientRoleDTO role) {
        return updateRoleToRepository(role)
                .map(this::toClientResponse);
    }

    private Single<ClientEntity> updateRoleToRepository(ClientRoleDTO clientRoleDTO) {
        return Single.create(singleSubscriber -> {
            ClientRoleEntity clientRole = clientRoleRepository
                    .findByClientIdAndRoleId(clientRoleDTO.getClientId(), clientRoleDTO.getRoleId());
            if (clientRole == null)
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                RoleEntity roleEntity = roleRepository.save(RoleEntity.builder()
                        .name(ERole.valueOf(clientRoleDTO.getNewRole()))
                        .build());
                clientRoleRepository.save(toClientRoleUpdate(clientRole, clientRoleDTO, roleEntity.getId()));
                singleSubscriber.onSuccess(clientRepository.findById(clientRole.getClientId()).get());
            }
        });
    }

    private ClientRoleEntity toClientRoleUpdate(
            ClientRoleEntity clientRoleEntity, ClientRoleDTO clientRoleDTO, Integer newRoleId) {
        return ClientRoleEntity.builder()
                .id(clientRoleEntity.getId())
                .clientId(clientRoleDTO.getClientId())
                .roleId(newRoleId)
                .build();
    }

    private ClientDTO toClientResponse(ClientEntity clientEntity) {
        ClientDTO clientResponse = new ClientDTO();
        BeanUtils.copyProperties(clientEntity, clientResponse);
        return clientResponse;
    }

    @Override
    public Completable delete(Integer id) {
        return deleteRoleInRepository(id);
    }

    private Completable deleteRoleInRepository(Integer id) {
        return Completable.create(completableSubscriber -> {
            Optional<RoleEntity> optionalRole = roleRepository.findById(id);
            if (!optionalRole.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                roleRepository.delete(optionalRole.get());
                completableSubscriber.onComplete();
            }
        });
    }
}
