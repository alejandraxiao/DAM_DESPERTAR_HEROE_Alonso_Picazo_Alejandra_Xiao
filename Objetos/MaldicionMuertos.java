package Objetos;

import Clases.ClaseObjeto;

public class MaldicionMuertos extends ClaseObjeto
{
    private static int mana = -15;
    private String descripcion;
    
    public MaldicionMuertos(String nombre, int nivel, int mana, String descripcion)
    {
        super(nombre, nivel);
        this.mana = mana;
        this.descripcion = "Se te resta el maná actual";
    }

    //----------Get / Set--------------

    public static int getMana() {
        return mana;
    }

    public String getDescripcion() {
        return descripcion;
    }

    
}
