package com.blackangeldragon.carritosv2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.sql.SQLDataException;

public class dbCarrera extends dbHelper {

    Context context;

    public dbCarrera(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarCarrera(int vueltas, int salidaFalso ){

        long id = 0;

        try {
            dbHelper DbHelper = new dbHelper(context);
            SQLiteDatabase db = DbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("vueltas", vueltas);
            values.put("salidaFalso", salidaFalso);

            id = db.insert(TABLE_CARRERA, null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }
}
