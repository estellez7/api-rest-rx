package com.clients.demo.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "client_roles")
public class ClientRoleEntity implements Serializable {

    @Id
    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "role_id")
    private Integer roleId;
}
