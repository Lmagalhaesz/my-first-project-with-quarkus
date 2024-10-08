package com.magalhaes.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.magalhaes.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String nome;
    private String email;

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.nome = user.getName();
            this.email = user.getEmail();
        } else {
            // Defina valores padrão ou lance uma exceção personalizada
            this.id = null;
            this.nome = "Usuário Desconhecido";
            this.email = "Email não disponível";
        }
    }
}
