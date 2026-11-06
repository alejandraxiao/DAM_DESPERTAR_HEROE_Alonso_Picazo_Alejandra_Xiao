package Objetos;

import Clases.ClaseObjeto;
import Mounstruos.Nivel2;
import Mounstruos.Nivel3;

public class MaldicionTorre extends ClaseObjeto
{
    Nivel2 N2 = new Nivel2(null, 0, 0, 0, 0);
    Nivel3 N3 = new Nivel3(null, 0, 0, 0, 0);

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

    //-------------------------------MÉTODOS-------------------------------
    public void ponerMaldicionMounstruosPrimerCofre()
    {
        N2.setVida(N2.getVida() + vidaMounstruo);
        N2.setVida(N2.getFuerza() + dañoMounstruo);

        N3.setVida(N3.getVida() + vidaMounstruo);
        N3.setVida(N3.getFuerza() + dañoMounstruo);
    }
    public void ponerMaldicionMounstruosSegundoCofre()
    {
        N3.setVida(N3.getVida() + vidaMounstruo);
        N3.setVida(N3.getFuerza() + dañoMounstruo);
    }
}
