package com.francopaiz.financialManagementAPI.service.role;


import com.francopaiz.financialManagementAPI.model.Role;
import com.francopaiz.financialManagementAPI.repository.role.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz RoleService que proporciona métodos para manejar
 * las operaciones relacionadas con los roles en la biblioteca.
 */
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Crea un nuevo rol en el sistema.
     *
     * @param role El objeto Role que representa el rol a crear.
     * @return El rol creado.
     */
    @Override
    public Role createRole(Role role) {
        return roleRepository.create(role);
    }

    /**
     * Obtiene una lista de todos los roles disponibles en el sistema.
     *
     * @return Una lista de objetos Role.
     */
    @Override
    public List<Role> getRoles() {
        return roleRepository.getRoles();
    }

    /**
     * Busca un rol por su identificador único.
     *
     * @param idRole El identificador del rol a buscar.
     * @return El objeto Role correspondiente al identificador proporcionado.
     * @throws IllegalArgumentException si el formato del idRole es inválido para el perfil de PostgreSQL.
     * @throws EntityNotFoundException si no se encuentra el rol.
     */
    @Override
    public Role findRoleById(String idRole) {
        if(profile.equals("postgres")) {
            try {
                Long id = Long.parseLong(idRole);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid idBook format for Postgres: " + idRole);
            }
        }
        return roleRepository.findRoleById(idRole)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + idRole));
    }

    /**
     * Actualiza un rol existente en el sistema.
     *
     * @param idRole El identificador del rol a actualizar.
     * @param role El objeto Role que contiene los nuevos datos del rol.
     * @return El rol actualizado.
     * @throws IllegalArgumentException si el formato del idRole es inválido para el perfil de PostgreSQL.
     * @throws EntityNotFoundException si no se encuentra el rol.
     */
    @Override
    public Role updateRole(String idRole, Role role) {
        if(profile.equals("postgres")) {
            try {
                Long id = Long.parseLong(idRole);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid idBook format for Postgres: " + idRole);
            }
        }
        Role roleFound = roleRepository.findRoleById(idRole)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + idRole));
        roleFound.setRole(role.getRole());
        return roleRepository.updateRole(roleFound);
    }

    /**
     * Elimina un rol del sistema por su identificador único.
     *
     * @param idRole El identificador del rol a eliminar.
     * @throws IllegalArgumentException si el formato del idRole es inválido para el perfil de PostgreSQL.
     * @throws EntityNotFoundException si no se encuentra el rol.
     */
    @Override
    public void deleteRole(String idRole) {
        if(profile.equals("postgres")) {
            try {
                Long id = Long.parseLong(idRole);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid idBook format for Postgres: " + idRole);
            }
        }
        roleRepository.findRoleById(idRole)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + idRole));
        roleRepository.deleteRole(idRole);
    }
}