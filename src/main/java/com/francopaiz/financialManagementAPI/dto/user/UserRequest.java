package com.francopaiz.financialManagementAPI.dto.user;

import lombok.Data;

/**
 * DTO (Data Transfer Object) para la creación de usuarios.
 * Esta clase representa la estructura de los datos necesarios para crear un nuevo usuario.
 */
@Data
public class UserRequest {

    /**
     * Nombre del usuario.
     * Este campo se utiliza para almacenar el nombre completo del usuario.
     */
    private String name;

    /**
     * Nombre de usuario.
     * Este campo se utiliza para almacenar el nombre de usuario único que el usuario elegirá.
     */
    private String username;

    /**
     * Correo electrónico del usuario.
     * Este campo se utiliza para almacenar la dirección de correo electrónico del usuario, que se utilizará para la comunicación y recuperación de cuentas.
     */
    private String email;

    /**
     * Contraseña del usuario.
     * Este campo se utiliza para almacenar la contraseña del usuario, que se cifrará antes de almacenarse en la base de datos.
     */
    private String password;
}