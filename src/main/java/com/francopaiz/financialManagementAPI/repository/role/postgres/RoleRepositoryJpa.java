package com.francopaiz.financialManagementAPI.repository.role.postgres;
import com.francopaiz.financialManagementAPI.model.postgres.RolePostgres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones de acceso a datos de roles en PostgreSQL.
 * Extiende JpaRepository para proporcionar operaciones CRUD sobre RolePostgres.
 */

public interface RoleRepositoryJpa extends JpaRepository<RolePostgres, Long> {

    /**
     * Busca un rol por su nombre.
     *
     * @param role El nombre del rol a buscar.
     * @return Un Optional que contiene el rol encontrado, o vacío si no se encontró.
     */
    Optional<RolePostgres> findByRole(String role);
}