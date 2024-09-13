package com.magalhaes.model;

import java.util.List;

import org.bson.types.ObjectId;

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
@RequiredArgsConstructor
@EqualsAndHashCode
public class Directory {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @NonNull
    private String name;
    @NonNull
    private ObjectId userId;
    private List<ObjectId> documentsId;

}
