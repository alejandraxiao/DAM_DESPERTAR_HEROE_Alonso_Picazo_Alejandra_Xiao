package Objetos;

import Proyecto.ClaseObjeto;

public class PocionCuracion extends ClaseObjeto
{
    private static int curacion = 25;
    private String descripcion;
    
    public PocionCuracion(String nombre, int nivel, int resistencia, String descripcion)
    {
        super(nombre, nivel);
        this.curacion = curacion;
        this.descripcion = "Se te suma una resistencia a la actual";
    }

    //----------Get / Set --------------------

    public static int getCuracion() {
        return curacion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
