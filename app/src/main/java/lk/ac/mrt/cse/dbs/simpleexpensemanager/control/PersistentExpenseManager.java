package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.app.ActivityManager;
import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Concrete implementation of Expense manager using SQLite as the persistence provider
 * <p>
 * Created by Saarrah I  Isthikar on 11/20/2016.
 */

public class PersistentExpenseManager extends ExpenseManager {
    private DBHelper dbHelper = null;

    /**
     * Constructing the persisent manager
     *
     * @param context
     */
    public PersistentExpenseManager(Context context) {
        dbHelper = new DBHelper(context);
        setup();
    }

    /**
     * Setting up persistent implementation for DAO
     */
    @Override
    public void setup() {
        AccountDAO accountDAO = new PersistentAccountDAO(dbHelper);
        setAccountsDAO(accountDAO);

        TransactionDAO transactionDAO = new PersistentTransactionDAO(dbHelper);
        setTransactionsDAO(transactionDAO);

    }

}
