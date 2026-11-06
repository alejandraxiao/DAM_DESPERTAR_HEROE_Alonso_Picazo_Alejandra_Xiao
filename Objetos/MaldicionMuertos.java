package Objetos;

import Clases.ClaseObjeto;
import Clases.ClasePersonaje;

public class MaldicionMuertos extends ClaseObjeto
{
    ClasePersonaje CP = new ClasePersonaje(null, null, null, 0, 0, 0, 0);
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

    //---------------------------------MÉTODOS---------------------------
    public void ponerMaldicionMuertos()
    {
        CP.setVida(CP.getVida() + mana);
    }
    
}
