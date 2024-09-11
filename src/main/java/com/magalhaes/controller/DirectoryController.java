package com.magalhaes.controller;

import com.magalhaes.model.Directory;
import com.magalhaes.model.DirectoryResponse;
import com.magalhaes.model.Document;
import com.magalhaes.model.DocumentResponse;
import com.magalhaes.service.DirectoryService;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DirectoryController {

    @Inject
    DirectoryService directoryService;

    @GET
    @Path("/directories")
    public List<DirectoryResponse> getAllDirectories() {
        return directoryService.getDirectories();
    }

    // Endpoint para obter um documento por ID
    /*@GET
    @Path("/document/{id}")
    public DocumentResponse getDocumentById(@PathParam("id") String id) {
        return documentService.getDocumentById(id);
    }*/

    // Endpoint para adicionar um novo documento
    @POST
    @Path("/directory")
    public String addDirectory(Directory directory) {
        return directoryService.addDirectory(directory);
    }

    /*@DELETE
    @Path("/document/{id}")
    public long deleteDocument(@PathParam("id") String id) {
        return documentService.deleteDocument(id);
    }*/

}
