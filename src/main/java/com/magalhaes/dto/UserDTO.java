package com.magalhaes.dto;

import org.bson.types.ObjectId;

import com.magalhaes.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private ObjectId id;
    private String nome;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.nome = user.getName();
        this.email = user.getEmail();
    }
}
