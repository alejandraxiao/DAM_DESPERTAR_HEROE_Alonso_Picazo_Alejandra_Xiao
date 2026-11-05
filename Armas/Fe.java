package Armas;

import Clases.ClaseArma;

public class Fe extends ClaseArma
{
     private String descripcion;

    public Fe(String nombre, int nivel, int daño, String descripcion)
    {
        super(nombre, nivel, daño);
        this.descripcion = "-Maná, Difícil de conseguir puntos";
    }

    //--------------Get / Set-------------------------

    public String getDescripcion() {
        return descripcion;
    }
}
