package com.francopaiz.financialManagementAPI.dto.user;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la respuesta de un usuario.
 * Esta clase representa la estructura de los datos que se envían al cliente al recuperar la información de un usuario del sistema.
 */
@Data
public class UserResponse {

    /**
     * Identificador único del usuario.
     * Este campo almacena el ID del usuario en el sistema.
     */
    private String id;

    /**
     * Nombre del usuario.
     * Este campo almacena el nombre completo del usuario.
     */
    private String name;

    /**
     * Nombre de usuario.
     * Este campo almacena el nombre de usuario que el usuario utiliza para iniciar sesión.
     */
    private String username;

    /**
     * Correo electrónico.
     * Este campo almacena la dirección de correo electrónico del usuario.
     */
    private String email;

    /**
     * Fecha de creación de la cuenta del usuario.
     * Este campo almacena la fecha y hora en que se creó la cuenta del usuario.
     */
    private LocalDateTime dateCreation;

    /**
     * Fecha de la última actualización de la información del usuario.
     * Este campo almacena la fecha y hora de la última modificación de los datos del usuario.
     */
    private LocalDateTime dateUpdate;
}