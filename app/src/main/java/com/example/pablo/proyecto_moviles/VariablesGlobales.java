package com.example.pablo.proyecto_moviles;

// Se debe crear en el paquete com.example...
// Forma de uso:
// VariablesGlobales vg = VariablesGlobales.getInstance(); vg.setMitexto("Hola");    int i = vg.getMivalor();
public class VariablesGlobales {

    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private static VariablesGlobales instance = null;

    protected VariablesGlobales() {}
    public static VariablesGlobales getInstance() {
        if(instance == null) {instance = new VariablesGlobales(); }
        return instance;
    }

}// fin de la clase de variables globales

