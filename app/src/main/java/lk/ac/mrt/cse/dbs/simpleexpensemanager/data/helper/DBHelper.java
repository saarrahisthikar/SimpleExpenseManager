package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Saarrah I  Isthikar on 11/20/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_manager.db";


    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db ) {

               //creation of account table

        db.execSQL(
                "create table account " +
                        "(account_no text primary key, bank_name text,holder_name text,balance double)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       // db.execSQL("DROP TABLE IF EXISTS account");
        //onCreate(db);
    }

  public void addTempAccount(){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("account_no", "123123");
      contentValues.put("bank_name", "hnb");
      contentValues.put("holder_name", "rimaz");
      contentValues.put("balance", "12.78");
      db.insert("account", null, contentValues);
  }
}
