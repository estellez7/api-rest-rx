package com.clients.demo.model.repository;

import com.clients.demo.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;
import org.springframework.stereotype.Repository;

// DAO
@Repository
public interface ClientRepositoryDemo extends JpaRepository<ClientEntity, Integer> {

    ClientEntity findByUsername(String username);
}
