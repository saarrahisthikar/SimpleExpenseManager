package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/** Java interactions with SQLite database
 *
 * Created by Saarrah I  Isthikar on 11/20/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_manager.db";
    public static final String TABLE_ACCOUNT ="account";
    public static final String TABLE_TRANSACTION = "transaction_log";

    public static final String ACCOUNT_NO="account_no";
    public static final String ACCOUNT_BANK= "bank_name";
    public static final String ACCOUNT_HOLDER = "holder_name";
    public static final String ACCOUNT_BALANCE = "balance";


    public static final String TRANSAC_DATE = "date";
    public static final String TRANSAC_ACC_NO= "account_no";
    public static final String TRANSAC_EXPENSE= "expense_type";
    public static final String TRANSAC_AMOUNT ="amount";


    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db ) {

        db.execSQL(
                "create table account " +
                        "(account_no text primary key, bank_name text,holder_name text,balance double)"
        );
        db.execSQL(
                "create table transaction_log " +
                        "(date text, account_no text, expense_type text, amount double)"
        );
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       // db.execSQL("DROP TABLE IF EXISTS account");
        //onCreate(db);
    }

}
