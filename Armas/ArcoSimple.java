package Armas;

import Proyecto.ClaseArma;

public class ArcoSimple extends ClaseArma
{
     private String descripcion;

    public ArcoSimple(String nombre, int nivel, int daño, String descripcion)
    {
        super(nombre, nivel, daño);
        this.descripcion = "-Fuerza, +resistencia";
    }

    //--------------Get / Set-------------------------

    public String getDescripcion() {
        return descripcion;
    }
}
