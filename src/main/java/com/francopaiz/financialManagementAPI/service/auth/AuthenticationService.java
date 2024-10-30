package com.francopaiz.financialManagementAPI.service.auth;
import com.francopaiz.financialManagementAPI.dto.user.UserRequest;
import com.francopaiz.financialManagementAPI.dto.user.UserRequestLogin;
import com.francopaiz.financialManagementAPI.model.Role;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.repository.role.RoleRepository;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import com.francopaiz.financialManagementAPI.security.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para manejar la autenticación de usuarios.
 * Proporciona métodos para registrar nuevos usuarios y autenticar usuarios existentes.
 */
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userRequest Objeto que contiene la información del nuevo usuario.
     * @return El usuario creado.
     */
    public User signup(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setDateCreation(LocalDateTime.now());
        user.setDateUpdate(LocalDateTime.now());

        // Asigna el rol "ROLE_USER" al nuevo usuario
        Role role = roleRepository.findRoleByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("error creating user, could not assign a role"));
        user.getRoles().add(role);

        return userRepository.createUser(user);
    }

    /**
     * Autentica a un usuario en el sistema.
     *
     * @param userRequestLogin Objeto que contiene las credenciales del usuario.
     * @return El usuario autenticado.
     * @throws EntityNotFoundException Si no se encuentra el usuario.
     */
    public User login(UserRequestLogin userRequestLogin) {
        // Realiza la autenticación
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequestLogin.getUsername(),
                        userRequestLogin.getPassword()
                )
        );

        // Busca y retorna el usuario autenticado
        return userRepository.findUserByUsername(userRequestLogin.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("error authenticating user, could not find user"));
    }

    /**
     * Obtiene los nombres de los roles asociados a un usuario.
     *
     * @param user El usuario del cual se desean obtener los roles.
     * @return Una lista de nombres de roles.
     */
    public List<String> getRolesName(User user) {
        return user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toList());
    }
}