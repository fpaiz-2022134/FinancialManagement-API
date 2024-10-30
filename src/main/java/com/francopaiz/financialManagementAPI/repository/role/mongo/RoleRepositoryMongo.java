package com.francopaiz.financialManagementAPI.repository.role.mongo;


import com.francopaiz.financialManagementAPI.caster.RoleCaster;
import com.francopaiz.financialManagementAPI.model.Role;
import com.francopaiz.financialManagementAPI.model.mongo.RoleMongo;
import com.francopaiz.financialManagementAPI.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de roles utilizando MongoDB.
 * Esta clase se encarga de la persistencia de datos relacionados con
 * los roles en una base de datos NoSQL y se activa solo cuando el perfil "mongo" está en uso.
 */
@Profile("mongo")
@RequiredArgsConstructor
@Repository
public class RoleRepositoryMongo implements RoleRepository {

    // Repositorio NoSQL para operaciones de base de datos
    private final RoleRepositoryNoSql roleRepositoryNoSql;

    // Utilidad para la conversión entre objetos Role y RoleMongo
    private final RoleCaster roleCaster;

    /**
     * Crea un nuevo rol en la base de datos.
     *
     * @param role Objeto Role que representa el rol a crear.
     * @return El objeto Role creado.
     */
    @Override
    public Role create(Role role) {
        // Convierte el objeto Role a RoleMongo para persistencia
        RoleMongo roleMongo = roleCaster.roleToRoleMongo(role);
        // Guarda el rol en la base de datos y obtiene el nuevo objeto
        RoleMongo newMongo = roleRepositoryNoSql.save(roleMongo);
        // Convierte el objeto RoleMongo guardado de nuevo a Role y lo retorna
        return roleCaster.roleMongoToRole(newMongo);
    }

    /**
     * Obtiene una lista de todos los roles en la base de datos.
     *
     * @return Lista de objetos Role.
     */
    @Override
    public List<Role> getRoles() {
        // Busca todos los roles en la base de datos y los convierte a objetos Role
        return roleRepositoryNoSql.findAll().stream()
                .map(roleCaster::roleMongoToRole)
                .collect(Collectors.toList());
    }

    /**
     * Busca un rol por su ID.
     *
     * @param idRole ID del rol que se desea encontrar.
     * @return Un Optional que puede contener el objeto Role si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Role> findRoleById(String idRole) {
        // Busca el rol en la base de datos y convierte el resultado a Role
        Optional<RoleMongo> roleMongo = roleRepositoryNoSql.findById(idRole);
        return roleMongo.map(roleCaster::roleMongoToRole);
    }

    /**
     * Actualiza la información de un rol existente.
     *
     * @param role Objeto Role que contiene la información actualizada del rol.
     * @return El objeto Role actualizado.
     */
    @Override
    public Role updateRole(Role role) {
        // Convierte el objeto Role a RoleMongo para la actualización
        RoleMongo roleMongo = roleCaster.roleToRoleMongo(role);
        // Guarda el rol actualizado en la base de datos y obtiene el nuevo objeto
        RoleMongo newMongo = roleRepositoryNoSql.save(roleMongo);
        // Convierte el objeto RoleMongo guardado de nuevo a Role y lo retorna
        return roleCaster.roleMongoToRole(newMongo);
    }

    /**
     * Elimina un rol de la base de datos por su ID.
     *
     * @param idRole ID del rol que se desea eliminar.
     */
    @Override
    public void deleteRole(String idRole) {
        // Elimina el rol de la base de datos utilizando su ID
        roleRepositoryNoSql.deleteById(idRole);
    }

    /**
     * Busca un rol por su nombre.
     *
     * @param roleName Nombre del rol que se desea encontrar.
     * @return Un Optional que puede contener el objeto Role si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Role> findRoleByName(String roleName) {
        // Busca el rol en la base de datos utilizando su nombre y convierte el resultado a Role
        Optional<RoleMongo> roleMongo = roleRepositoryNoSql.findByRole(roleName);
        return roleMongo.map(roleCaster::roleMongoToRole);
    }
}