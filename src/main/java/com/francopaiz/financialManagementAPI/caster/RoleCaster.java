package com.francopaiz.financialManagementAPI.caster;


import com.francopaiz.financialManagementAPI.model.Role;
import com.francopaiz.financialManagementAPI.model.mongo.RoleMongo;
import com.francopaiz.financialManagementAPI.model.postgres.RolePostgres;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para realizar conversiones entre diferentes representaciones
 * de un rol, específicamente entre las entidades Role, RolePostgres y RoleMongo.
 */
@Component
public class RoleCaster {

    /**
     * Convierte un objeto Role a un objeto RolePostgres.
     *
     * @param role El objeto Role que se va a convertir.
     * @return Un objeto RolePostgres que representa el rol.
     */
    public RolePostgres roleToRolePostgres(Role role) {
        RolePostgres rolePostgres = new RolePostgres();
        rolePostgres.setIdRole((role.getIdRole() != null && !role.getIdRole().isEmpty())
                ? Long.parseLong(role.getIdRole()) : null);
        rolePostgres.setRole(role.getRole());
        return rolePostgres;
    }

    /**
     * Convierte un objeto RolePostgres a un objeto Role.
     *
     * @param rolePostgres El objeto RolePostgres que se va a convertir.
     * @return Un objeto Role que representa el rol.
     */
    public Role rolePostgresToRole(RolePostgres rolePostgres) {
        Role role = new Role();
        role.setIdRole(rolePostgres.getIdRole().toString());
        role.setRole(rolePostgres.getRole());
        return role;
    }

    /**
     * Convierte un objeto Role a un objeto RoleMongo.
     *
     * @param role El objeto Role que se va a convertir.
     * @return Un objeto RoleMongo que representa el rol.
     */
    public RoleMongo roleToRoleMongo(Role role) {
        RoleMongo roleMongo = new RoleMongo();
        roleMongo.setIdRole(role.getIdRole());
        roleMongo.setRole(role.getRole());
        return roleMongo;
    }

    /**
     * Convierte un objeto RoleMongo a un objeto Role.
     *
     * @param roleMongo El objeto RoleMongo que se va a convertir.
     * @return Un objeto Role que representa el rol.
     */
    public Role roleMongoToRole(RoleMongo roleMongo) {
        Role role = new Role();
        role.setIdRole(roleMongo.getIdRole());
        role.setRole(roleMongo.getRole());
        return role;
    }
}