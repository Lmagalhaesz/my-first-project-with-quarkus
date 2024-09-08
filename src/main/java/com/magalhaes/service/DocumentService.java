package com.magalhaes.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.magalhaes.model.Document;
import com.magalhaes.model.DocumentResponse;
import com.magalhaes.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentService {
    private final MongoClient mongoClient;
    private final MongoCollection<Document> documentCollection;
    private final MongoCollection<User> userCollection;

    public DocumentService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.documentCollection = mongoClient.getDatabase("test").getCollection("documents", Document.class);
        this.userCollection = mongoClient.getDatabase("test").getCollection("users", User.class);  // Coleção de usuários
    }

    // Método para obter todos os documentos com os detalhes do dono (owner)
    public List<DocumentResponse> getDocumentsWithOwner() {
        // Lista para armazenar a resposta
        List<DocumentResponse> documentResponses = new ArrayList<>();

        // Busca todos os documentos
        List<Document> documents = documentCollection.find().into(new ArrayList<>());

        // Para cada documento, buscar o usuário dono e criar a resposta
        for (Document document : documents) {
            // Busca o usuário pelo userId do documento
            User user = userCollection.find(Filters.eq("_id", document.getUserId())).first();

            // Cria a resposta com o documento e o dono
            DocumentResponse documentResponse = new DocumentResponse(document, user);

            // Adiciona a resposta à lista
            documentResponses.add(documentResponse);
        }

        // Retorna a lista de DocumentResponse
        return documentResponses;
    }

    // Método para obter um documento pelo ID
    public DocumentResponse getDocumentById(String id) {
        // Converte o id para ObjectId
        ObjectId objectId = new ObjectId(id);

        // Busca o documento pelo ID
        Document document = documentCollection.find(Filters.eq("_id", objectId)).first();

        // Verifica se o documento foi encontrado
        if (document == null) {
            return null;  // Ou lançar uma exceção personalizada
        }

        // Busca o dono do documento (usuário)
        User user = userCollection.find(Filters.eq("_id", document.getUserId())).first();

        // Retorna o DocumentResponse com o dono
        return new DocumentResponse(document, user);
    }

    public String addDocument(Document document) {
        return documentCollection.insertOne(document).getInsertedId().asObjectId().getValue().toHexString();
    }
}
