package com.clients.demo.model.repository;

import com.clients.demo.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// DAO
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
