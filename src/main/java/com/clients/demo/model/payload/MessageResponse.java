package com.clients.demo.model.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class MessageResponse implements Serializable {

    private String message;

    private Object object;
}
