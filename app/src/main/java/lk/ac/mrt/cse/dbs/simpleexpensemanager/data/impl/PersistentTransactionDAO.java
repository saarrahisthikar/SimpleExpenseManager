package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_BALANCE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_HOLDER;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_NO;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TABLE_ACCOUNT;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TABLE_TRANSACTION;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TRANSAC_ACC_NO;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TRANSAC_AMOUNT;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TRANSAC_DATE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TRANSAC_EXPENSE;

/**
 * This is a persistent implementation of the TransactionDAO interface.
 *
 * SQLite has been used as the persistent provider.
 * Created by Saarrah I  Isthikar on 11/20/2016.
 */

public class PersistentTransactionDAO implements TransactionDAO {


    private DBHelper dbHelper = null;

    public PersistentTransactionDAO(DBHelper dbHelper){
        this.dbHelper=dbHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);

        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSAC_ACC_NO,accountNo);
        contentValues.put(TRANSAC_AMOUNT,amount);
        contentValues.put(TRANSAC_DATE, strDate);
        contentValues.put(TRANSAC_EXPENSE, expenseType.toString());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(TABLE_TRANSACTION, null, contentValues);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions= new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor results = db.rawQuery("select * " + " from " + DBHelper.TABLE_TRANSACTION, null);

        results.moveToFirst();

        while (results.isAfterLast() == false){

            String number = results.getString(results.getColumnIndex(TRANSAC_ACC_NO));
            String strDate = results.getString(results.getColumnIndex(TRANSAC_DATE));

            Date convertedDate = null;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                convertedDate = df.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double amount =  Double.valueOf( results.getString(results.getColumnIndex(TRANSAC_AMOUNT)));
            String exType = results.getString(results.getColumnIndex(TRANSAC_EXPENSE));

           ExpenseType expenseType =  ExpenseType.valueOf(exType);

            Transaction transaction = new Transaction(convertedDate,number,expenseType,amount);

            transactions.add(transaction);
            results.moveToNext();
        }
        return transactions;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        int size = getAllTransactionLogs().size();
        if (size <= limit) {
            return getAllTransactionLogs();
        }

        return getAllTransactionLogs().subList(size - limit, size);

    }
}
