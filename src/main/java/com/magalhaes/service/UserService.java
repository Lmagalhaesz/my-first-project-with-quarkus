package com.magalhaes.service;

import java.util.List;
import java.util.ArrayList;

import com.magalhaes.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

    private final MongoClient mongoClient;
    private final MongoCollection<User> coll;

    public UserService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.coll = mongoClient.getDatabase("test").getCollection("users", User.class);
    }

    public String addUser(User user) {
        user.setPassword(user.getPassword());
        return coll.insertOne(user).getInsertedId().asObjectId().getValue().toHexString();
    }

    public List<User> getUsers() {
        return coll.find().into(new ArrayList<>());
    }

      
}
