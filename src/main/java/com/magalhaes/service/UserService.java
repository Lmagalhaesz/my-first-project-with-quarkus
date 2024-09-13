package com.magalhaes.service;

import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import com.magalhaes.exceptions.DataBaseErrorException;
import com.magalhaes.exceptions.GenericException;
import com.magalhaes.exceptions.UserErrorException;
import com.magalhaes.exceptions.enums.ErrorCodeEnum;
import com.magalhaes.model.User;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
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
            List<User> users = this.getUsers();
            for(User userf : users){
                if(userf.getEmail().equals(user.getEmail())){
                    throw new UserErrorException("Já existe tal usuário cadastrado", ErrorCodeEnum.UE0001.getCode());
                }
            user.setPassword(user.getPassword());
            add = coll.insertOne(user).getInsertedId().asObjectId().getValue().toHexString();
            }
        } catch (MongoWriteException e) {
            // Exceção específica para falhas na escrita
            throw new DataBaseErrorException("Erro ao escrever no banco de dados: " + e.getMessage(),
                    ErrorCodeEnum.DB0001.getCode());
        } catch (MongoException e) {
            // Exceção geral do MongoDB
            throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(),
                    ErrorCodeEnum.DB0001.getCode());
        } catch (IllegalArgumentException e) {
            // Exceção para argumentos inválidos, se aplicável
            throw new GenericException("Argumento inválido: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
        } catch (Exception e) {
            // Captura exceções gerais
            throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
        }
        if(add == null) throw new UserErrorException("Erro ao cadastrar usuário.", ErrorCodeEnum.UE0001.getCode());
        return add;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>(); 
    try {
        users = coll.find().into(new ArrayList<>());
    } catch (MongoWriteException e) {
        // Exceção específica para falhas na escrita
        throw new DataBaseErrorException("Erro ao escrever no banco de dados: " + e.getMessage(),
                ErrorCodeEnum.DB0001.getCode());
    } catch (MongoException e) {
        // Exceção geral do MongoDB
        throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(),
                ErrorCodeEnum.DB0001.getCode());
    } catch (IllegalArgumentException e) {
        // Exceção para argumentos inválidos, se aplicável
        throw new GenericException("Argumento inválido: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
    } catch (Exception e) {
        // Captura exceções gerais
        throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
    }

    if (users.isEmpty()) throw new UserErrorException("Não há nenhum usuário cadastrado", ErrorCodeEnum.UE0001.getCode());

    return users;
    }

    public User getUserById(String id){
        try {
            User user = coll.find(Filters.eq("_id", new ObjectId(id))).first();
            
            if (user == null) {
                throw new UserErrorException("Não há usuário cadastrado com este ID: " + id, ErrorCodeEnum.UE0001.getCode());
            }
            
            return user;
    
        } catch (MongoWriteException e) {
            // Exceção específica para falhas na escrita
            throw new DataBaseErrorException("Erro ao escrever no banco de dados: " + e.getMessage(),
                    ErrorCodeEnum.DB0001.getCode());
        } catch (MongoException e) {
            // Exceção geral do MongoDB
            throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(),
                    ErrorCodeEnum.DB0001.getCode());
        } catch (IllegalArgumentException e) {
            // Exceção para argumentos inválidos, se aplicável
            throw new GenericException("Argumento inválido: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
        } catch (Exception e) {
            // Captura exceções gerais
            throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
        }
    }

    public long deleteUser(String id) {
        try {
            Bson filter = eq("_id", new ObjectId(id));
            
            long deletedCount = coll.deleteOne(filter).getDeletedCount();
            
            if (deletedCount == 0) {
                throw new UserErrorException("Não há usuário cadastrado com este ID: " + id, ErrorCodeEnum.UE0001.getCode());
            }
            
            return deletedCount;
        
        } catch (MongoWriteException e) {
            // Exceção específica para falhas na escrita
            throw new DataBaseErrorException("Erro ao escrever no banco de dados: " + e.getMessage(),
                    ErrorCodeEnum.DB0001.getCode());
        } catch (MongoException e) {
            // Exceção geral do MongoDB
            throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(),
                    ErrorCodeEnum.DB0001.getCode());
        } catch (IllegalArgumentException e) {
            // Exceção para argumentos inválidos, se aplicável
            throw new GenericException("Argumento inválido: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
        } catch (Exception e) {
            // Captura exceções gerais
            throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
        }   
    }
      
}
