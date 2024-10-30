package com.francopaiz.financialManagementAPI.controller.expense;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.service.category.CategoryService;
import com.francopaiz.financialManagementAPI.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Expense> findAll(){

        System.out.println("HOLA");
        return expenseService.getExpenses();
    }

    @GetMapping("/{idExpense}")
    public Expense findById(@PathVariable String idExpense){


        return expenseService.findExpenseById(idExpense);
    }

    @PostMapping("/{idCategory}")
    public Expense save (@PathVariable String idCategory, @RequestBody Expense expense){
        System.out.println("holaaaaaaaaa");
        if (expense.getCategory() ==null){
            Category categoryFound = categoryService.findCategoryById(idCategory);
            System.out.println(categoryFound);
            expense.setCategory(categoryFound);
        }


        try {
            return expenseService.createExpense(expense);
        } catch (Exception e) {
            e.printStackTrace(); // o usa un logger para registrar el error
            throw new RuntimeException("Error al crear el gasto", e);

        }
    }

    @PutMapping("/{idExpense}")
    public Expense update(@PathVariable String idExpense, @RequestBody Expense expense){
        return expenseService.updateExpense(idExpense, expense);
    }

    @DeleteMapping("/{idExpense}")
    public void deleteById(@PathVariable String idExpense){
        expenseService.deleteExpense(idExpense);
    }

    @GetMapping("/my-expenses")
    public List<Expense> findIncomesForAuthenticatedUser() {
        return expenseService.findExpensesForAuthenticatedUser();
    }
}
