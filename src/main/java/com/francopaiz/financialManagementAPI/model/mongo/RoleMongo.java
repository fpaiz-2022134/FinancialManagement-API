package com.francopaiz.financialManagementAPI.model.mongo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Esta clase representa un documento de rol en una colección de MongoDB.
 * Utiliza las anotaciones de Spring Data MongoDB para mapear los datos a una colección específica.
 * La anotación @Data de Lombok genera automáticamente getters, setters, toString,
 * equals, y hashCode.
 */
@Data
@Document(collection = "roles") // Define que esta clase está asociada a la colección "roles" en MongoDB.
public class RoleMongo {

    @Id
    private String idRole; // Identificador único del rol en la base de datos.

    private String role; // Nombre del rol (por ejemplo, "ADMIN", "USER").
}