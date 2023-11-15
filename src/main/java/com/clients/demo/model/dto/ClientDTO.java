package com.clients.demo.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ClientDTO implements Serializable {

    private Integer idClient;

    private String name;

    private String last_name;

    private String email;

    private Date date;

    private String username;

    private String password;

    private Set<String> roles;
}
