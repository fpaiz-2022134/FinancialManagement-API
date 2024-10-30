package com.francopaiz.financialManagementAPI.config;

import com.francopaiz.financialManagementAPI.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtro de autenticación JWT que verifica el token de autenticación en cada solicitud.
 * Extiende OncePerRequestFilter para garantizar que se ejecute una sola vez por solicitud.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver; // Manejo de excepciones de filtros.
    private final JwtService jwtService; // Servicio para manejar operaciones relacionadas con JWT.


    private final UserDetailsService userDetailsService; // Servicio para cargar detalles de usuarios.

    /**
     * Método principal que realiza la filtración y autenticación de la solicitud.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros que deben ejecutarse.
     * @throws ServletException Si hay un error de servlet.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Obtener el encabezado de autorización.

        // Verificar si el encabezado está presente y comienza con "Bearer ".
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continuar con la cadena de filtros si no hay token.
            return;
        }

        try {
            final String jwt = authHeader.substring(7); // Extraer el token JWT del encabezado.
            final String username = jwtService.extractUsername(jwt); // Extraer el nombre de usuario del token.

            // Obtener el objeto de autenticación actual.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Si el nombre de usuario no es nulo y no hay autenticación previa, se procede a autenticar.
            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // Cargar detalles del usuario.

                // Extraer roles del token JWT.
                List<String> roles = (List<String>) jwtService.extractClaim(jwt, claims -> claims.get("roles"));

                // Convertir roles a GrantedAuthority.
                Collection<? extends GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                System.out.println("roles: " + roles);

                // Verificar si el token es válido.
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Crear un objeto de autenticación basado en el usuario y roles.
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );

                    // Establecer los detalles de autenticación en el token.
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken); // Establecer autenticación en el contexto.
                }
            }

            filterChain.doFilter(request, response); // Continuar con la cadena de filtros.
        } catch (Exception exception) {
            // Manejar cualquier excepción que ocurra durante la filtración.
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}