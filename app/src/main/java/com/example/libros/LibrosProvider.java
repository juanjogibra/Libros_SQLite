package com.example.libros;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class LibrosProvider extends ContentProvider {

    //Definición del CONTENT_URI
    private static final String uri =
            "content://com.example.provider/libros";

    public static final Uri CONTENT_URI = Uri.parse(uri);

    //Clase interna para declarar las constantes de columna
    public static final class Libros implements BaseColumns
    {
        private Libros() {}

        //Nombres de columnas
        public static final String COL_NOMBRE = "nombre";
        public static final String COL_AUTOR = "autor";
        public static final String COL_GENERO = "genero";
        public static final String COL_FOTO = "idfoto";
    }
	//Base de datos
    private BDHelper helper;
    private static final String BD_NOMBRE = "mibasededatos";
    private static final int BD_VERSION = 1;
    private static final String TABLA_LIBROS = "libros";

    //UriMatcher
    private static final int LIBROS = 1;
    private static final int LIBROS_ID = 2;
    private static final UriMatcher uriMatcher;

    //Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("content://com.example.provider", "libros", LIBROS);
        uriMatcher.addURI("content://com.example.provider", "libros/#", LIBROS_ID);
    }



    @Override
    public boolean onCreate() {
        helper = new BDHelper(getContext(), BD_NOMBRE, BD_VERSION);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == LIBROS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.query(TABLA_LIBROS, projection, where,
                selectionArgs, null, null, sortOrder);

        return c;
    }

	@Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match)
        {
            case LIBROS:
                return "vnd.android.cursor.dir/vnd.com.example.provider.libros";
            case LIBROS_ID:
                return "vnd.android.cursor.item/vnd.com.example.provider.libros";
            default:
                return null;
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long regId = 1;

        SQLiteDatabase db = helper.getWritableDatabase();

        regId = db.insert(TABLA_LIBROS, null, values);

        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == LIBROS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = helper.getWritableDatabase();

        cont = db.delete(TABLA_LIBROS, where, selectionArgs);

        return cont;
    }


    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {

        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == LIBROS_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = helper.getWritableDatabase();

        cont = db.update(TABLA_LIBROS, values, where, selectionArgs);

        return cont;
    }

}
