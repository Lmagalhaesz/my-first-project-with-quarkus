package com.magalhaes.model;

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
public class DocumentResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String titulo;
    private String conteudo;
    private UserDTO owner;  // Informações do dono

    public DocumentResponse(Document document, User user) {
        this.id = document.getId();
        this.titulo = document.getTitle();
        this.conteudo = document.getContent();
        this.owner = new UserDTO(user);  // Converte o usuário para o DTO
    }
    public Document toDocument() {
        return new Document(
            this.id,
            this.owner != null ? this.owner.getId() : null, // Assuming owner ID is needed
            this.titulo,
            this.conteudo
        );
    }
}
