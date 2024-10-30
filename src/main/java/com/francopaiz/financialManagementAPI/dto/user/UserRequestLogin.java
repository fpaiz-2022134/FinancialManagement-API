package com.francopaiz.financialManagementAPI.dto.user;

import lombok.Data;

/**
 * DTO (Data Transfer Object) para la solicitud de inicio de sesión de un usuario.
 * Esta clase representa la estructura de los datos necesarios para autenticar a un usuario en el sistema.
 */
@Data
public class UserRequestLogin {

    /**
     * Nombre de usuario.
     * Este campo se utiliza para almacenar el nombre de usuario que el usuario ingresará para iniciar sesión.
     */
    private String username;

    /**
     * Contraseña del usuario.
     * Este campo se utiliza para almacenar la contraseña que el usuario ingresará para autenticar su cuenta.
     */
    private String password;
}