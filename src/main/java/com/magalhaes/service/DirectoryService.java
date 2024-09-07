package com.magalhaes.service;

import com.magalhaes.model.Directory;
import com.magalhaes.model.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DirectoryService {
    private final MongoClient mongoClient;
    private final MongoCollection<Directory> coll;

    public DirectoryService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.coll = mongoClient.getDatabase("test").getCollection("directories", Directory.class);
    }
}
