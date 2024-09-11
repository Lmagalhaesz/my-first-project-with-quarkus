package com.magalhaes.controller;

import com.magalhaes.model.Document;
import com.magalhaes.model.DocumentResponse;
import com.magalhaes.model.User;
import com.magalhaes.service.DocumentService;
import com.magalhaes.service.UserService;

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
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Path("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    // Endpoint para obter um documento por ID
    @GET
    @Path("/user/{id}")
    public User getUserById(@PathParam("id") String id) {
        return userService.getUserById(id);
    }

    // Endpoint para adicionar um novo documento
    @POST
    @Path("/user")
    public String addUser(User user) {
        return userService.addUser(user);
    }

    @DELETE
    @Path("/user/{id}")
    public long deleteDocument(@PathParam("id") String id) {
        return userService.deleteUser(id);
    }

}
