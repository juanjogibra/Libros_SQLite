package com.example.libros;

import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Modificacion extends AppCompatActivity {

    BDHelper dbhelp;
    SQLiteDatabase db;
    ImageButton ib;
    TextView tw;
    EditText nombre;
    EditText autor;
    Spinner sp;
    EditText descripcion;

    String name;
    String author;
    String gender;
    String description;

    private static final String BD_NOMBRE = "mibasededatos";
    private static final int BD_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion);
         dbhelp = new BDHelper(getBaseContext(), BD_NOMBRE, BD_VERSION);
         db = dbhelp.getWritableDatabase();
         Bundle bu = getIntent().getExtras();
         final int pos = bu.getInt("pos");

        Bundle b = getIntent().getExtras();
        libros lib = (libros) b.getSerializable("id1");
        Bundle b2 = getIntent().getExtras();


        nombre = findViewById(R.id.etnombre);
        autor = findViewById(R.id.etautor);
        descripcion = findViewById(R.id.etdesc);
        sp = findViewById(R.id.spinner);

       // nombre.setText(lib.getNombre().toString());
       // autor.setText(lib.getAutor().toString());
      //  descripcion.setText(lib.getDescripcion().toString());

        tw = findViewById(R.id.textView);

        FloatingActionButton fab = findViewById(R.id.btnGuardar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                author = autor.getText().toString();
                gender = sp.getSelectedItem().toString();
                description = descripcion.getText().toString();


                Log.d("myTag", "desc: "+description+" name = "+nombre);
              //db.execSQL("INSERT INTO libros(nombre, autor, genero, descripcion, favorito, fotoid) VALUES('"+name+"','"+author+"','"+gender+"','"+description+"', 0, 'defecto')");

                db.execSQL("UPDATE libros SET nombre = '" + name + "'WHERE id = "+pos+"");
                db.execSQL("UPDATE libros SET autor = '" + author + "'WHERE id = "+pos+"");
                db.execSQL("UPDATE libros SET genero = '" + gender + "'WHERE id = "+pos+"");
                db.execSQL("UPDATE libros SET descripcion = '" + description + "'WHERE id = "+pos+"");


               // db.execSQL("INSERT INTO libros(nombre, autor, genero, descripcion) VALUES('prueba','guille','sin genero','esto es una pruebita')");

               Toast.makeText(getBaseContext(), "Se ha modificado un nuevo libro de "+gender, Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "Registra el nuevo libro creado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return false;
            }
        });


    }
}
