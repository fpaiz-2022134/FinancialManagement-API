package com.francopaiz.financialManagementAPI.model;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Esta clase representa a un usuario en el sistema de biblioteca.
 * Implementa la interfaz UserDetails para ser utilizada en el contexto de seguridad.
 * La anotación @Data de Lombok genera automáticamente getters, setters, toString,
 * equals, y hashCode.
 */
@Data
public class User implements UserDetails {

    private String id; // Identificador único del usuario.
    private String name; // Nombre completo del usuario.
    private String username; // Nombre de usuario para el inicio de sesión.
    private String email; // Dirección de correo electrónico del usuario.
    private String password; // Contraseña cifrada del usuario.
    private LocalDateTime dateCreation; // Fecha de creación de la cuenta del usuario.
    private LocalDateTime dateUpdate; // Fecha de la última actualización de la cuenta.
    private Set<Role> roles = new HashSet<>(); // Conjunto de roles asignados al usuario.

    /**
     * Obtiene los derechos de acceso del usuario.
     *
     * @return Colección de autoridades concedidas al usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Debe implementarse para devolver las autoridades del usuario.
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