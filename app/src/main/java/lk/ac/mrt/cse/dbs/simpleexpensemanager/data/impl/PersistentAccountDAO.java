package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_NO;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACOOUNT_BALANCE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TABLE_ACCOUNT;

/**
 * This is a persistent implementation of the AccountDAO interface. SQLite has been used as the persistent provider.
 * Created by Saarrah I  Isthikar on 11/20/2016.
 *
 */

public class PersistentAccountDAO implements AccountDAO {

    private DBHelper dbHelper = null;

    public PersistentAccountDAO(DBHelper dbHelper){
        this.dbHelper=dbHelper;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountNumbers = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor results =  db.rawQuery( "select " + ACCOUNT_NO + " from " + DBHelper.TABLE_ACCOUNT, null );

        results.moveToFirst();

        while(results.isAfterLast() == false){
            accountNumbers.add(results.getString(results.getColumnIndex(ACCOUNT_NO)));
            results.moveToNext();
        }
        return accountNumbers;

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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_no", account.getAccountNo());
        contentValues.put("bank_name",account.getBankName());
        contentValues.put("holder_name", account.getAccountHolderName());
        contentValues.put("balance", account.getBalance());
        db.insert("account", null, contentValues);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("contacts",
                "accountNo = ? ",
                new String[] { accountNo });
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        Account accountInfo = getAccount(accountNo);
        if(accountInfo==null){
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }


        switch (expenseType) {
            case EXPENSE:

                accountInfo .setBalance(accountInfo.getBalance() - amount);
                break;
            case INCOME:
                accountInfo .setBalance(accountInfo.getBalance() + amount);
                break;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String strSQL = "UPDATE " + TABLE_ACCOUNT + " SET "+ ACOOUNT_BALANCE  + "=" +accountInfo.getBalance()+" WHERE " + ACCOUNT_NO + "=" + accountNo ;

        db.execSQL(strSQL);


    }
}
