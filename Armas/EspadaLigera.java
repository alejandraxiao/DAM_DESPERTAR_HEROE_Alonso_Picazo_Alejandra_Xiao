package Armas;

import Clases.ClaseArma;

public class EspadaLigera extends ClaseArma
{
     private String descripcion;

    public EspadaLigera(String nombre, int nivel, int daño, String descripcion)
    {
        super(nombre, nivel, daño);
        this.descripcion = "-Fuerza, +resistencia";
    }

    //--------------Get / Set-------------------------

    public String getDescripcion() {
        return descripcion;
    }

}
