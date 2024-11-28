package com.devInnovators.Whatchdog.Query.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data                   // Genera los getters, setters, toString, equals, y hashCode.
@AllArgsConstructor      // Genera un constructor con todos los campos.
@NoArgsConstructor 

@Document(collection = "query_coordinates")
public class QueryCoordinates {

    @Field("latitude")
    private double latitude;

    @Field("longitude")
    private double longitude;

}