package com.magalhaes.service;

import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import com.magalhaes.exceptions.DataBaseErrorException;
import com.magalhaes.exceptions.UserErrorException;
import com.magalhaes.exceptions.enums.ErrorCodeEnum;
import com.magalhaes.model.User;
import com.mongodb.MongoException;
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
        String add = null;
        try {
            user.setPassword(user.getPassword());
            add = coll.insertOne(user).getInsertedId().asObjectId().getValue().toHexString();
        } catch (MongoException e) {
            throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
        }
        if(add == null) throw new UserErrorException("O usuário é nulo", ErrorCodeEnum.UE0001.getCode());
        return add;
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
