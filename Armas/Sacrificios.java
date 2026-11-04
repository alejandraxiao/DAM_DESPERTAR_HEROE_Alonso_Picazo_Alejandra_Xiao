package Armas;

import Proyecto.ClaseArma;

public class Sacrificios extends ClaseArma
{
     private String descripcion;

    public Sacrificios(String nombre, int nivel, int daño, String descripcion)
    {
        super(nombre, nivel, daño);
        this.descripcion = "+Maná, Fácil de conseguir puntos";
    }

    //--------------Get / Set-------------------------

    public String getDescripcion() {
        return descripcion;
    }
}
