package com.francopaiz.financialManagementAPI.service.income;

import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.repository.income.IncomeRepository;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public List<Income> getIncomes() {
        return incomeRepository.getIncomes();
    }

    @Override
    public Income findIncomeById(String id) {
        validateIdFormat(id);  // Verifica el formato del ID
        return incomeRepository.findIncomeById(id).orElse(null);
    }

    @Override
    public Income createIncome(Income income) {
       /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedId = (String) authentication.getPrincipal();
        System.out.println(authenticatedId);*/


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();
/*
        System.out.println(idUser);
*/

        User authenticatedUser = userRepository.findUserById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        System.out.println(authenticatedUser);


        income.setUser(authenticatedUser);

        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }

       /* System.out.println(income.getUser());
        System.out.println(incomeRepository.createIncome(income));*/
        try {
            return incomeRepository.createIncome(income);
        } catch (Exception e) {
            e.printStackTrace(); // o usa un logger para registrar el error
            throw new RuntimeException("Error al crear el ingreso", e);

        }
    }

    @Override
    public Income updateIncome(String id, Income income) {
        validateIdFormat(id);  // Verifica el formato del ID

        Income existingIncome = incomeRepository.findIncomeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingreso no encontrado"));

        if (income.getAmount() != null) {
            existingIncome.setAmount(income.getAmount());
        }

        if (income.getDate() != null) {
            existingIncome.setDate(income.getDate());
        }

        if (income.getSource() != null) {
            existingIncome.setSource(income.getSource());
        }

        if (income.getUser() != null) {
            existingIncome.setUser(income.getUser());
        }

        return incomeRepository.updateIncome(existingIncome);
    }

    @Override
    public void deleteIncome(String id) {
        validateIdFormat(id);  // Verifica el formato del ID
        incomeRepository.deleteIncome(id);
    }

    @Override
    public List<Income> findByUser(User user) {
        return incomeRepository.findByUser(user);
    }

    @Override
    public List<Income> findIncomesForAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();
        System.out.println(idUser);

        User authenticatedUser = userRepository.findUserById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        System.out.println(authenticatedUser);

        System.out.println(incomeRepository.findByUser(authenticatedUser));
        try{
            return incomeRepository.findByUser(authenticatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al encontrar el ingreso", e);
        }

    }

    private void validateIdFormat(String id) {
        if (profile.equals("postgres")) {
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid id format for Postgres: " + id);
            }
        }
    }
}