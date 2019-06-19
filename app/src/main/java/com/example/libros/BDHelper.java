package com.example.libros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    public static String DB_FILEPATH = "/data/data/com.example.libros/databases/database.db";
    private static int version = 1;
    private static String name = "mibasededatos" ; //HipotecaDb
    private static SQLiteDatabase.CursorFactory factory = null;
    public static ArrayList<libros> obras;

    public BDHelper(Context context, String bdNombre, int bdVersion)
    {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Creando base de datos");

        //table hipoteca

        db.execSQL( "CREATE TABLE IF NOT EXISTS libros(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL, " +
                "autor TEXT, " +
                "genero TEXT," +
                "descripcion TEXT," +
                "favorito INTEGER," +
                "idfoto TEXT)" );

        db.execSQL( "CREATE UNIQUE INDEX id ON libros(nombre ASC)" );

        Log.i(this.getClass().toString(), "Tabla libros creada");


       //   Insertamos datos iniciales



        String count = "SELECT * FROM libros";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        if (!(mcursor.getCount() > 0)) {

            db.execSQL("INSERT INTO libros(id, nombre, autor, genero, descripcion, favorito, idfoto) VALUES(1, 'Harry Potter y la piedra filosofal', 'J.K.Rowling','fantasia','vacio',1, 'potter')");
            db.execSQL("INSERT INTO libros(id, nombre, autor, genero, descripcion, favorito, idfoto) VALUES(2, 'Don Quijote de la mancha', 'Miguel de Cervantes','Narrativa','vacio',0, 'quijote')");
            db.execSQL("INSERT INTO libros(id, nombre, autor, genero, descripcion, favorito, idfoto) VALUES(3, 'Harry Potter y la camara secreta', 'J.K.Rowling','fantasia','vacio',0, 'camara')");
            db.execSQL("INSERT INTO libros(id, nombre, autor, genero, descripcion, favorito, idfoto) VALUES(4, 'El principe de la niebla', 'Carlos Ruiz Zafon','intriga','vacio',1, 'zafon')");
        }




        Log.i(this.getClass().toString(), "Datos iniciales Usuario insertados");

        Log.i(this.getClass().toString(), "Base de datos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("DROP TABLE IF EXISTS libros");
        } catch (SQLiteException e) {
            // Manejo de excepciones
        }
        onCreate(db);
    }




}
