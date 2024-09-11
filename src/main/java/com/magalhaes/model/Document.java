package com.magalhaes.model;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Document {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    @NonNull
    private String title;
    @NonNull
    private String content;
}
