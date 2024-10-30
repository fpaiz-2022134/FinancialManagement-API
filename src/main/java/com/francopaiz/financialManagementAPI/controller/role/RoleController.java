package com.francopaiz.financialManagementAPI.controller.role;

import com.francopaiz.financialManagementAPI.model.Role;
import com.francopaiz.financialManagementAPI.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de roles.
 * Este controlador maneja las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los roles en la biblioteca.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService; // Servicio para la gestión de roles.

    /**
     * Maneja la creación de un nuevo rol.
     *
     * @param role El objeto Role que contiene la información del rol.
     * @return Un objeto ResponseEntity que contiene el rol creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role); // Crea el rol utilizando el servicio de roles.
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED); // Devuelve el rol creado con estado HTTP 201.
    }

    /**
     * Maneja la solicitud para obtener todos los roles.
     *
     * @return Un objeto ResponseEntity que contiene una lista de roles y un estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getRoles(); // Obtiene todos los roles utilizando el servicio de roles.
        return new ResponseEntity<>(roles, HttpStatus.OK); // Devuelve la lista de roles con estado HTTP 200.
    }

    /**
     * Maneja la solicitud para obtener un rol específico por su ID.
     *
     * @param idRole El ID del rol que se desea obtener.
     * @return Un objeto ResponseEntity que contiene el rol encontrado y un estado HTTP 200 (OK).
     */
    @GetMapping("/{idRole}")
    public ResponseEntity<Role> getRole(@PathVariable("idRole") String idRole) {
        Role role = roleService.findRoleById(idRole); // Busca el rol utilizando el servicio de roles.
        return new ResponseEntity<>(role, HttpStatus.OK); // Devuelve el rol encontrado con estado HTTP 200.
    }

    /**
     * Maneja la actualización de un rol existente.
     *
     * @param idRole El ID del rol que se va a actualizar.
     * @param role Los datos actualizados del rol.
     * @return Un objeto ResponseEntity que contiene el rol actualizado y un estado HTTP 200 (OK).
     */
    @PutMapping("{idRole}")
    public ResponseEntity<Role> updateRole(@PathVariable("idRole") String idRole, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(idRole, role); // Actualiza el rol utilizando el servicio de roles.
        return new ResponseEntity<>(updatedRole, HttpStatus.OK); // Devuelve el rol actualizado con estado HTTP 200.
    }

    /**
     * Maneja la eliminación de un rol existente.
     *
     * @param idRole El ID del rol que se va a eliminar.
     * @return Un objeto ResponseEntity que contiene un mensaje de éxito y un estado HTTP 200 (OK).
     */
    @DeleteMapping("/{idRole}")
    public ResponseEntity<Map<String, String>> deleteRole(@PathVariable("idRole") String idRole) {
        roleService.deleteRole(idRole); // Elimina el rol utilizando el servicio de roles.
        Map<String, String> response = new HashMap<>();
        response.put("message", "Role deleted successfully"); // Mensaje de éxito.
        return new ResponseEntity<>(response, HttpStatus.OK); // Devuelve el mensaje con estado HTTP 200.
    }
}