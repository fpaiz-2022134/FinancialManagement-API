package com.francopaiz.financialManagementAPI.caster;

import com.francopaiz.financialManagementAPI.dto.user.UserResponse;
import com.francopaiz.financialManagementAPI.model.Role;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.mongo.RoleMongo;
import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import com.francopaiz.financialManagementAPI.model.postgres.RolePostgres;
import com.francopaiz.financialManagementAPI.model.postgres.UserPostgres;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para realizar conversiones entre diferentes representaciones
 * de un usuario, específicamente entre las entidades User, UserPostgres, UserMongo y UserResponse.
 */
@RequiredArgsConstructor

@Component
public class UserCaster {

    private final RoleCaster roleCaster;

    /**
     * Convierte un objeto User a un objeto UserPostgres.
     *
     * @param user El objeto User que se va a convertir.
     * @return Un objeto UserPostgres que representa el usuario.
     */
    public UserPostgres userToUserPostgres(User user) {
        UserPostgres userPostgres = new UserPostgres();
        userPostgres.setId((user.getId() != null && !user.getId().isEmpty())
                ? Long.parseLong(user.getId()) : null);
        userPostgres.setName(user.getName());
        userPostgres.setUsername(user.getUsername());
        userPostgres.setEmail(user.getEmail());
        userPostgres.setPassword(user.getPassword());
        userPostgres.setDateCreation(user.getDateCreation());
        userPostgres.setDateUpdate(user.getDateUpdate());
        Set<RolePostgres> rolesPostgres = user.getRoles().stream()
                .map(roleCaster::roleToRolePostgres)
                .collect(Collectors.toSet());
        userPostgres.setRoles(rolesPostgres);
        return userPostgres;
    }

    /**
     * Convierte un objeto UserPostgres a un objeto User.
     *
     * @param userPostgres El objeto UserPostgres que se va a convertir.
     * @return Un objeto User que representa el usuario.
     */
    public User userPostgresToUser(UserPostgres userPostgres) {
        User user = new User();
        user.setId(String.valueOf(userPostgres.getId()));
        user.setName(userPostgres.getName());
        user.setUsername(userPostgres.getUsername());
        user.setEmail(userPostgres.getEmail());
        user.setPassword(userPostgres.getPassword());
        user.setDateCreation(userPostgres.getDateCreation());
        user.setDateUpdate(userPostgres.getDateUpdate());
        Set<Role> roles = userPostgres.getRoles().stream()
                .map(roleCaster::rolePostgresToRole)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    /**
     * Convierte un objeto User a un objeto UserMongo.
     *
     * @param user El objeto User que se va a convertir.
     * @return Un objeto UserMongo que representa el usuario.
     */
    public UserMongo userToUserMongo(User user) {
        UserMongo userMongo = new UserMongo();
        userMongo.setId(user.getId());
        userMongo.setName(user.getName());
        userMongo.setUsername(user.getUsername());
        userMongo.setEmail(user.getEmail());
        userMongo.setPassword(user.getPassword());
        userMongo.setDateCreation(user.getDateCreation());
        userMongo.setDateUpdate(user.getDateUpdate());
        Set<RoleMongo> roles = user.getRoles().stream()
                .map(roleCaster::roleToRoleMongo)
                .collect(Collectors.toSet());
        userMongo.setRoles(roles);
        return userMongo;
    }

    /**
     * Convierte un objeto UserMongo a un objeto User.
     *
     * @param userMongo El objeto UserMongo que se va a convertir.
     * @return Un objeto User que representa el usuario.
     */
    public User userMongoToUser(UserMongo userMongo) {
        User user = new User();
        user.setId(userMongo.getId());
        user.setName(userMongo.getName());
        user.setUsername(userMongo.getUsername());
        user.setEmail(userMongo.getEmail());
        user.setPassword(userMongo.getPassword());
        user.setDateCreation(userMongo.getDateCreation());
        user.setDateUpdate(userMongo.getDateUpdate());
        Set<Role> roles = userMongo.getRoles().stream()
                .map(roleCaster::roleMongoToRole)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    /**
     * Convierte un objeto User a un objeto UserResponse.
     *
     * @param user El objeto User que se va a convertir.
     * @return Un objeto UserResponse que representa la respuesta del usuario.
     */
    public UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setDateCreation(user.getDateCreation());
        userResponse.setDateUpdate(user.getDateUpdate());
        return userResponse;
    }


    /**
     * Convierte un objeto UserResponse a un objeto User.
     *
     * @param userResponse El objeto UserResponse que se va a convertir.
     * @return Un objeto User que representa el usuario.
     */
    public User userResponseToUser(UserResponse userResponse) {
        User user = new User();
        user.setId(userResponse.getId());
        user.setName(userResponse.getName());
        user.setUsername(userResponse.getUsername());
        user.setEmail(userResponse.getEmail());
        user.setDateCreation(userResponse.getDateCreation());
        user.setDateUpdate(userResponse.getDateUpdate());
        System.out.println(user);
        // Los roles y password no se incluyen en UserResponse, por lo tanto, se omiten en esta conversión
        return user;
    }
}