package com.mobile.cover.photo.editor.back.maker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {

    // TODO: 28/6/2018 DECLARATIONS
    public static final String DATABASE_NAME = "Print_photo.db";
    public static final String TABLE_NAME = "Printphoto";
    public static final String COL_1 = "id";
    public static final String COL_2 = "cart_id";
    public static final String COL_3 = "cart_image_file";
//    public static final String COL_4 = "image_height";
//    public static final String COL_5 = "image_width";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // TODO: 28/6/2018 ONCREATE METHOD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,cart_id INTEGER,cart_image_file BLOB,image_height TEXT,image_width TEXT)");
    }

    // TODO: 28/6/2018 ONUPGRADE METHOD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // TODO: 28/6/2018 METHOD FOR INSERTING DATA
    public boolean insertData(String cart_id, String cart_image_file) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, cart_id);
        contentValues.put(COL_3, cart_image_file);
//        contentValues.put(COL_4, image_height);
//        contentValues.put(COL_5, image_width);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // TODO: 28/6/2018 CURSOR
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    // TODO: 28/6/2018 METHOD FOR UPDATE DATA
    public boolean updateData(String id, String cart_id, String cart_image_file) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, cart_id);
        contentValues.put(COL_3, cart_image_file);
//        contentValues.put(COL_4, image_height);
//        contentValues.put(COL_5, image_width);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    // TODO: 28/6/2018 METHOD FOR DELETE DATA
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Cursor data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + "\twhere ID = " + id, null);
        return cursor;
    }

}
