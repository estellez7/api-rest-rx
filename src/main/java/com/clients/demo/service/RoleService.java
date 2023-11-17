package com.clients.demo.service;

import com.clients.demo.model.dto.ClientDTO;
import com.clients.demo.model.dto.ClientRoleDTO;
import com.clients.demo.model.dto.RoleDTO;
import io.reactivex.Completable;
import io.reactivex.Single;

import java.util.List;

public interface RoleService {

    Single<ClientDTO> updateRole(ClientRoleDTO clientRoleDTO);

    Completable delete(Integer id);

    Single<List<RoleDTO>> getRoles();

    Single<RoleDTO> findById(Integer id);

}
