package com.francopaiz.financialManagementAPI.controller.income;


import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.service.income.IncomeService;
import com.francopaiz.financialManagementAPI.service.income.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public List<Income> findAll() {
        return incomeService.getIncomes();
    }

    @GetMapping("/{idIncome}")
    public Income findById(@PathVariable String idIncome)
    {
        return incomeService.findIncomeById(idIncome);
    }

    @PostMapping()
    public Income save(@RequestBody Income income){
        return incomeService.createIncome(income);
    }

    @PutMapping("/{idIncome}")
    public Income update(@PathVariable String idIncome,@RequestBody Income income){
        return incomeService.updateIncome(idIncome, income);
    }

    @DeleteMapping("/{idIncome}")
    public void deleteById(@PathVariable String idIncome){
        incomeService.deleteIncome(idIncome);
    }

    @GetMapping("/my-incomes")
    public List<Income> findIncomesForAuthenticatedUser() {
        return incomeService.findIncomesForAuthenticatedUser();
    }
}
