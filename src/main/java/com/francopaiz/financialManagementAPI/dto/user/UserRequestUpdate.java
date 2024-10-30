package com.francopaiz.financialManagementAPI.dto.user;

import lombok.Data;

/**
 * DTO (Data Transfer Object) para la solicitud de actualización de un usuario.
 * Esta clase representa la estructura de los datos necesarios para actualizar la información de un usuario en el sistema.
 */
@Data
public class UserRequestUpdate {

    /**
     * Nombre del usuario.
     * Este campo se utiliza para almacenar el nuevo nombre del usuario que se actualizará en el sistema.
     */
    private String name;

    /**
     * Nombre de usuario.
     * Este campo se utiliza para almacenar el nuevo nombre de usuario que se actualizará en el sistema.
     */
    private String username;

    /**
     * Correo electrónico.
     * Este campo se utiliza para almacenar el nuevo correo electrónico del usuario que se actualizará en el sistema.
     */
    private String email;
}