package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * This is a persistent implementation of the AccountDAO interface. SQLite has been used as the persistent provider.
 * Created by Saarrah I  Isthikar on 11/20/2016.
 *
 */

public class PersistentAccountDAO implements AccountDAO {



    @Override
    public List<String> getAccountNumbersList() {
        return new ArrayList<String>();
         ////// TODO: 11/20/2016  
    }

    @Override
    public List<Account> getAccountsList() {
        return null;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }

    @Override
    public void addAccount(Account account) {
//add account logics
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
