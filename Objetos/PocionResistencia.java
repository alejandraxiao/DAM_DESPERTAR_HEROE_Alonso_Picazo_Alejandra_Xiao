package Objetos;

import Clases.ClaseObjeto;

public class PocionResistencia extends ClaseObjeto
{
    private static int resistencia = 20;
    private String descripcion;
    
    public PocionResistencia(String nombre, int nivel, int resistencia, String descripcion)
    {
        super(nombre, nivel);
        this.resistencia = resistencia;
        this.descripcion = "Se te suma una resistencia a la actual";
    }

    //----------Get / Set --------------------

    public static int getResistencia() {
        return resistencia;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
