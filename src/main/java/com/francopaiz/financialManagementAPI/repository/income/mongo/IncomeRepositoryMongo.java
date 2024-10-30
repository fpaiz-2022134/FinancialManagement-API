package com.francopaiz.financialManagementAPI.repository.income.mongo;

import com.francopaiz.financialManagementAPI.caster.IncomeCaster;
import com.francopaiz.financialManagementAPI.caster.UserCaster;
import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.mongo.IncomeMongo;
import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import com.francopaiz.financialManagementAPI.repository.income.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de ingresos para MongoDB.
 * Esta clase se encarga de la persistencia de datos relacionados
 * con los ingresos en una base de datos MongoDB.
 */
@Profile("mongo") // Activa esta implementación cuando el perfil 'mongo' está activo.
@Repository // Indica que esta clase es un repositorio de acceso a datos.
@RequiredArgsConstructor
public class IncomeRepositoryMongo implements IncomeRepository {

    private final IncomeRepositoryNoSql incomeRepositoryNoSql; // Repositorio NoSQL para operaciones CRUD.
    private final IncomeCaster incomeCaster; // Utilidad para convertir entre entidades.
    private final UserCaster userCaster;
    /**
     * Crea un nuevo ingreso en la base de datos.
     *
     * @param income El ingreso a crear.
     * @return El ingreso creado.
     */
    @Override
    public Income createIncome(Income income) {
        IncomeMongo incomeMongo = incomeCaster.incomeToIncomeMongo(income); // Convierte a IncomeMongo.
        IncomeMongo newIncome = incomeRepositoryNoSql.save(incomeMongo); // Guarda el ingreso en la base de datos.
        return incomeCaster.incomeMongoToIncome(newIncome); // Convierte y retorna el ingreso creado.
    }

    /**
     * Recupera todos los ingresos de la base de datos.
     *
     * @return Una lista de ingresos.
     */
    @Override
    public List<Income> getIncomes() {
        return incomeRepositoryNoSql.findAll().stream() // Obtiene todos los ingresos de la base de datos.
                .map(incomeCaster::incomeMongoToIncome) // Convierte cada ingreso a Income.
                .collect(Collectors.toList()); // Recoge los ingresos en una lista.
    }

    /**
     * Encuentra un ingreso por su ID.
     *
     * @param idIncome El ID del ingreso a buscar.
     * @return Un Optional que contiene el ingreso si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Income> findIncomeById(String idIncome) {
        Optional<IncomeMongo> incomeMongo = incomeRepositoryNoSql.findById(idIncome); // Busca el ingreso por ID.
        return incomeMongo.map(incomeCaster::incomeMongoToIncome); // Convierte el resultado a un Optional de Income.
    }

    /**
     * Actualiza un ingreso existente en la base de datos.
     *
     * @param income El ingreso a actualizar.
     * @return El ingreso actualizado.
     */
    @Override
    public Income updateIncome(Income income) {
        IncomeMongo incomeMongo = incomeCaster.incomeToIncomeMongo(income); // Convierte a IncomeMongo.
        IncomeMongo updatedIncome = incomeRepositoryNoSql.save(incomeMongo); // Guarda el ingreso actualizado.
        return incomeCaster.incomeMongoToIncome(updatedIncome); // Convierte y retorna el ingreso actualizado.
    }

    /**
     * Elimina un ingreso de la base de datos por su ID.
     *
     * @param idIncome El ID del ingreso a eliminar.
     */
    @Override
    public void deleteIncome(String idIncome) {
        incomeRepositoryNoSql.deleteById(idIncome); // Elimina el ingreso por ID.
    }

    /**
     * Encuentra los ingresos asociados a un usuario específico.
     *
     * @param user El usuario cuyos ingresos se van a buscar.
     * @return Una lista de objetos Income que pertenecen al usuario.
     */
    @Override
    public List<Income> findByUser(User user) {
        UserMongo userMongo = userCaster.userToUserMongo(user);
        return incomeRepositoryNoSql.findByUser(userMongo).stream() // Obtiene los ingresos del usuario.
                .map(incomeCaster::incomeMongoToIncome) // Convierte cada ingreso a Income.
                .collect(Collectors.toList()); // Recoge los ingresos en una lista.
    }

    /**
     * Busca los ingresos de un usuario dentro de un rango de fechas específico.
     *
     * @param user El usuario cuyos ingresos se van a buscar.
     * @param startDate La fecha de inicio del rango.
     * @param endDate La fecha de fin del rango.
     * @return Una lista de objetos Income que pertenecen al usuario dentro del rango de fechas.
     */
    @Override
    public List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate) {

        System.out.println("User que cae en income: " + user);
        UserMongo userMongo = userCaster.userToUserMongo(user);
        System.out.println(incomeRepositoryNoSql.findByUserAndDateBetween(userMongo, startDate, endDate));

        return incomeRepositoryNoSql.findByUserAndDateBetween(userMongo, startDate, endDate).stream() // Obtiene los ingresos del usuario en el rango de fechas.
                .map(incomeCaster::incomeMongoToIncome) // Convierte cada ingreso a Income.
                .collect(Collectors.toList()); // Recoge los ingresos en una lista.
    }
}
