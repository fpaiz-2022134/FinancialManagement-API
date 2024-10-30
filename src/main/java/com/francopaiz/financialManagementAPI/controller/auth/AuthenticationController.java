package com.francopaiz.financialManagementAPI.controller.auth;


import com.francopaiz.financialManagementAPI.dto.user.UserRequest;
import com.francopaiz.financialManagementAPI.dto.user.UserRequestLogin;
import com.francopaiz.financialManagementAPI.dto.user.UserResponseLogin;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.service.auth.AuthenticationService;
import com.francopaiz.financialManagementAPI.service.auth.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para la autenticación de usuarios.
 * Este controlador maneja las operaciones de registro y autenticación de usuarios.
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService; // Servicio para la gestión de JWT.
    private final AuthenticationService authenticationService; // Servicio para la autenticación de usuarios.

    /**
     * Maneja la solicitud de registro de un nuevo usuario.
     *
     * @param registerUserDto Datos del usuario que se va a registrar.
     * @return Un objeto ResponseEntity que contiene el usuario registrado y un estado HTTP 201 (CREATED).
     */
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto); // Registra al usuario utilizando el servicio de autenticación.
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED); // Devuelve el usuario registrado con estado HTTP 201.
    }

    /**
     * Maneja la solicitud de inicio de sesión de un usuario.
     *
     * @param loginUserDto Datos del usuario que intenta iniciar sesión.
     * @return Un objeto ResponseEntity que contiene la respuesta de inicio de sesión y un estado HTTP 200 (OK).
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponseLogin> authenticate(@RequestBody UserRequestLogin loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto); // Autentica al usuario utilizando el servicio de autenticación.
        List<String> roles = authenticationService.getRolesName(authenticatedUser); // Obtiene los roles del usuario autenticado.
        String jwtToken = jwtService.generateToken(authenticatedUser, roles); // Genera un token JWT para el usuario autenticado.

        // Crea la respuesta de inicio de sesión que incluye el token y su tiempo de expiración.
        UserResponseLogin loginResponse = new UserResponseLogin();
        loginResponse.setToken(jwtToken); // Establece el token en la respuesta.
        loginResponse.setExpiresIn(jwtService.getExpirationTime()); // Establece el tiempo de expiración del token en la respuesta.

        return new ResponseEntity<>(loginResponse, HttpStatus.OK); // Devuelve la respuesta de inicio de sesión con estado HTTP 200.
    }
}