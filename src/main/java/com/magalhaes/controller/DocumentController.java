package com.magalhaes.controller;

import com.magalhaes.model.Document;
import com.magalhaes.model.DocumentResponse;
import com.magalhaes.service.DocumentService;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DocumentController {

    @Inject
    DocumentService documentService;

    @GET
    @Path("/documents")
    public List<DocumentResponse> getAllDocuments() {
        return documentService.getDocumentsWithOwner();
    }

    // Endpoint para obter um documento por ID
    @GET
    @Path("/document/{id}")
    public DocumentResponse getDocumentById(@PathParam("id") String id) {
        return documentService.getDocumentById(id);
    }

    // Endpoint para adicionar um novo documento
    @POST
    @Path("/document")
    public String addDocument(Document document) {
        return documentService.addDocument(document);
    }

}
