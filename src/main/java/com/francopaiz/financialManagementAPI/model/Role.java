package com.francopaiz.financialManagementAPI.model;

import lombok.Data;

/**
 * Esta clase representa un rol dentro del sistema de biblioteca.
 * La anotación @Data de Lombok genera automáticamente getters, setters, toString,
 * equals, y hashCode.
 */
@Data
public class Role {

    private String idRole; // Identificador único del rol.
    private String role; // Nombre o descripción del rol (por ejemplo, "ADMIN", "USER").
}