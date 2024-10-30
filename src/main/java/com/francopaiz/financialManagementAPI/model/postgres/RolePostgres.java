package com.francopaiz.financialManagementAPI.model.postgres;

import jakarta.persistence.*; // Importa las anotaciones de JPA.
import lombok.Data;

/**
 * Esta clase representa un rol en la base de datos PostgreSQL.
 * Utiliza las anotaciones de Jakarta Persistence para mapear los datos a una tabla específica.
 * La anotación @Data de Lombok genera automáticamente getters, setters, toString,
 * equals, y hashCode.
 */
@Data
@Entity // Indica que esta clase es una entidad JPA.
@Table(name = "roles") // Define que esta clase está asociada a la tabla "roles" en la base de datos.
public class RolePostgres {

    @Id // Marca este campo como el identificador único de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor se generará automáticamente.
    @Column(name = "id_role") // Mapea esta propiedad al campo "id_role" en la tabla.
    private Long idRole; // Identificador único del rol en la base de datos.

    private String role; // Nombre del rol (por ejemplo, ADMIN, USER).
}