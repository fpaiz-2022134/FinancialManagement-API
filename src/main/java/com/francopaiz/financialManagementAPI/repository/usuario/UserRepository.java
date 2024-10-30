package com.francopaiz.financialManagementAPI.repository.usuario;

import com.francopaiz.financialManagementAPI.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el repositorio de usuarios.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad User.
 */
public interface UserRepository {

    /**
     * Crea un nuevo usuario en el repositorio.
     *
     * @param user El objeto User a crear.
     * @return El usuario creado.
     */
    User createUser(User user);

    /**
     * Obtiene todos los usuarios del repositorio.
     *
     * @return Una lista de objetos User.
     */
    List<User> getUsers();

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<User> findUserById(String id);

    /**
     * Actualiza un usuario existente en el repositorio.
     *
     * @param user El objeto User con los datos actualizados.
     * @return El usuario actualizado.
     */
    User updateUser(User user);

    /**
     * Elimina un usuario del repositorio por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    void deleteUser(String id);

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<User> findUserByUsername(String username);
}