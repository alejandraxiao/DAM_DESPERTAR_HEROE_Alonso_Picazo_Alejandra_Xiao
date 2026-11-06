package Objetos;

import Clases.ClaseObjeto;
import Clases.ClasePersonaje;

public class MaldicionAlmas extends ClaseObjeto
{
    ClasePersonaje CP = new ClasePersonaje(null, null, null, 0, 0, 0, 0);

    private static int vida = -20;
    private String descripcion;
    
    public MaldicionAlmas(String nombre, int nivel, int vida, String descripcion)
    {
        super(nombre, nivel);
        this.vida = vida;
        this.descripcion = "Se te resta la vida actual";
    }   

    //---------------------MÉTODOS--------------------

    public void ponerMaldiciónAlmas()
    {
        CP.setVida(CP.getVida() + vida);
    }
}
