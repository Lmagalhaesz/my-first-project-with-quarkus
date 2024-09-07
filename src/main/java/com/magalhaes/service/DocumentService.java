package com.magalhaes.service;

import com.magalhaes.model.Document;
import com.magalhaes.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DocumentService {
    private final MongoClient mongoClient;
    private final MongoCollection<Document> coll;

    public DocumentService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.coll = mongoClient.getDatabase("test").getCollection("documents", Document.class);
    }
}
