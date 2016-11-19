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
 * Created by Saarrah I  Isthikar on 11/20/2016.
 */

public class PersistentExpenseManager extends ExpenseManager {

    public PersistentExpenseManager(Context context){
        DBHelper dbHelper =new DBHelper(context);
        dbHelper.addTempAccount();
        setup();
    }

    @Override
    public void setup() {
        AccountDAO accountDAO = new PersistentAccountDAO();
        setAccountsDAO(accountDAO);

        TransactionDAO transactionDAO = new PersistentTransactionDAO();
        setTransactionsDAO(transactionDAO);

    }

}
