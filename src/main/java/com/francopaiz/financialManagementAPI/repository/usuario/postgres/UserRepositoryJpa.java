package com.francopaiz.financialManagementAPI.repository.usuario.postgres;

import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import com.francopaiz.financialManagementAPI.model.postgres.UserPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio JPA para gestionar usuarios en PostgreSQL.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD
 * sobre la entidad UserPostgres.
 */
@Repository
public interface UserRepositoryJpa extends JpaRepository<UserPostgres, Long> {

    /**
     * Busca un usuario en la base de datos por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<UserPostgres> findByEmail(String email);

    /**
     * Busca un usuario en la base de datos por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<UserPostgres> findByUsername(String username);
}
