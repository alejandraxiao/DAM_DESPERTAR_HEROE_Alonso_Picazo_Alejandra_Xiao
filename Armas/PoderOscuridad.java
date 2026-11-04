package Armas;

import Clases.ClaseArma;

public class PoderOscuridad extends ClaseArma
{
     private String descripcion;

    public PoderOscuridad(String nombre, int nivel, int daño, String descripcion)
    {
        super(nombre, nivel, daño);
        this.descripcion = "+Fuerza, +Maná, -resistencia";
    }

    //--------------Get / Set-------------------------

    public String getDescripcion() {
        return descripcion;
    }
}
