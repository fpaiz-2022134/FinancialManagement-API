package com.francopaiz.financialManagementAPI.service.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para manejar la generación y validación de tokens JWT.
 */
@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey; // Clave secreta para firmar el token.

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration; // Tiempo de expiración del token.

    /**
     * Extrae el nombre de usuario del token JWT.
     *
     * @param token El token JWT del cual se desea extraer el nombre de usuario.
     * @return El nombre de usuario extraído.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae una reclamación del token JWT utilizando un resolver de funciones.
     *
     * @param token          El token JWT del cual se desea extraer la reclamación.
     * @param claimsResolver Función que maneja la extracción de la reclamación.
     * @param <T>           Tipo de la reclamación que se está extrayendo.
     * @return El valor de la reclamación extraída.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT para un usuario y sus roles.
     *
     * @param userDetails Detalles del usuario para quien se genera el token.
     * @param roles      Lista de roles asociados al usuario.
     * @return El token JWT generado.
     */
    public String generateToken(UserDetails userDetails, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles); // Se añaden los roles como reclamo adicional.
        return generateToken(claims, userDetails);
    }

    /**
     * Genera un token JWT utilizando reclamaciones adicionales y detalles del usuario.
     *
     * @param extraClaims  Reclamaciones adicionales a incluir en el token.
     * @param userDetails  Detalles del usuario para quien se genera el token.
     * @return El token JWT generado.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Obtiene el tiempo de expiración del token JWT.
     *
     * @return El tiempo de expiración.
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Construye un token JWT con las reclamaciones, detalles del usuario y tiempo de expiración.
     *
     * @param extraClaims  Reclamaciones adicionales a incluir en el token.
     * @param userDetails  Detalles del usuario para quien se genera el token.
     * @param expiration   Tiempo de expiración del token.
     * @return El token JWT generado.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Verifica si el token JWT es válido.
     *
     * @param token       El token JWT a verificar.
     * @param userDetails Detalles del usuario para quien se valida el token.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token El token JWT a verificar.
     * @return true si el token ha expirado, false en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     *
     * @param token El token JWT del cual se desea extraer la fecha de expiración.
     * @return La fecha de expiración.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todas las reclamaciones del token JWT.
     *
     * @param token El token JWT del cual se desean extraer las reclamaciones.
     * @return Las reclamaciones extraídas.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave de firma a partir de la clave secreta.
     *
     * @return La clave de firma.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}