package com.francopaiz.financialManagementAPI.dto.user;


import lombok.Data;

/**
 * DTO (Data Transfer Object) para la respuesta de inicio de sesión de un usuario.
 * Esta clase representa la estructura de los datos que se envían al cliente al autenticar a un usuario en el sistema.
 */
@Data
public class UserResponseLogin {

    /**
     * Token de acceso generado para el usuario autenticado.
     * Este campo almacena el token JWT que se utiliza para autenticar al usuario en solicitudes posteriores.
     */
    private String token;

    /**
     * Tiempo de expiración del token en milisegundos.
     * Este campo indica cuánto tiempo (en milisegundos) es válido el token antes de que expire.
     */
    private long expiresIn;
}