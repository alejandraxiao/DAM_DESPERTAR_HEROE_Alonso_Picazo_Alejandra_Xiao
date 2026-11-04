package Mounstruos;

import Clases.ClaseMoustruo;

public class Nivel2 extends ClaseMoustruo
{
    private int arma = 15;

    public Nivel2(String nombre, int vida, int nivel, int fuerza, int arma)
    {
        super(nombre, vida, vida, nivel, fuerza);
        this.arma = arma;
    }

    //-----------Get / Set---------

    public int getArma() {
        return arma;
    }

    //-----------Métodos-------------

    public void InfoMounstruos(int nivel, int vida, int fuerza, int arma)
    {
        System.out.println("Nivel : " + nivel);
        System.out.println("Fuerza : " + incrementoFuerza(fuerza, arma));
        System.out.println("Vida : " + vida);
    }

    public int incrementoFuerza(int fuerza, int arma)
    {
        int fuerzaTotal = 0;
        int armaActual = getArma();
        int fuerzaActual = getFuerza();

        fuerzaTotal = armaActual + fuerzaActual;

        return fuerzaTotal;
    }   
}
