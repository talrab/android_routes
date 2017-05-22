package com.example.routes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tal on 15/02/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    int region_id = -1;

    public static final String DATABASE_NAME = "routes.db";
    public static final String TABLE_NAME_CATEGORIES = "categories_table";
    public static final String TABLE_NAME_CATEGORIES_COL_1 = "ID";
    public static final String TABLE_NAME_CATEGORIES_COL_2 = "NAME";

    public static final String TABLE_NAME_ROUTES = "routes_table";
    public static final String TABLE_NAME_ROUTES_COL_1 = "ID";
    public static final String TABLE_NAME_ROUTES_COL_2 = "NAME";
    public static final String TABLE_NAME_ROUTES_COL_3 = "DATE";
    public static final String TABLE_NAME_ROUTES_COL_4 = "COMMENT";
    public static final String TABLE_NAME_ROUTES_COL_5 = "CAT_ID";



    public DataBaseHelper (Context context){
        super(context,DATABASE_NAME,null,9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_CATEGORIES + " ( "+ TABLE_NAME_CATEGORIES_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_NAME_CATEGORIES_COL_2 + " TEXT)" );
        db.execSQL("create table " + TABLE_NAME_ROUTES + " ( "+ TABLE_NAME_ROUTES_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_NAME_ROUTES_COL_2 + " TEXT, " + TABLE_NAME_ROUTES_COL_3 + " TEXT, " + TABLE_NAME_ROUTES_COL_4 + " TEXT, " + TABLE_NAME_ROUTES_COL_5 + " INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROUTES);
        onCreate(db);
    }

    public boolean insertCategory (String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATEGORIES + " WHERE NAME = " + "'"+name+"'",null);
        if (res.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_NAME_CATEGORIES_COL_2, name);
            long result = db.insert(TABLE_NAME_CATEGORIES, null, contentValues); //insert will return -1 when fails)
            res.close();
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else return false;
    }

    public boolean insertRoute (String name, String date, String comment, String region){

        int region_id = getRegionID(region);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_ROUTES + " WHERE NAME = " + "'"+name+"'" + " AND " + TABLE_NAME_ROUTES_COL_3 + "=" + "'"+date+"'" ,null);
        if (res.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_NAME_ROUTES_COL_2, name);
            contentValues.put(TABLE_NAME_ROUTES_COL_3, date);
            contentValues.put(TABLE_NAME_ROUTES_COL_4, comment);
            contentValues.put(TABLE_NAME_ROUTES_COL_5, region_id);
            long result = db.insert(TABLE_NAME_ROUTES, null, contentValues); //insert will return -1 when fails)
            res.close();
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else return false;
    }


    public ArrayList<String> getCategoryNames (){
        ArrayList<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATEGORIES,null);
        while (res.moveToNext()){
            result.add(res.getString(1)); //this adds an element to the list.
        }
        res.close();
        return result;
    }

    public ArrayList<String> getRoutesNames (String region){
        int region_id = getRegionID(region);
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_ROUTES + " WHERE CAT_ID = " + region_id,null);
        while (res.moveToNext()){
            result.add(res.getString(1)); //this adds an element to the list.
        }
        res.close();
        return result;
    }

    public ArrayList<String> getRoutesDates (String region){
        int region_id = getRegionID(region);
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_ROUTES + " WHERE CAT_ID = " + region_id,null);
        while (res.moveToNext()){
            result.add(res.getString(2)); //this adds an element to the list.
        }
        res.close();
        return result;
    }

    public ArrayList<String> getRoutesComments (String region){
        int region_id = getRegionID(region);
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_ROUTES + " WHERE CAT_ID = " + region_id,null);
        while (res.moveToNext()){
            result.add(res.getString(3)); //this adds an element to the list.
        }
        res.close();
        return result;
    }


    public Cursor getAllData (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATEGORIES,null);
        return res;
    }

    public Cursor getAllRoutesData (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_ROUTES,null);
        return res;
    }

    public void deleteAllData (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_CATEGORIES);
        }

    public void deleteAllDataRoutes (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_ROUTES);
    }

    private int getRegionID (String region){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res1 = db.rawQuery("SELECT ID FROM " + TABLE_NAME_CATEGORIES + " WHERE NAME = " + "'" + region + "'",null);
        if (res1.moveToNext()){
            return res1.getInt(0);
        }
        else return -1;
    }


}

