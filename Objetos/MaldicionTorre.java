package Objetos;

import Proyecto.ClaseObjeto;

public class MaldicionTorre extends ClaseObjeto
{
    private static int vidaMounstruo = 20;
    private static int dañoMounstruo = 5;
    private String descripcion;
    
    public MaldicionTorre(String nombre, int nivel, int vidaMounstruo,int dañoMounstruo, String descripcion)
    {
        super(nombre, nivel);
        this.vidaMounstruo = vidaMounstruo;
        this.dañoMounstruo = dañoMounstruo;
        this.descripcion = "Se le suma más vida y daño a los mounstruos";
    }

    //----------Get / Set --------------------

    public static int getVidaMounstruo() {
        return vidaMounstruo;
    }

    public static int getDañoMounstruo() {
        return dañoMounstruo;
    }

    public String getDescripcion() {
        return descripcion;
    }


    
}
