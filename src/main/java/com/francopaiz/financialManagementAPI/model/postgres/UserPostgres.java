package com.francopaiz.financialManagementAPI.model.postgres;
import jakarta.persistence.*; // Importa las anotaciones de JPA.
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Esta clase representa un usuario en la base de datos PostgreSQL.
 * Implementa la interfaz UserDetails de Spring Security para proporcionar
 * información sobre el usuario que se utilizará en la autenticación y autorización.
 * La anotación @Data de Lombok genera automáticamente getters, setters, toString,
 * equals, y hashCode.
 */
@Data
@Entity // Indica que esta clase es una entidad JPA.
@Table(name = "users") // Define que esta clase está asociada a la tabla "users" en la base de datos.
public class UserPostgres implements UserDetails {

    @Id // Marca este campo como el identificador único de la entidad.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor se generará automáticamente.
    @Column(name = "id_user") // Mapea esta propiedad al campo "id_user" en la tabla.
    private Long id; // Identificador único del usuario.

    private String name; // Nombre completo del usuario.
    private String username; // Nombre de usuario utilizado para iniciar sesión.
    private String email; // Correo electrónico del usuario.
    private String password; // Contraseña cifrada del usuario.

    @Column(name = "date_creation") // Mapea esta propiedad al campo "date_creation" en la tabla.
    private LocalDateTime dateCreation; // Fecha de creación del usuario.

    @Column(name = "date_update") // Mapea esta propiedad al campo "date_update" en la tabla.
    private LocalDateTime dateUpdate; // Fecha de última actualización del usuario.

    @ManyToMany(fetch = FetchType.EAGER) // Relación muchos a muchos con roles.
    @JoinTable( // Define la tabla intermedia que vincula usuarios y roles.
            name = "user_role", // Nombre de la tabla intermedia.
            joinColumns = @JoinColumn(name = "id_user"), // Columna que referencia el id del usuario.
            inverseJoinColumns = @JoinColumn(name = "id_role") // Columna que referencia el id del rol.
    )
    private Set<RolePostgres> roles = new HashSet<>(); // Conjunto de roles asociados al usuario.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Devuelve una lista de autoridades (roles) del usuario.
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