package com.francopaiz.financialManagementAPI.repository.usuario.mongo;


import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Interfaz del repositorio de usuarios para MongoDB.
 * Extiende MongoRepository para proporcionar operaciones CRUD
 * y consultas específicas para la entidad UserMongo.
 */
public interface UserRepositoryNoSql extends MongoRepository<UserMongo, String> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que puede contener el usuario si se encuentra, o vacío si no.
     */
    Optional<UserMongo> findByUsername(String username);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Un Optional que puede contener el usuario si se encuentra, o vacío si no.
     */
    Optional<UserMongo> findByEmail(String email);
}