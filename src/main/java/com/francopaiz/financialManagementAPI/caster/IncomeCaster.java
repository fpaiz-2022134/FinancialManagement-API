package com.francopaiz.financialManagementAPI.caster;
import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.mongo.IncomeMongo;
import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import com.francopaiz.financialManagementAPI.model.postgres.IncomePostgres;
import com.francopaiz.financialManagementAPI.model.postgres.UserPostgres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncomeCaster {

    private final UserCaster userCaster;

    // Constructor para la inyección de UserCaster
    @Autowired
    public IncomeCaster(UserCaster userCaster) {
        this.userCaster = userCaster;
    }

    public IncomeMongo incomeToIncomeMongo(Income income) {
        IncomeMongo incomeMongo = new IncomeMongo();
        incomeMongo.setId(income.getId());
        incomeMongo.setSource(income.getSource());
        incomeMongo.setAmount(income.getAmount());
        incomeMongo.setDate(income.getDate());

        if (income.getUser() != null){
            UserMongo userMongo = userCaster.userToUserMongo(income.getUser());
            incomeMongo.setUser(userMongo);
        }

/*
        incomeMongo.setUser(income.getUser());
*/
        return incomeMongo;
    }

    public Income incomeMongoToIncome(IncomeMongo incomeMongo) {
        Income income = new Income();
        income.setId(incomeMongo.getId());
        income.setSource(incomeMongo.getSource());
        income.setAmount(incomeMongo.getAmount());
        income.setDate(incomeMongo.getDate());

        if (incomeMongo.getUser() != null){
            User user = userCaster.userMongoToUser(incomeMongo.getUser());
            income.setUser(user);
        }
/*
        income.setUser(incomeMongo.getUser());
*/
        return income;
    }

    public IncomePostgres incomeToIncomePostgres(Income income) {
        IncomePostgres incomePostgres = new IncomePostgres();
        /*incomePostgres.setId(Long.valueOf(income.getId()));*/
        incomePostgres.setId((income.getId() != null && !income.getId().isEmpty())
                ? Long.parseLong(income.getId()) : null);
        incomePostgres.setSource(income.getSource());
        incomePostgres.setAmount(income.getAmount());
        incomePostgres.setDate(income.getDate());

        if (income.getUser() != null) {
            UserPostgres userPostgres = userCaster.userToUserPostgres(income.getUser());
            incomePostgres.setUser(userPostgres);
        }

        return incomePostgres;
    }

    public Income incomePostgresToIncome(IncomePostgres incomePostgres) {
        Income income = new Income();
        /*if (income.getId() != null) {
            incomePostgres.setId(Long.valueOf(income.getId()));
        }*/
        income.setId(String.valueOf(incomePostgres.getId()));
        income.setSource(incomePostgres.getSource());
        income.setAmount(incomePostgres.getAmount());
        income.setDate(incomePostgres.getDate());

        if (incomePostgres.getUser() != null) {
            User user = userCaster.userPostgresToUser(incomePostgres.getUser());
            income.setUser(user);
        }
        System.out.println("Conversión de incompostgres a income" + income.getUser());
        return income;
    }

    /*public IncomePostgres incomeMongoToIncomePostgres(IncomeMongo incomeMongo) {
        IncomePostgres incomePostgres = new IncomePostgres();
        incomePostgres.setId(Long.valueOf(incomeMongo.getId()));
        incomePostgres.setSource(incomeMongo.getSource());
        incomePostgres.setAmount(incomeMongo.getAmount());
        incomePostgres.setDate(incomeMongo.getDate());

        if (incomeMongo.getUser() != null) {
            UserPostgres userPostgres = userCaster.userToUserPostgres(incomeMongo.getUser());
            incomePostgres.setUser(userPostgres);
        }

        return incomePostgres;
    }

    public IncomeMongo incomePostgresToIncomeMongo(IncomePostgres incomePostgres) {
        IncomeMongo incomeMongo = new IncomeMongo();
        incomeMongo.setId(String.valueOf(incomePostgres.getId()));
        incomeMongo.setSource(incomePostgres.getSource());
        incomeMongo.setAmount(incomePostgres.getAmount());
        incomeMongo.setDate(incomePostgres.getDate());

        if (incomePostgres.getUser() != null) {
            User user = userCaster.userPostgresToUser(incomePostgres.getUser());
            incomeMongo.setUser(user);
        }

        return incomeMongo;
    }*/
}