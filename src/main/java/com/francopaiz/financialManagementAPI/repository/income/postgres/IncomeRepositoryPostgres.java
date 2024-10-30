package com.francopaiz.financialManagementAPI.repository.income.postgres;

import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.postgres.IncomePostgres;
import com.francopaiz.financialManagementAPI.repository.income.IncomeRepository;
import com.francopaiz.financialManagementAPI.caster.IncomeCaster;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de ingresos para PostgreSQL.
 * Esta clase se encarga de la persistencia de datos relacionados
 * con los ingresos en una base de datos PostgreSQL.
 */
@Profile("postgres") // Activa esta implementación cuando el perfil 'postgres' está activo.
@Repository // Indica que esta clase es un repositorio de acceso a datos.
@RequiredArgsConstructor
public class IncomeRepositoryPostgres implements IncomeRepository {

    private final IncomeRepositoryJpa incomeRepositoryJpa; // Repositorio JPA para operaciones CRUD.
    private final IncomeCaster incomeCaster; // Utilidad para convertir entre entidades.

    /*public IncomeRepositoryPostgres(IncomeRepositoryJpa incomeRepositoryJpa, IncomeCaster incomeCaster) {
        this.incomeRepositoryJpa = incomeRepositoryJpa;
        this.incomeCaster = incomeCaster;
    }
*/
    /**
     * Crea un nuevo ingreso en la base de datos.
     *
     * @param income El ingreso a crear.
     * @return El ingreso creado.
     */
    @Override
    public Income createIncome(Income income) {
        IncomePostgres incomePostgres = incomeCaster.incomeToIncomePostgres(income); // Convierte a IncomePostgres.

        System.out.println("PRUEBAAAAAAAA" + incomePostgres);

        IncomePostgres newIncome = incomeRepositoryJpa.save(incomePostgres); // Guarda el ingreso en la base de datos.
        System.out.println("Nuevo INCOME: " + newIncome);
        return incomeCaster.incomePostgresToIncome(newIncome); // Convierte y retorna el ingreso creado.
    }

    /**
     * Recupera todos los ingresos de la base de datos.
     *
     * @return Una lista de ingresos.
     */
    @Override
    public List<Income> getIncomes() {
        return incomeRepositoryJpa.findAll().stream() // Obtiene todos los ingresos de la base de datos.
                .map(incomeCaster::incomePostgresToIncome) // Convierte cada ingreso a Income.
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
        Optional<IncomePostgres> incomePostgres = incomeRepositoryJpa.findById(Long.parseLong(idIncome)); // Busca el ingreso por ID.
        System.out.println(incomePostgres);
        return incomePostgres.map(incomeCaster::incomePostgresToIncome); // Convierte el resultado a un Optional de Income.
    }

    /**
     * Actualiza un ingreso existente en la base de datos.
     *
     * @param income El ingreso a actualizar.
     * @return El ingreso actualizado.
     */
    @Override
    public Income updateIncome(Income income) {
        IncomePostgres incomePostgres = incomeCaster.incomeToIncomePostgres(income); // Convierte a IncomePostgres.
        IncomePostgres updatedIncome = incomeRepositoryJpa.save(incomePostgres); // Guarda el ingreso actualizado.
        return incomeCaster.incomePostgresToIncome(updatedIncome); // Convierte y retorna el ingreso actualizado.
    }

    /**
     * Elimina un ingreso de la base de datos por su ID.
     *
     * @param idIncome El ID del ingreso a eliminar.
     */
    @Override
    public void deleteIncome(String idIncome) {
        incomeRepositoryJpa.deleteById(Long.parseLong(idIncome)); // Elimina el ingreso por ID.
    }

    /**
     * Encuentra los ingresos asociados a un usuario específico.
     *
     * @param user El usuario cuyos ingresos se van a buscar.
     * @return Una lista de objetos Income que pertenecen al usuario.
     */
    @Override
    public List<Income> findByUser(User user) {
        return incomeRepositoryJpa.findByUser_Id(Long.valueOf(user.getId())).stream() // Obtiene los ingresos del usuario.
                .map(incomeCaster::incomePostgresToIncome) // Convierte cada ingreso a Income.
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
        return incomeRepositoryJpa.findByUser_IdAndDateBetween(Long.valueOf(user.getId()), startDate, endDate).stream() // Obtiene los ingresos del usuario en el rango de fechas.
                .map(incomeCaster::incomePostgresToIncome) // Convierte cada ingreso a Income.
                .collect(Collectors.toList()); // Recoge los ingresos en una lista.
    }
}