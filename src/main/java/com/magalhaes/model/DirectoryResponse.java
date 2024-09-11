package com.magalhaes.model;

import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.magalhaes.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private List<Document> documents;
    private UserDTO owner;  // Informações do dono

    public DirectoryResponse(Directory directory, List<Document> documents, User user) {
        this.id = directory.getId();
        this.name = directory.getName();
        this.documents = documents;
        this.owner = new UserDTO(user);  // Converte o usuário para o DTO
    }
}
