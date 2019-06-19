package com.example.libros;

import java.io.Serializable;

public class libros implements Serializable {

    private String nombre;
    private String autor;
    private String genero;
    private String descripcion;
    private boolean favorito;
    private int fotoid;



    public libros()  {

    }

    public libros(String nombre, String autor) {
        this.nombre = nombre;
        this.autor = autor;
    }
    public libros(String nombre, String autor, String genero, String descripcion) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.descripcion = descripcion;
    }


    public libros(String nombre, String autor, int fotoid) {
        this.nombre = nombre;
        this.autor = autor;
        this.fotoid = fotoid;
    }
    public libros(String nombre, String autor, String genero, int fotoid) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.fotoid = fotoid;
    }


    public libros(String nombre, String autor, String genero, String descripcion, int fotoid, boolean favorito) {
        this.nombre = nombre;
        this.genero = genero;
        this.autor = autor;
        this.descripcion = descripcion;
        this.fotoid = fotoid;
        this.favorito = favorito;


    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String info) {
        this.genero = info;
    }

    public int getFotoid() {
        return fotoid;
    }

    public void setFotoid(int fotoid) {
        this.fotoid = fotoid;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }


    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



}
