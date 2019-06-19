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

public class nuevo extends AppCompatActivity {
    final int PICK_IMAGE = 1;
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
        setContentView(R.layout.activity_nuevo);
        dbhelp = new BDHelper(getBaseContext(), BD_NOMBRE, BD_VERSION);
        db = dbhelp.getWritableDatabase();


        nombre = findViewById(R.id.etnombre);
        autor = findViewById(R.id.etautor);
        descripcion = findViewById(R.id.etdesc);
        sp = findViewById(R.id.spinner);

        tw = findViewById(R.id.textView);
        ib = (ImageButton) findViewById(R.id.imageButton);
        ib.setEnabled(false);
        /*ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });*/
        FloatingActionButton fab = findViewById(R.id.btnGuardar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                author = autor.getText().toString();
                gender = sp.getSelectedItem().toString();
                description = descripcion.getText().toString();

                System.out.print(name + author + gender + description);
                Log.d("myTag", "desc: " + description + " name = " + nombre);
                //db.execSQL("INSERT INTO libros(nombre, autor, genero, descripcion, favorito, fotoid) VALUES('"+name+"','"+author+"','"+gender+"','"+description+"', 0, 'defecto')");
                String portada = "defecto";
                db.execSQL("INSERT INTO libros (nombre, autor, genero, descripcion, favorito, idfoto)VALUES('" +
                        name + "','" +
                        author + "','" +
                        gender + "','" +
                        description + "','" +
                        0 + "','" +
                        portada + "');");


                // db.execSQL("INSERT INTO libros(nombre, autor, genero, descripcion) VALUES('prueba','guille','sin genero','esto es una pruebita')");

                Toast.makeText(getBaseContext(), "Se ha registrado un nuevo libro de " + gender, Toast.LENGTH_LONG).show();
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
/*
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Uri imageUri = null;


        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                ib.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(nuevo.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(nuevo.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

        String p = getRealPathFromURI(imageUri);
        //getPath();
        //  String name = p.substring(p.lastIndexOf("/"+1));
        Toast.makeText(nuevo.this, p, Toast.LENGTH_LONG).show();
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
*/

}
