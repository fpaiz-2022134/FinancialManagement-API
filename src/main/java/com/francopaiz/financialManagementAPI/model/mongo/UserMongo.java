package com.francopaiz.financialManagementAPI.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Esta clase representa un documento de usuario en una colección de MongoDB.
 * Implementa la interfaz UserDetails para la gestión de la seguridad y autenticación.
 * La anotación @Data de Lombok genera automáticamente getters, setters, toString,
 * equals, y hashCode.
 */
@Data
@Document(collection = "users") // Define que esta clase está asociada a la colección "users" en MongoDB.
public class UserMongo implements UserDetails {

    @Id
    private String id; // Identificador único del usuario en la base de datos.

    private String name; // Nombre completo del usuario.
    private String username; // Nombre de usuario único.
    private String email; // Correo electrónico del usuario.
    private String password; // Contraseña cifrada del usuario.

    @Field(name = "date_creation") // Mapea el campo "date_creation" en MongoDB a esta propiedad.
    private LocalDateTime dateCreation; // Fecha de creación de la cuenta del usuario.

    @Field(name = "date_updated") // Mapea el campo "date_updated" en MongoDB a esta propiedad.
    private LocalDateTime dateUpdate; // Fecha de la última actualización de la cuenta del usuario.

    private Set<RoleMongo> roles = new HashSet<>(); // Conjunto de roles asociados al usuario.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Retorna las autoridades otorgadas al usuario (actualmente vacío).
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired(); // Indica si la cuenta no ha expirado.
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked(); // Indica si la cuenta no está bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired(); // Indica si las credenciales no han expirado.
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled(); // Indica si la cuenta está habilitada.
    }
}