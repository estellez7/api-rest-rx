package com.clients.demo.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ClientRoleDTO implements Serializable {

    private Integer clientId;

    private Integer roleId;

    private String newRole;
}
