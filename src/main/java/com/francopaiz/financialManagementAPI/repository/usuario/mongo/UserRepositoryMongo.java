package com.francopaiz.financialManagementAPI.repository.usuario.mongo;

import com.francopaiz.financialManagementAPI.caster.UserCaster;
import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import lombok.RequiredArgsConstructor;
import com.francopaiz.financialManagementAPI.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de usuarios para MongoDB.
 * Esta clase implementa las operaciones definidas en la interfaz UserRepository
 * y utiliza un repositorio NoSQL para acceder a los datos de los usuarios.
 */
@Profile("mongo")
@RequiredArgsConstructor
@Repository
public class UserRepositoryMongo implements UserRepository {

    private final UserRepositoryNoSql userRepositoryNoSql; // Repositorio NoSQL para operaciones CRUD
    private final UserCaster userCaster; // Utilidad para convertir entre User y UserMongo

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param user El usuario a crear.
     * @return El usuario creado.
     */
    @Override
    public User createUser(User user) {
        UserMongo userMongo = userCaster.userToUserMongo(user); // Conversión a formato Mongo
        UserMongo newUser = userRepositoryNoSql.save(userMongo); // Guardar en la base de datos
        return userCaster.userMongoToUser(newUser); // Convertir de nuevo a User
    }

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return Una lista de usuarios.
     */
    @Override
    public List<User> getUsers() {
        return userRepositoryNoSql.findAll().stream() // Obtener todos los usuarios en formato Mongo
                .map(userCaster::userMongoToUser) // Convertir a formato User
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return Un Optional que puede contener el usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<User> findUserById(String id) {
        Optional<UserMongo> userMongo = userRepositoryNoSql.findById(id); // Buscar en la base de datos
        return userMongo.map(userCaster::userMongoToUser); // Convertir si se encuentra
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param user El usuario con los datos actualizados.
     * @return El usuario actualizado.
     */
    @Override
    public User updateUser(User user) {
        UserMongo userMongo = userCaster.userToUserMongo(user); // Conversión a formato Mongo
        UserMongo newUser = userRepositoryNoSql.save(userMongo); // Guardar en la base de datos
        return userCaster.userMongoToUser(newUser); // Convertir de nuevo a User
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    @Override
    public void deleteUser(String id) {
        userRepositoryNoSql.deleteById(id); // Eliminar de la base de datos
    }

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Un Optional que puede contener el usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<UserMongo> userMongo = userRepositoryNoSql.findByEmail(email); // Buscar en la base de datos
        return userMongo.map(userCaster::userMongoToUser); // Convertir si se encuentra
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que puede contener el usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        Optional<UserMongo> userMongo = userRepositoryNoSql.findByUsername(username); // Buscar en la base de datos
        return userMongo.map(userCaster::userMongoToUser); // Convertir si se encuentra
    }
}