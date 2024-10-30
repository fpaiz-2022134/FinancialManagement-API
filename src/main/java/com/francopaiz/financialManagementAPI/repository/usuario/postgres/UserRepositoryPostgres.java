package com.francopaiz.financialManagementAPI.repository.usuario.postgres;

import com.francopaiz.financialManagementAPI.caster.UserCaster;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.postgres.UserPostgres;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de usuarios para la base de datos PostgreSQL.
 * Esta clase gestiona las operaciones CRUD para la entidad User.
 */
@Profile("postgres")
@Repository
@RequiredArgsConstructor
public class UserRepositoryPostgres implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa; // Repositorio JPA para operaciones CRUD en PostgreSQL

    private final UserCaster userCaster; // Clase para convertir entre User y UserPostgres

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param user El objeto User a crear.
     * @return El usuario creado.
     */
    @Override
    public User createUser(User user) {
        UserPostgres userPostgres = userCaster.userToUserPostgres(user);
        UserPostgres newUser = userRepositoryJpa.save(userPostgres);
        return userCaster.userPostgresToUser(newUser);
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return Una lista de usuarios.
     */
    @Override
    public List<User> getUsers() {
        return userRepositoryJpa.findAll().stream()
                .map(userCaster::userPostgresToUser)
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<User> findUserById(String id) {
        Optional<UserPostgres> userPostgres = userRepositoryJpa.findById(Long.parseLong(id));
        return userPostgres.map(userCaster::userPostgresToUser);
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param user El objeto User con los datos actualizados.
     * @return El usuario actualizado.
     */
    @Override
    public User updateUser(User user) {
        UserPostgres userPostgres = userCaster.userToUserPostgres(user);
        UserPostgres newUser = userRepositoryJpa.save(userPostgres);
        return userCaster.userPostgresToUser(newUser);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param idUser El ID del usuario a eliminar.
     */
    @Override
    public void deleteUser(String idUser) {
        userRepositoryJpa.deleteById(Long.parseLong(idUser));
    }

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<UserPostgres> userPostgres = userRepositoryJpa.findByEmail(email);
        return userPostgres.map(userCaster::userPostgresToUser);
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        Optional<UserPostgres> userPostgres = userRepositoryJpa.findByUsername(username);
        return userPostgres.map(userCaster::userPostgresToUser);
    }
}