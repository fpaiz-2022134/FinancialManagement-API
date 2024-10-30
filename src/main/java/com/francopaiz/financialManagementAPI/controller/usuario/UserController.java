package com.francopaiz.financialManagementAPI.controller.usuario;

import com.francopaiz.financialManagementAPI.dto.user.UserRequestUpdate;
import com.francopaiz.financialManagementAPI.dto.user.UserResponse;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de usuarios.
 * Este controlador maneja las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los usuarios en la biblioteca.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService; // Servicio para la gestión de usuarios.

    /**
     * Maneja la solicitud para obtener todos los usuarios.
     *
     * @return Un objeto ResponseEntity que contiene una lista de usuarios y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> userResponses = userService.getUsers(); // Obtiene todos los usuarios utilizando el servicio de usuarios.
        return new ResponseEntity<>(userResponses, HttpStatus.OK); // Devuelve la lista de usuarios con estado HTTP 200.
    }

    /**
     * Maneja la solicitud para obtener un usuario específico por su ID.
     *
     * @param idUser El ID del usuario que se desea obtener.
     * @return Un objeto ResponseEntity que contiene el usuario encontrado y un estado HTTP 200 (OK).
     */
    @GetMapping("/{idUser}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("idUser") String idUser) {
        UserResponse userResponse = userService.findUserById(idUser); // Busca el usuario utilizando el servicio de usuarios.
        return new ResponseEntity<>(userResponse, HttpStatus.OK); // Devuelve el usuario encontrado con estado HTTP 200.
    }

    /**
     * Maneja la actualización de un usuario existente.
     *
     * @param idUser El ID del usuario que se va a actualizar.
     * @param userRequestUpdate Los datos actualizados del usuario.
     * @return Un objeto ResponseEntity que contiene el usuario actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("/{idUser}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("idUser") String idUser, @RequestBody UserRequestUpdate userRequestUpdate) {
        UserResponse userResponse = userService.updateUser(idUser, userRequestUpdate); // Actualiza el usuario utilizando el servicio de usuarios.
        return new ResponseEntity<>(userResponse, HttpStatus.OK); // Devuelve el usuario actualizado con estado HTTP 200.
    }

    /**
     * Maneja la eliminación de un usuario existente.
     *
     * @param idUser El ID del usuario que se va a eliminar.
     * @return Un objeto ResponseEntity que contiene un mensaje de éxito y un estado HTTP 200 (OK).
     */
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("idUser") String idUser) {
        userService.deleteUser(idUser); // Elimina el usuario utilizando el servicio de usuarios.
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully"); // Mensaje de éxito.
        return new ResponseEntity<>(response, HttpStatus.OK); // Devuelve el mensaje con estado HTTP 200.
    }
}