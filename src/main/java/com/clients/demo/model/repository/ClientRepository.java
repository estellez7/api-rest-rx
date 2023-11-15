package com.clients.demo.model.repository;

import com.clients.demo.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// DAO
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

    ClientEntity findByUsername(String username);
}
