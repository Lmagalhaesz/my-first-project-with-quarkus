package com.magalhaes.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.magalhaes.model.Directory;
import com.magalhaes.model.DirectoryResponse;
import com.magalhaes.model.Document;
import com.magalhaes.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DirectoryService {
    private final MongoClient mongoClient;
    private final MongoCollection<Directory> coll;
    private final MongoCollection<Document> documentCollection;
    private final MongoCollection<User> userCollection;

    public DirectoryService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.coll = mongoClient.getDatabase("test").getCollection("directories", Directory.class);
        this.documentCollection = mongoClient.getDatabase("test").getCollection("documents", Document.class);
        this.userCollection = mongoClient.getDatabase("test").getCollection("users", User.class);
    }

    // Método para obter todos os documentos com os detalhes do dono (owner)
   public List<DirectoryResponse> getDirectories() {
    // Lista para armazenar a resposta
    List<DirectoryResponse> directoryResponses = new ArrayList<>();

    // Busca todos os diretórios
    List<Directory> directories = coll.find().into(new ArrayList<>());

    // Para cada diretório, buscar o usuário dono e criar a resposta
    for (Directory directory : directories) {
        // Busca o usuário pelo userId do diretório
        User user = userCollection.find(Filters.eq("_id", directory.getUserId())).first();
        
        // Inicializa a lista de documentos associados a este diretório
        List<Document> directoryDocuments = new ArrayList<>();

        // Busca os documentos relacionados ao diretório pelo ID dos documentos
        for (ObjectId documentId : directory.getDocumentsId()) {
            Document document = documentCollection.find(Filters.eq("_id", documentId)).first();
            if (document != null) {
                directoryDocuments.add(document); // Adiciona o documento encontrado
            }
        }

        // Cria o objeto DirectoryResponse
        DirectoryResponse directoryResponse = new DirectoryResponse(directory, directoryDocuments, user);
        directoryResponses.add(directoryResponse);
    }

    // Retorna a lista de DirectoryResponse
    return directoryResponses;
}
public String addDirectory(Directory directory) {
    return coll.insertOne(directory).getInsertedId().asObjectId().getValue().toHexString();
}
}
