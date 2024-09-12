package com.magalhaes.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.magalhaes.exceptions.DataBaseErrorException;
import com.magalhaes.exceptions.DocumentErrorException;
import com.magalhaes.exceptions.GenericException;
import com.magalhaes.exceptions.enums.ErrorCodeEnum;
import com.magalhaes.model.Document;
import com.magalhaes.model.DocumentResponse;
import com.magalhaes.model.User;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;

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

    public List<DocumentResponse> getDocumentsWithOwner() {
    List<DocumentResponse> documentResponses = new ArrayList<>();
    try {
        List<Document> documents = documentCollection.find().into(new ArrayList<>());

        for (Document document : documents) {
            User user = userCollection.find(Filters.eq("_id", document.getUserId())).first();
            
            if (user == null) {
                throw new DocumentErrorException("Usuário não encontrado para o documento: " + document.getId(), ErrorCodeEnum.DE0001.getCode());
            }
            
            DocumentResponse documentResponse = new DocumentResponse(document, user);
            documentResponses.add(documentResponse);
        }

    } catch (MongoException e) {
        throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
    } catch (DocumentErrorException e) {
        throw e;
    } catch (Exception e) {
        throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
    }
    if(documentResponses.size() == 0) throw new DocumentErrorException("Nenhum documento encontrado.", ErrorCodeEnum.DE0001.getCode()); 
    return documentResponses;
}


    public DocumentResponse getDocumentById(String id) {

        Document document = null;
        User user = null;
        try {
            ObjectId objectId = new ObjectId(id);

        document = documentCollection.find(Filters.eq("_id", objectId)).first();

        if (document == null) throw new DocumentErrorException("Documento é nulo", ErrorCodeEnum.DE0001.getCode());

        user = userCollection.find(Filters.eq("_id", document.getUserId())).first();

        if(user == null) throw new DocumentErrorException("Usuário não encontrado para o documento: " + document.getId(), ErrorCodeEnum.DE0001.getCode());

        } catch (MongoException e) {
            throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
        } catch (DocumentErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new GenericException("Erro inesperado: " + e.getMessage(), ErrorCodeEnum.GE0001.getCode());
        }
        

        return new DocumentResponse(document, user);
    }

    public String addDocument(Document document) {
        String add = null;
        try {
            add = documentCollection.insertOne(document).getInsertedId().asObjectId().getValue().toHexString();
        } catch (MongoException e) {
            throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
        }
        if (add == null) throw new DocumentErrorException("Erro ao adicionar documento", ErrorCodeEnum.DE0001.getCode()); 
        return add;
    }

    public long deleteDocument(String id) {
        long delete = 0;
        try {
            Bson filter = eq("_id", new ObjectId(id));
            delete = documentCollection.deleteOne(filter).getDeletedCount();
        } catch (MongoException e) {
            throw new DataBaseErrorException("Erro de banco de dados: " + e.getMessage(), ErrorCodeEnum.DB0001.getCode());
        }
        if(delete ==0) throw new DocumentErrorException("Erro ao deletar documento", ErrorCodeEnum.DE0001.getCode()); 
        return delete;
    }
}
