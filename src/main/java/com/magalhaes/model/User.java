package com.magalhaes.model;

import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
public class User {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @NonNull
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String password;


    public String setPassword(String pswdString) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(pswdString); // Criptografa a senha
        return password;
    }
    
    public boolean verifyPassword(String passwordString) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(passwordString, this.password); // Verifica se a senha corresponde
    }

}
