package com.francopaiz.financialManagementAPI.repository.role.postgres;

import com.francopaiz.financialManagementAPI.caster.RoleCaster;
import com.francopaiz.financialManagementAPI.model.Role;
import com.francopaiz.financialManagementAPI.model.postgres.RolePostgres;
import com.francopaiz.financialManagementAPI.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio para gestionar roles en PostgreSQL.
 * Esta clase implementa la interfaz RoleRepository y utiliza RoleRepositoryJpa
 * para realizar las operaciones de acceso a datos.
 */
@Profile("postgres")
@RequiredArgsConstructor
@Repository
public class RoleRepositoryPostgres implements RoleRepository {

    private final RoleRepositoryJpa roleRepositoryJpa; // Repositorio JPA para operaciones CRUD
    private final RoleCaster roleCaster; // Utilidad para la conversión entre Role y RolePostgres

    @Override
    public Role create(Role role) {
        // Convierte el objeto Role a RolePostgres y lo guarda en la base de datos
        RolePostgres rolePostgres = roleCaster.roleToRolePostgres(role);
        RolePostgres newRole = roleRepositoryJpa.save(rolePostgres);
        // Retorna el objeto Role convertido de nuevo desde RolePostgres
        return roleCaster.rolePostgresToRole(newRole);
    }

    @Override
    public List<Role> getRoles() {
        // Obtiene todos los roles de la base de datos y los convierte a la representación de negocio
        return roleRepositoryJpa.findAll().stream()
                .map(roleCaster::rolePostgresToRole)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findRoleById(String idRole) {
        // Busca un rol por su ID y lo convierte a la representación de negocio
        Optional<RolePostgres> rolePostgres = roleRepositoryJpa.findById(Long.parseLong(idRole));
        return rolePostgres.map(roleCaster::rolePostgresToRole);
    }

    @Override
    public Role updateRole(Role role) {
        // Convierte el objeto Role a RolePostgres, lo actualiza en la base de datos
        RolePostgres rolePostgres = roleCaster.roleToRolePostgres(role);
        RolePostgres newRole = roleRepositoryJpa.save(rolePostgres);
        // Retorna el objeto Role convertido de nuevo desde RolePostgres
        return roleCaster.rolePostgresToRole(newRole);
    }

    @Override
    public void deleteRole(String idRole) {
        // Elimina el rol de la base de datos por su ID
        roleRepositoryJpa.deleteById(Long.parseLong(idRole));
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        // Busca un rol por su nombre y lo convierte a la representación de negocio
        Optional<RolePostgres> rolePostgres = roleRepositoryJpa.findByRole(roleName);
        return rolePostgres.map(roleCaster::rolePostgresToRole);
    }
}