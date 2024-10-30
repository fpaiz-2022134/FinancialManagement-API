package com.francopaiz.financialManagementAPI.service.user;

import com.francopaiz.financialManagementAPI.dto.user.UserRequestUpdate;
import com.francopaiz.financialManagementAPI.dto.user.UserResponse;
import com.francopaiz.financialManagementAPI.model.User;
import java.util.List;

/**
 * Interfaz que define los servicios relacionados con los usuarios en la biblioteca.
 * Proporciona métodos para obtener, actualizar y eliminar usuarios.
 */
public interface UserService {

    /**
     * Obtiene una lista de todos los usuarios disponibles en el sistema.
     *
     * @return Una lista de objetos UserResponse que representan a los usuarios.
     */
    List<UserResponse> getUsers();

    /**
     * Busca un usuario por su identificador único.
     *
     * @param idUser El identificador del usuario a buscar.
     * @return El objeto UserResponse correspondiente al identificador proporcionado.
     */
    UserResponse findUserById(String idUser);

    /**
     * Actualiza la información de un usuario existente en el sistema.
     *
     * @param idUser El identificador del usuario a actualizar.
     * @param userRequestUpdate El objeto UserRequestUpdate que contiene los nuevos datos del usuario.
     * @return El objeto UserResponse actualizado.
     */
    UserResponse updateUser(String idUser, UserRequestUpdate userRequestUpdate);

    /**
     * Elimina un usuario del sistema por su identificador único.
     *
     * @param idUser El identificador del usuario a eliminar.
     */
    void deleteUser(String idUser);
}