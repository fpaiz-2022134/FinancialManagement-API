package com.francopaiz.financialManagementAPI.repository.role.mongo;

import com.francopaiz.financialManagementAPI.model.mongo.RoleMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Interfaz que extiende MongoRepository para realizar operaciones CRUD
 * sobre la colección de roles en MongoDB.
 *
 * Esta interfaz permite el acceso a la base de datos MongoDB para llevar a cabo
 * operaciones de creación, lectura, actualización y eliminación (CRUD)
 * sobre documentos de tipo RoleMongo.
 */
public interface RoleRepositoryNoSql extends MongoRepository<RoleMongo, String> {

    /**
     * Busca un rol en la base de datos por su nombre.
     *
     * @param role Nombre del rol a buscar.
     * @return Un Optional que puede contener el objeto RoleMongo si se encuentra,
     *         o vacío si no se encuentra ningún rol con ese nombre.
     */
    Optional<RoleMongo> findByRole(String role);
}