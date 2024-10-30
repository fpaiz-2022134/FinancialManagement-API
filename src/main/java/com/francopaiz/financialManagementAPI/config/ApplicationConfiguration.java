package com.francopaiz.financialManagementAPI.config;

import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Esta clase configura la seguridad de la aplicación, incluyendo la gestión de la autenticación de usuarios.
 * Utiliza la anotación @Configuration para indicar que esta clase contiene definiciones de beans.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {


    private final UserRepository userRepository;; // Repositorio de usuarios utilizado para acceder a los datos de los usuarios.

    /**
     * Proporciona un UserDetailsService que busca usuarios por su nombre de usuario.
     *
     * @return Un UserDetailsService que lanza una excepción si no se encuentra el usuario.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Excepción si el usuario no existe.
    }

    /**
     * Proporciona un codificador de contraseñas usando BCrypt.
     *
     * @return Un BCryptPasswordEncoder para cifrar y verificar contraseñas.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Implementación de codificación de contraseñas segura.
    }

    /**
     * Proporciona un AuthenticationManager para manejar la autenticación de usuarios.
     *
     * @param config Configuración de autenticación.
     * @return Un AuthenticationManager configurado.
     * @throws Exception Si hay un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Obtiene el AuthenticationManager del contexto de configuración.
    }

    /**
     * Proporciona un AuthenticationProvider que utiliza el UserDetailsService y el codificador de contraseñas.
     *
     * @return Un AuthenticationProvider configurado para autenticar usuarios.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Crea un nuevo DaoAuthenticationProvider.

        authProvider.setUserDetailsService(userDetailsService()); // Establece el UserDetailsService.
        authProvider.setPasswordEncoder(passwordEncoder()); // Establece el codificador de contraseñas.

        return authProvider; // Devuelve el AuthenticationProvider configurado.
    }
}