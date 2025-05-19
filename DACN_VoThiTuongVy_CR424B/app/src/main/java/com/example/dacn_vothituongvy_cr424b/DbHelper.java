package com.example.dacn_vothituongvy_cr424b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context ctx) {
        super(ctx, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    /* ---------- CRUD ---------- */
    public long insertContact(String image, String name, String phone,
                              String email, String note,
                              String addedTime, String updatedTime) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.C_IMAGE,        image);
        cv.put(Constants.C_NAME,         name);
        cv.put(Constants.C_PHONE,        phone);
        cv.put(Constants.C_EMAIL,        email);
        cv.put(Constants.C_NOTE,         note);
        cv.put(Constants.C_ADDED_TIME,   addedTime);
        cv.put(Constants.C_UPDATED_TIME, updatedTime);

        long id = db.insert(Constants.TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public ArrayList<ModelContact> getAllData() {
        ArrayList<ModelContact> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.C_NAME + " COLLATE NOCASE ASC", null);

        if (c.moveToFirst()) {
            do {
                list.add(new ModelContact(
                        c.getString(c.getColumnIndexOrThrow(Constants.C_ID)),
                        c.getString(c.getColumnIndexOrThrow(Constants.C_NAME)),
                        c.getString(c.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        c.getString(c.getColumnIndexOrThrow(Constants.C_PHONE)),
                        c.getString(c.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        c.getString(c.getColumnIndexOrThrow(Constants.C_NOTE)),
                        c.getString(c.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        c.getString(c.getColumnIndexOrThrow(Constants.C_UPDATED_TIME))
                ));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public ModelContact getContactById(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + "=?", new String[]{id});
        if (c != null && c.moveToFirst()) {
            ModelContact contact = new ModelContact(
                    c.getString(c.getColumnIndexOrThrow(Constants.C_ID)),
                    c.getString(c.getColumnIndexOrThrow(Constants.C_NAME)),
                    c.getString(c.getColumnIndexOrThrow(Constants.C_IMAGE)),
                    c.getString(c.getColumnIndexOrThrow(Constants.C_PHONE)),
                    c.getString(c.getColumnIndexOrThrow(Constants.C_EMAIL)),
                    c.getString(c.getColumnIndexOrThrow(Constants.C_NOTE)),
                    c.getString(c.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                    c.getString(c.getColumnIndexOrThrow(Constants.C_UPDATED_TIME))
            );
            c.close();
            return contact;
        }
        return null;
    }

    public void deleteContact(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID + "=?", new String[]{id});
        db.close();
    }
    public void updateContact(String id, String image, String name, String phone,
                              String email, String note, String addedTime, String updatedTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Constants.C_IMAGE, image);
        cv.put(Constants.C_NAME, name);
        cv.put(Constants.C_PHONE, phone);
        cv.put(Constants.C_EMAIL, email);
        cv.put(Constants.C_NOTE, note);
        cv.put(Constants.C_ADDED_TIME, addedTime);
        cv.put(Constants.C_UPDATED_TIME, updatedTime);

        db.update(Constants.TABLE_NAME, cv, Constants.C_ID + "=?", new String[]{id});
        db.close();
    }



}
