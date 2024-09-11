package com.magalhaes.dto;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.magalhaes.model.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String title;
    private String content;

    public DocumentDTO(Document document) {
        if (document != null) {
            this.id = document.getId();
            this.title = document.getTitle();
            this.content = document.getContent();
        } else {
            // Defina valores padrão ou lance uma exceção personalizada
            this.id = null;
            this.title = "Documento Desconhecido";
            this.content = "";
        }
    }
}
