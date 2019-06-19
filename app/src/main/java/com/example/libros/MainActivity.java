package com.example.libros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Creamos los componentes de la interfaz e instanciamos la base de datos

    ArrayList<libros> obras;
    BDHelper dbhelp;
    SQLiteDatabase db;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView rv;
    AdapterDatos adaptador;
    int pos = 0;
    private static final String BD_NOMBRE = "mibasededatos";
    private static final int BD_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncTaskRunner runner = new AsyncTaskRunner(); //Objeto de clase Asynctask (Ralentizar programa)
        String sleepTime = "5"; //Tiempo de espera
        runner.execute(sleepTime); //Ejecutar asynctask con el tiempo de espera determinado


        dbhelp = new BDHelper(getBaseContext(), BD_NOMBRE, BD_VERSION); //Objeto de la clase BDHelper (Conexion a Base de datos)
        db = dbhelp.getWritableDatabase(); //Hacemos editable la base de datos

        //Deslizar para actualizar y boton de añadir flotante
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        FloatingActionButton fab = findViewById(R.id.btnAnadir);

// Acción del boton de añadir
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(MainActivity.this, nuevo.class);

                startActivity(nuevo);
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "Registra un nuevo libro", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return false;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        rv = findViewById(R.id.recyclerId); //Recyclerview que usamos para mostrar los datos
        obras = new ArrayList<>(); //Arraylist para llenar el recyclerview
        rv.setLayoutManager(new LinearLayoutManager(this)); //Tipo de Recyclerview (Vertical)
        llenarLista(); //Metodo para llenar el Arraylist
        adaptador = new AdapterDatos(obras); //Adaptador del recyclerview
        rv.setAdapter(adaptador); //Añadimos el adaptador al recyclerview
        adaptador.notifyDataSetChanged(); //actualizacion del recyclerview por medio del adapter

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Accion de un elemento del recyclerview
                Intent abrirDellates = new Intent(MainActivity.this, prueba.class);
                libros l = obras.get(rv.getChildAdapterPosition(v));
                pos = rv.getChildAdapterPosition(v);
                int id = (pos + 1);
                Bundle b = new Bundle(); //Creamos dos bundles para transferir datos a la siguiente actividad
                Bundle b2 = new Bundle();
                b.putSerializable("id1", l);
                b2.putInt("pos", id);
                abrirDellates.putExtras(b);
                abrirDellates.putExtras(b2);
                startActivity(abrirDellates);
                Toast.makeText(MainActivity.this,
                        id + "", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Menu superior donde insertamos la barra de busqueda
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        MenuItem itemsearch = menu.findItem(R.id.search_id);
        SearchView sv = (SearchView) itemsearch.getActionView(); //Widget de busqueda en la interfaz
        sv.setQueryHint("buscar libro...");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) { //metodo del adapter que filtra el contenido
                adaptador.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Acción de los elementos de la barra (Vacio)
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Metodo que rellena el recyclerview con una consulta a la base de datos por medio de un cursor.

    public void llenarLista() {

        String nombre = null;
        String autor = null;
        String foto = null;
        String genero = null;
        int valor = 0;
        Cursor fila = db.rawQuery("SELECT nombre, autor, genero, idfoto FROM libros", null);
        if (fila.moveToNext()) {
            do {
                nombre = fila.getString(0);
                autor = fila.getString(1);
                genero = fila.getString(2);
                foto = fila.getString(3);
                valor = getResources().getIdentifier(foto, "drawable", getPackageName());

                obras.add(new libros(nombre, autor, genero, valor)); //Añadimos una consulta al arraylist.

            }
            while (fila.moveToNext());
        }

    }

    private void refreshContent() { //Metodo para actualizar el recyclerview al deslizar hacia abajo


        mSwipeRefreshLayout.setRefreshing(true);
        obras.clear();
        llenarLista();
        rv.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdapterDatos(obras);
        rv.setAdapter(adaptador);
        mSwipeRefreshLayout.setRefreshing(false);


    }


    // Clase que permite un AsyncTask al inicio de la actividad principal

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        // private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                //  resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                //  resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                // resp = e.getMessage();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //  finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Espere por favor",
                    "Cargando la base de datos. Puede tardar un rato");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }
    }


}