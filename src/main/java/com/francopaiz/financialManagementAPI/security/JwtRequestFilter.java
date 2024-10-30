package com.francopaiz.financialManagementAPI.security;

import com.francopaiz.financialManagementAPI.dto.user.UserResponse;
import com.francopaiz.financialManagementAPI.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Extraer el token del encabezado 'token'
        String token = request.getHeader("token");

        // Validar el token y autenticar al usuario
        if (token != null && jwtTokenUtil.validateToken(token)) {
            String userId = jwtTokenUtil.getUserIdFromToken(token);

            // Buscar el usuario usando el servicio de usuario
            UserResponse userResponse = userService.findUserById(userId);

            // Crear el objeto de autenticación
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userResponse.getId(), null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    // Método para extraer el token del encabezado
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
