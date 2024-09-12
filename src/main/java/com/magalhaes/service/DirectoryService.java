package com.magalhaes.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.magalhaes.exceptions.DataBaseErrorException;
import com.magalhaes.exceptions.DirectoryErrorException;
import com.magalhaes.exceptions.GenericException;
import com.magalhaes.exceptions.enums.ErrorCodeEnum;
import com.magalhaes.model.Directory;
import com.magalhaes.model.DirectoryResponse;
import com.magalhaes.model.Document;
import com.magalhaes.model.User;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
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

   public List<DirectoryResponse> getDirectories() {
    List<DirectoryResponse> directoryResponses = new ArrayList<>();

    try {
        List<Directory> directories = coll.find().into(new ArrayList<>());
        
        for (Directory directory : directories) {
        
            User user = userCollection.find(Filters.eq("_id", directory.getUserId())).first();
        
    
            List<Document> directoryDocuments = new ArrayList<>();

        
            for (ObjectId documentId : directory.getDocumentsId()) {
                Document document = documentCollection.find(Filters.eq("_id", documentId)).first();
                if (document != null) {
                    directoryDocuments.add(document); // Adiciona o documento encontrado
                }
            }
        if(directory == null) throw new DirectoryErrorException("Diretórios não encontrados.", ErrorCodeEnum.DIRE0001.getCode());
        if(user == null) throw new DirectoryErrorException("Usuário de tal diretório não encontrado.", ErrorCodeEnum.DIRE0001.getCode());
        DirectoryResponse directoryResponse = new DirectoryResponse(directory, directoryDocuments, user);
        directoryResponses.add(directoryResponse);
    }
    } catch (MongoWriteException e) {
        // Exceção específica para falhas na escrita
        throw new DataBaseErrorException("Erro ao escrever no banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
    } catch (MongoException e) {
        // Exceção geral do MongoDB
        throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
    } catch (IllegalArgumentException e) {
        // Exceção para argumentos inválidos, se aplicável
        throw new GenericException("Argumento inválido: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
    } catch (Exception e) {
        // Captura exceções gerais
        throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
    }
    if(directoryResponses.isEmpty()) throw new DirectoryErrorException("Não foi possível listar os diretórios.", ErrorCodeEnum.DIRE0001.getCode());
    return directoryResponses;
}
public String addDirectory(Directory directory) {
    String response = null;
    try {
        // Verifica se já existe um diretório com o mesmo nome
        Bson filter = Filters.eq("name", directory.getName());
        long count = coll.countDocuments(filter);

        if (count > 0) {
            directory.setName(directory.getName() + " - copy");
        }

        // Insere o novo diretório
        response = coll.insertOne(directory).getInsertedId().asObjectId().getValue().toHexString();
    } catch (MongoWriteException e) {
        // Exceção específica para falhas na escrita
        throw new DataBaseErrorException("Erro ao escrever no banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
    } catch (MongoException e) {
        // Exceção geral do MongoDB
        throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
    } catch (IllegalArgumentException e) {
        // Exceção para argumentos inválidos, se aplicável
        throw new GenericException("Argumento inválido: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
    } catch (Exception e) {
        // Captura exceções gerais
        throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
    }

    if (response == null) {
        throw new DirectoryErrorException("Diretório não pôde ser criado", ErrorCodeEnum.DIRE0001.getCode());
    }

    return response;
}
}
