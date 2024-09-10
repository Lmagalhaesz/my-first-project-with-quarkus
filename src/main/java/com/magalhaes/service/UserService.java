package com.magalhaes.service;

import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import com.magalhaes.model.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.eq;

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

    public User getUserById(String id){
        return coll.find(Filters.eq("_id", new ObjectId(id) )).first();
    }

    public long deleteUser(String id) {
        Bson filter = eq("_id", new ObjectId(id));
        return coll.deleteOne(filter).getDeletedCount();
    }
      
}
