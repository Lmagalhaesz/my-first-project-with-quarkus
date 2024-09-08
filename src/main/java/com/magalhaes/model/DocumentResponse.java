package com.magalhaes.model;

import org.bson.types.ObjectId;

import com.magalhaes.dto.UserDTO;

public class DocumentResponse {
    private ObjectId id;
    private String titulo;
    private String conteudo;
    private UserDTO owner;  // Informações do dono

    public DocumentResponse(Document document, User user) {
        this.id = document.getId();
        this.titulo = document.getTitle();
        this.conteudo = document.getContent());
        this.owner = new UserDTO(user);  // Converte o usuário para o DTO
    }
}
