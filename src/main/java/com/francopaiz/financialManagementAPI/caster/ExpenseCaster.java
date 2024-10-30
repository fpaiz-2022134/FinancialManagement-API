package com.francopaiz.financialManagementAPI.caster;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.mongo.CategoryMongo;
import com.francopaiz.financialManagementAPI.model.mongo.ExpenseMongo;
import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import com.francopaiz.financialManagementAPI.model.postgres.CategoryPostgres;
import com.francopaiz.financialManagementAPI.model.postgres.ExpensePostgres;
import com.francopaiz.financialManagementAPI.model.postgres.UserPostgres;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCaster {

    private final UserCaster userCaster;
    private final CategoryCaster categoryCaster;

    // Constructor que permite la inyección de dependencias
    @Autowired
    public ExpenseCaster(UserCaster userCaster, CategoryCaster categoryCaster) {
        this.userCaster = userCaster;
        this.categoryCaster = categoryCaster;
    }

    public ExpensePostgres expenseToExpensePostgres(Expense expense) {
        ExpensePostgres expensePostgres = new ExpensePostgres();
        if (expense.getId()!= null){
            expensePostgres.setId(Long.valueOf(expense.getId()));
        }
        expensePostgres.setDescription(expense.getDescription());
        expensePostgres.setAmount(expense.getAmount());
        expensePostgres.setDate(expense.getDate());

        // Usar la instancia inyectada de UserCaster
        if (expense.getUser() != null) {
            UserPostgres userPostgres = userCaster.userToUserPostgres(expense.getUser());
            expensePostgres.setUser(userPostgres);
        }

        // Usar la instancia inyectada de CategoryCaster
        if (expense.getCategory() != null) {
            CategoryPostgres categoryPostgres = categoryCaster.categoryToCategoryPostgres(expense.getCategory());
            expensePostgres.setCategory(categoryPostgres);
        }

        return expensePostgres;
    }

    /**
     * Convierte un objeto ExpensePostgres a un objeto Expense.
     *
     * @param expensePostgres El objeto ExpensePostgres que se va a convertir.
     * @return Un objeto Expense que representa el gasto.
     */
    public Expense expensePostgresToExpense(ExpensePostgres expensePostgres) {
        Expense expense = new Expense();
        expense.setId(String.valueOf(expensePostgres.getId()));
        expense.setDescription(expensePostgres.getDescription());
        expense.setAmount(expensePostgres.getAmount());
        expense.setDate(expensePostgres.getDate());

        // Usar la instancia inyectada de CategoryCaster
        if (expensePostgres.getCategory() != null) {
            Category category = categoryCaster.categoryPostgresToCategory(expensePostgres.getCategory());
            expense.setCategory(category);
        }

        // Usar la instancia inyectada de UserCaster
        if (expensePostgres.getUser() != null) {
            User user = userCaster.userPostgresToUser(expensePostgres.getUser());
            expense.setUser(user);
        }

        System.out.println(expense.getUser());

        return expense;
    }

    /**
     * Convierte un objeto Expense a un objeto ExpenseMongo.
     *
     * @param expense El objeto Expense que se va a convertir.
     * @return Un objeto ExpenseMongo que representa el gasto.
     */
    public ExpenseMongo expenseToExpenseMongo(Expense expense) {
        ExpenseMongo expenseMongo = new ExpenseMongo();
        expenseMongo.setId(expense.getId());
        expenseMongo.setDescription(expense.getDescription());
        expenseMongo.setAmount(expense.getAmount());
        expenseMongo.setDate(expense.getDate());

        if(expense.getCategory() != null){
            CategoryMongo categoryMongo = categoryCaster.categoryToCategoryMongo(expense.getCategory());
            expenseMongo.setCategory(categoryMongo);
        }

       /* expenseMongo.setCategory(expense.getCategory());*/

        if (expense.getUser() != null){
            UserMongo userMongo = userCaster.userToUserMongo(expense.getUser());
            expenseMongo.setUser(userMongo);
        }

       /* expenseMongo.setUser(expense.getUser());*/
        return expenseMongo;
    }

    /**
     * Convierte un objeto ExpenseMongo a un objeto Expense.
     *
     * @param expenseMongo El objeto ExpenseMongo que se va a convertir.
     * @return Un objeto Expense que representa el gasto.
     */
    public Expense expenseMongoToExpense(ExpenseMongo expenseMongo) {
        System.out.println(expenseMongo);
        Expense expense = new Expense();
        expense.setId(expenseMongo.getId());
        expense.setDescription(expenseMongo.getDescription());
        expense.setAmount(expenseMongo.getAmount());
        expense.setDate(expenseMongo.getDate());

        if(expenseMongo.getCategory() != null){
            Category category = categoryCaster.categoryMongoToCategory(expenseMongo.getCategory());
            expense.setCategory(category);
        }

        if(expenseMongo.getUser() != null){
            User user = userCaster.userMongoToUser(expenseMongo.getUser());
            expense.setUser(user);
        }
       /* expense.setCategory(expenseMongo.getCategory());
        expense.setUser(expenseMongo.getUser());
*/
        System.out.println(expense);
        return expense;
    }
}

