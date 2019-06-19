package com.example.libros;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class prueba extends AppCompatActivity {
     TextView nombre;
     TextView autor;
     TextView genero;
    ImageView imagen;
    SQLiteDatabase db;
    BDHelper dbhelp;
    int pos;
    private static final String BD_NOMBRE = "mibasededatos";
    private static final int BD_VERSION = 1;
    String direccion, mensaje, asunto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion);

        dbhelp = new BDHelper(getBaseContext(), BD_NOMBRE, BD_VERSION);
        db = dbhelp.getWritableDatabase();

          Bundle b = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();
          nombre = findViewById(R.id.idNombreDetalles);
         autor = findViewById(R.id.idAutorDetalles);
         genero = findViewById(R.id.idGeneroDetalles);
         imagen = findViewById(R.id.idImageDetalles);

          libros lib = (libros) b.getSerializable("id1");
          pos = b2.getInt("pos");

         nombre.setText(lib.getNombre());
         autor.setText("Autor: "+lib.getAutor());
        genero.setText("Genero: "+lib.getGenero());
          imagen.setImageResource(lib.getFotoid());


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalles_menus, menu);
      //  MenuItem itemnavegar = menu.findItem(R.id.navegar_id);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        int option = item.getItemId();
        Bundle b = getIntent().getExtras();
        libros lib = (libros) b.getSerializable("id1");

        if(option == R.id.modificar_id) {
            Intent modificar = new Intent(prueba.this, Modificacion.class);
            Bundle bu = new Bundle();
            bu.putInt("pos", pos);
            modificar.putExtras(bu);
            startActivity(modificar);
            Toast.makeText(prueba.this,
                    "Modificar ", Toast.LENGTH_LONG).show();
        }
        if(option == R.id.borrar_id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this); //Menu de confirmacion de eliminar

            //titulo
            builder.setTitle("Borrar elemento de la lista");

            //mensaje
            builder.setMessage("¿Esta seguro de que desea eliminarlo por completo?");

            //// permite cancelar el mensaje
            builder.setCancelable(true);

            //Accion del boton SI
            builder.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            db.execSQL("DELETE FROM libros WHERE id = "+pos+"");
                            Toast.makeText(getBaseContext(), "Se ha borrado un registro", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    });

            //Negative Button
            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Usually, negative use to close Dialog
                            //So, do nothing here, just dismiss it
                        }
                    });

            AlertDialog dialog = builder.create(); //Mostrar el dialogo de confirmacion.
            dialog.show();

           /* compartir.setType("text/plain");
            mensaje = hw.getInfo();
            asunto = hw.getNombre();
            compartir.putExtra(Intent.EXTRA_SUBJECT, asunto);
            compartir.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(compartir, "Compartir por..."));
*/
        }/*
        if(option == R.id.email_id) {
            Intent cartear = new Intent(Intent.ACTION_SENDTO);
            cartear.setType("text/plain");
            mensaje = hw.getInfo();
            asunto = hw.getNombre();
            cartear.putExtra(Intent.EXTRA_EMAIL, "usuario@dominio.com");
            cartear.putExtra(Intent.EXTRA_SUBJECT, asunto);
            cartear.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(cartear, "enviar correo"));


        }*/

        return false;
    }

}
