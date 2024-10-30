package com.francopaiz.financialManagementAPI.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * Esta clase define las reglas de seguridad para las solicitudes HTTP y configura el filtrado JWT.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider; // Proveedor de autenticación.
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Filtro para la autenticación JWT.

    /**
     * Configura la cadena de filtros de seguridad de la aplicación.
     *
     * @param http Objeto HttpSecurity para personalizar la configuración de seguridad.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desactiva la protección CSRF, ya que se utiliza JWT.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**", "/").permitAll()  // Permitir acceso a /usuarios sin autenticación
                        .requestMatchers("/api/v1/role/**").permitAll()
                        .requestMatchers("/api/v1/user/**").authenticated()
                        .requestMatchers("/api/v1/categories/**").authenticated()
                        .requestMatchers("/api/v1/incomes/**").authenticated()
                        .requestMatchers("/api/v1/expenses/**").authenticated()
                        .requestMatchers("/api/v1/summaries/**").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Establece la gestión de sesión como sin estado.
                .authenticationProvider(authenticationProvider) // Establece el proveedor de autenticación.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Añade el filtro JWT antes del filtro de autenticación por nombre de usuario y contraseña.

        return http.build(); // Construye y devuelve la cadena de filtros de seguridad.
    }
}