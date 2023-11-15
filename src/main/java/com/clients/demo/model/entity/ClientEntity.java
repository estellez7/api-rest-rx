package com.clients.demo.model.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "clients")
public class ClientEntity implements Serializable {

    @Id
    @Column(name = "id_client")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String last_name;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "date")
    private Date date;

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "client_roles", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
}
