package com.example.pablo.proyecto_moviles;

public class Product {
    private String id;
    private String nombre;
    private String servicio;
    private String descripcion;
    private double latitud;
    private double longitud;
    private String correo;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Product(String id, String nombre, String servicio, String descripcion, double latitud, double longitud, String correo, String image) {
        this.id = id;
        this.nombre = nombre;
        this.servicio = servicio;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.correo = correo;
        this.image = image;

    }
}