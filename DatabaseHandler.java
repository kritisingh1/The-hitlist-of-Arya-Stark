package com.example.dell.aryashitlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dell.aryashitlist.TableData.TableInfo;

/**
 * Created by kriti on 04-01-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+TableInfo.TABLE_NAME+"("+TableInfo.NAME+" TEXT,"+TableInfo.STATUS+" TEXT,"+TableInfo.DESCRIPTION+" TEXT,"+TableInfo.IMG_LOCATION+" INTEGER);";

    public DatabaseHandler(Context context) {
        super(context, TableInfo.DATABASE_NAME, null, database_version);
        Log.d("DatabaseHandler","Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.d("DatabaseHandler","Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addInfo(DatabaseHandler dh, String name, String status,String description, int img_location){

        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.NAME,name);
        cv.put(TableInfo.STATUS,status);
        cv.put(TableInfo.DESCRIPTION,description);
        cv.put(TableInfo.IMG_LOCATION,img_location);
        db.insert(TableInfo.TABLE_NAME,null,cv);
        Log.d("DatabaseHandler","One row inserted");

    }

    public Cursor getInfo(DatabaseHandler dh){
        SQLiteDatabase db = dh.getReadableDatabase();
        String [] columns = {TableInfo.NAME,TableInfo.STATUS,TableInfo.DESCRIPTION,TableInfo.IMG_LOCATION};
        Cursor cr = db.query(TableInfo.TABLE_NAME,columns,null,null,null,null,null);
        return cr;
    }
}
