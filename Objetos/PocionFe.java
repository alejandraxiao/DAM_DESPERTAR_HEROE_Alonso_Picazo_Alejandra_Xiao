package Objetos;

import Proyecto.ClaseObjeto;

public class PocionFe extends ClaseObjeto
{
     private static int fe = 15;
    private String descripcion;
    
    public PocionFe(String nombre, int nivel, int resistencia, String descripcion)
    {
        super(nombre, nivel);
        this.fe = resistencia;
        this.descripcion = "Se te suma a la fe actual";
    }

    //----------Get / Set --------------------

    public static int getfe() {
        return fe;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
