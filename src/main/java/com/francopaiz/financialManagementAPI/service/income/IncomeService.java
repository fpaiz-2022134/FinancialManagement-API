    package com.francopaiz.financialManagementAPI.service.income;

    import com.francopaiz.financialManagementAPI.model.Income;
    import com.francopaiz.financialManagementAPI.model.User;

    import java.util.List;

    /**
     * Interfaz para el servicio de ingresos.
     * Define las operaciones que se pueden realizar sobre la entidad Income.
     */
    public interface IncomeService {

        /**
         * Obtiene todos los ingresos registrados.
         * @return Lista de todos los ingresos.
         */
        List<Income> getIncomes();

        /**
         * Busca un ingreso por su ID.
         * @param id El ID del ingreso.
         * @return El ingreso encontrado o null si no se encuentra.
         */
        Income findIncomeById(String id);

        /**
         * Crea un nuevo ingreso.
         * @param income El objeto Income a crear.
         * @return El ingreso creado.
         */
        Income createIncome(Income income);

        /**
         * Actualiza un ingreso existente.
         * @param id El ID del ingreso a actualizar.
         * @param income Los datos actualizados del ingreso.
         * @return El ingreso actualizado.
         */
        Income updateIncome(String id, Income income);

        /**
         * Elimina un ingreso por su ID.
         * @param id El ID del ingreso a eliminar.
         */
        void deleteIncome(String id);

        /**
         * Busca los ingresos asociados a un usuario específico.
         * @param user El usuario asociado a los ingresos.
         * @return Lista de ingresos del usuario.
         */
        List<Income> findByUser(User user);

        /**
         * Busca los ingresos del usuario autenticado.
         * @return Lista de ingresos del usuario autenticado.
         */
        List<Income> findIncomesForAuthenticatedUser();
    }
