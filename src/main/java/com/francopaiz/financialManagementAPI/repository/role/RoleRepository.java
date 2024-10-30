package com.francopaiz.financialManagementAPI.repository.role;


import com.francopaiz.financialManagementAPI.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el repositorio de roles.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad Role.
 */
public interface RoleRepository {

    /**
     * Crea un nuevo rol en el repositorio.
     *
     * @param role El objeto Role a crear.
     * @return El rol creado.
     */
    Role create(Role role);

    /**
     * Obtiene todos los roles del repositorio.
     *
     * @return Una lista de objetos Role.
     */
    List<Role> getRoles();

    /**
     * Busca un rol por su ID.
     *
     * @param idRole El ID del rol a buscar.
     * @return Un Optional que contiene el rol si se encuentra, o vacío si no.
     */
    Optional<Role> findRoleById(String idRole);

    /**
     * Actualiza un rol existente en el repositorio.
     *
     * @param role El objeto Role con los datos actualizados.
     * @return El rol actualizado.
     */
    Role updateRole(Role role);

    /**
     * Elimina un rol del repositorio por su ID.
     *
     * @param idRole El ID del rol a eliminar.
     */
    void deleteRole(String idRole);

    /**
     * Busca un rol por su nombre.
     *
     * @param roleName El nombre del rol a buscar.
     * @return Un Optional que contiene el rol si se encuentra, o vacío si no.
     */
    Optional<Role> findRoleByName(String roleName);
}