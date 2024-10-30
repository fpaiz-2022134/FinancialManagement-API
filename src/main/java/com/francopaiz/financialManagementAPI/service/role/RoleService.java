package com.francopaiz.financialManagementAPI.service.role;


import com.francopaiz.financialManagementAPI.model.Role;

import java.util.List;

/**
 * Interfaz que define los servicios relacionados con los roles en la biblioteca.
 * Proporciona métodos para crear, obtener, actualizar y eliminar roles.
 */
public interface RoleService {

    /**
     * Crea un nuevo rol en el sistema.
     *
     * @param role El objeto Role que representa el rol a crear.
     * @return El rol creado.
     */
    Role createRole(Role role);

    /**
     * Obtiene una lista de todos los roles disponibles en el sistema.
     *
     * @return Una lista de objetos Role.
     */
    List<Role> getRoles();

    /**
     * Busca un rol por su identificador único.
     *
     * @param idRole El identificador del rol a buscar.
     * @return El objeto Role correspondiente al identificador proporcionado.
     */
    Role findRoleById(String idRole);

    /**
     * Actualiza un rol existente en el sistema.
     *
     * @param idRole El identificador del rol a actualizar.
     * @param role El objeto Role que contiene los nuevos datos del rol.
     * @return El rol actualizado.
     */
    Role updateRole(String idRole, Role role);

    /**
     * Elimina un rol del sistema por su identificador único.
     *
     * @param idRole El identificador del rol a eliminar.
     */
    void deleteRole(String idRole);
}