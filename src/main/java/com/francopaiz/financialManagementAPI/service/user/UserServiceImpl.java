package com.francopaiz.financialManagementAPI.service.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import com.francopaiz.financialManagementAPI.dto.user.UserRequest;
import com.francopaiz.financialManagementAPI.dto.user.UserResponse;
import com.francopaiz.financialManagementAPI.dto.user.UserRequestUpdate;
import com.francopaiz.financialManagementAPI.caster.UserCaster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz UserService que proporciona métodos para manejar
 * las operaciones relacionadas con los usuarios en la biblioteca.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCaster userCaster;

    private static final String USER_NOT_FOUND = "User not found with ID: ";

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Obtiene una lista de todos los usuarios disponibles en el sistema.
     *
     * @return Una lista de objetos UserResponse que representan a los usuarios.
     */
    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.getUsers();
        return users.stream().map(userCaster::userToUserResponse).collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param idUser El identificador del usuario a buscar.
     * @return El objeto UserResponse correspondiente al identificador proporcionado.
     * @throws IllegalArgumentException si el formato del idUser es inválido para el perfil de PostgreSQL.
     * @throws EntityNotFoundException si no se encuentra el usuario.
     */
    @Override
    public UserResponse findUserById(String idUser) {
        if(profile.equals("postgres")) {
            try {
                Long id = Long.parseLong(idUser);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid idBook format for Postgres: " + idUser);
            }
        }
        User user = userRepository.findUserById(idUser)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + idUser));
        return userCaster.userToUserResponse(user);
    }

    /**
     * Actualiza la información de un usuario existente en el sistema.
     *
     * @param idUser El identificador del usuario a actualizar.
     * @param userRequestUpdate El objeto UserRequestUpdate que contiene los nuevos datos del usuario.
     * @return El objeto UserResponse actualizado.
     * @throws IllegalArgumentException si el formato del idUser es inválido para el perfil de PostgreSQL.
     * @throws EntityNotFoundException si no se encuentra el usuario.
     */
    @Override
    public UserResponse updateUser(String idUser, UserRequestUpdate userRequestUpdate) {
        if(profile.equals("postgres")) {
            try {
                Long id = Long.parseLong(idUser);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid idBook format for Postgres: " + idUser);
            }
        }
        User user = userRepository.findUserById(idUser)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + idUser));
        user.setName(userRequestUpdate.getName());
        user.setUsername(userRequestUpdate.getUsername());
        user.setEmail(userRequestUpdate.getEmail());
        User update = userRepository.updateUser(user);
        return userCaster.userToUserResponse(update);
    }

    /**
     * Elimina un usuario del sistema por su identificador único.
     *
     * @param idUser El identificador del usuario a eliminar.
     * @throws IllegalArgumentException si el formato del idUser es inválido para el perfil de PostgreSQL.
     * @throws EntityNotFoundException si no se encuentra el usuario.
     */
    @Override
    public void deleteUser(String idUser) {
        if(profile.equals("postgres")) {
            try {
                Long id = Long.parseLong(idUser);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid idBook format for Postgres: " + idUser);
            }
        }
        userRepository.findUserById(idUser)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND + idUser));
        userRepository.deleteUser(idUser);
    }
}