package com.blackangeldragon.carritosv2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "carritos.db";
    public static final String TABLE_CARRERA = "t_carrera";

    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE" + TABLE_CARRERA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCRMENT," +
                "vueltas INTEGER  NOT NULL," +
                "salidafalso INTEGER NOT NULL," +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE" + TABLE_CARRERA);
        onCreate(sqLiteDatabase);

    }
}
