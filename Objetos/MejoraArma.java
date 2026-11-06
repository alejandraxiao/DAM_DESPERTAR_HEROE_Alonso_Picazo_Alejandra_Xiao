package Objetos;

import Clases.ClaseObjeto;
import Clases.ClasePersonaje;

public class MejoraArma extends ClaseObjeto
{
    ClasePersonaje CP = new ClasePersonaje(null, null, null, 0, 0, 0, 0);
    private static int mejora = 1;
    private static int daño = 5;
    private static int mana = -5;
    private String descripcion;
    
    public MejoraArma(String nombre, int nivel, int mejora, int daño, int mana, String descripcion)
    {
        super(nombre, nivel);
        this.mejora = mejora;
        this.daño = daño;
        this.mana = mana;
        this.descripcion = "Mejora de nivel +1";
    }

    //----------Get / Set --------------------

    public static int getMejora() {
        return mejora;
    }

    public static int getDaño() {
        return daño;
    }

    public static int getMana() {
        return mana;
    }

    public String getDescripcion() {
        return descripcion;
    }


    //---------------------------MÉTODOS-----------------------

    public void ponerNivelArma()
    {
        CP.setDaño(CP.getDaño() + daño);
    }
}
