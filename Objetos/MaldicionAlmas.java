package Objetos;

import Proyecto.ClaseObjeto;

public class MaldicionAlmas extends ClaseObjeto
{
    private static int vida = -20;
    private String descripcion;
    
    public MaldicionAlmas(String nombre, int nivel, int vida, String descripcion)
    {
        super(nombre, nivel);
        this.vida = vida;
        this.descripcion = "Se te resta la vida actual";
    }   
}
