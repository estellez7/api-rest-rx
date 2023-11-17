package com.clients.demo.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RoleDTO implements Serializable {

    private Integer id;

    private String name;

}
