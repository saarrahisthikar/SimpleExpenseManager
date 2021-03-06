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

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_BANK;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_NO;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_BALANCE;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_BANK;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.ACCOUNT_HOLDER;
import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper.DBHelper.TABLE_ACCOUNT;

/**
 * This is a persistent implementation of the AccountDAO interface.
 * SQLite has been used as the persistent provider.
 * <p>
 * Created by Saarrah I  Isthikar on 11/20/2016.
 */

public class PersistentAccountDAO implements AccountDAO {

    private DBHelper dbHelper = null;

    /**
     * Constructs the Dao injecting SQLite DB
     *
     * @param dbHelper
     */
    public PersistentAccountDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Returns all the account numbers in the account table
     *
     * @return
     */
    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountNumbers = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor results = db.rawQuery("select " + ACCOUNT_NO + " from " + DBHelper.TABLE_ACCOUNT, null);

        results.moveToFirst();

        while (results.isAfterLast() == false) {
            accountNumbers.add(results.getString(results.getColumnIndex(ACCOUNT_NO)));
            results.moveToNext();
        }
        return accountNumbers;

    }

    /**
     * Returns all the records in the account database
     *
     * @return
     */
    @Override
    public List<Account> getAccountsList() {
        List<Account> accounts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor results = db.rawQuery("select *" + " from " + DBHelper.TABLE_ACCOUNT, null);

        results.moveToFirst();

        while (results.isAfterLast() == false) {

            String number = results.getString(results.getColumnIndex(ACCOUNT_NO));
            String bankName = results.getString(results.getColumnIndex(ACCOUNT_BANK));
            String holderName = results.getString(results.getColumnIndex(ACCOUNT_HOLDER));
            double balance = results.getDouble(results.getColumnIndex(ACCOUNT_BALANCE));

            Account account = new Account(number, bankName, holderName, balance);

            accounts.add(account);
            results.moveToNext();
        }
        return accounts;
    }

    /**
     * Returns the account details ofthe particular account having the given account number
     *
     * @param accountNo as String
     * @return
     * @throws InvalidAccountException
     */
    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account account = null;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor results = db.rawQuery("select * from " + TABLE_ACCOUNT + " where " + ACCOUNT_NO + "='" + accountNo + "'", null);
        results.moveToFirst();

        while (results.isAfterLast() == false) {

            String number = results.getString(results.getColumnIndex(ACCOUNT_NO));
            String bankName = results.getString(results.getColumnIndex(ACCOUNT_BANK));
            String holderName = results.getString(results.getColumnIndex(ACCOUNT_HOLDER));
            double balance = results.getDouble(results.getColumnIndex(ACCOUNT_BALANCE));

            account = new Account(number, bankName, holderName, balance);

            return account;

        }


        return account;
    }

    /***
     * Adds an account to the acoount table
     *
     * @param account - the account to be added.
     */
    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_NO, account.getAccountNo());
        contentValues.put(ACCOUNT_BANK, account.getBankName());
        contentValues.put(ACCOUNT_HOLDER, account.getAccountHolderName());
        contentValues.put(ACCOUNT_BALANCE, account.getBalance());
        db.insert(TABLE_ACCOUNT, null, contentValues);
    }

    /**
     * Removes the account from the account table
     *
     * @param accountNo - of the account to be removed.
     * @throws InvalidAccountException
     */
    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_ACCOUNT, ACCOUNT_NO + "= ? ",
                new String[]{accountNo});
    }

    /**
     * Updates the balance in the account table once a transaction is happened
     *
     * @param accountNo   - account number of the respective account
     * @param expenseType - the type of the transaction
     * @param amount      - amount involved
     * @throws InvalidAccountException
     */
    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        Account accountInfo = getAccount(accountNo);
        if (accountInfo == null) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }


        switch (expenseType) {
            case EXPENSE:
                accountInfo.setBalance(accountInfo.getBalance() - amount);
                break;
            case INCOME:
                accountInfo.setBalance(accountInfo.getBalance() + amount);
                break;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_ACCOUNT + " SET " + ACCOUNT_BALANCE + "=" + accountInfo.getBalance() + " WHERE " + ACCOUNT_NO + "='" + accountNo + "'";
        db.execSQL(strSQL);

    }
}
