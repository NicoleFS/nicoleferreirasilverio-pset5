package com.example.nicole.nicoleferreirasilverio_pset5;

/**
 * Created by Nicole on 2-12-2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nicole on 22-11-2016.
 */

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDB.db";
    private static final int DATABASE_VERSION = 1;
    String TABLE;

    String todo_id = "task";

    // constructor
    public DBhelper (Context context, String TABLE) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.TABLE = TABLE;
    }

    // onCreate
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + todo_id + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    // onUpgrade
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int I, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    // create
    public void create( String todo_element ){
        SQLiteDatabase db  = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todo_id, todo_element);
        db.insert(TABLE, null, values);
        db.close();
    }

    // read
    public Cursor read() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = new String[] { "_id", "todo_id" };
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE, null);
        //Cursor cursor = db.query(TABLE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }

    //update
    public void update( String todo_element) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todo_id, todo_element);
        db.update(TABLE, values, " _id = ? ", new String[] {String.valueOf(todo_id)});
        db.close();
    }


    //delete
    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " _id = ? ", new String[] {String.valueOf(id)});
        db.close();
    }

}
