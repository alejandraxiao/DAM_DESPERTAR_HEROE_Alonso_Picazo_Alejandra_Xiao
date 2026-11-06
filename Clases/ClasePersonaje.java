package Clases;

public class ClasePersonaje 
{
    private String nombre;
    private String clase;
    private String arma;
    private final int vidaMaxima;
    private final int vidaMinima;
    private int vida;
    private int daño;

    public ClasePersonaje(String nombre, String clase, String arma, int vida, int daño, int vidaMaxima, int vidaMinima)
    {
        this.nombre = nombre;
        this.clase = clase;
        this.arma = arma;
        this.vida = vida;
        this.daño = daño;
        this.vidaMaxima = 100;
        this.vidaMinima = 0;
    }

    //---Get / Set---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }
    
    public int getVidaMinima() {
        return vidaMinima;
    }
    
    public int getVida() {
        return vida;
    }
    
    public void setVida(int vida) {
        this.vida = vida;
    }
    
    public int getDaño() {
        return daño;
    }
    
    public void setDaño(int daño) {
        this.daño = daño;
    }

    //--- Métodos ---
    
    public void InfoDaño()
    {
        System.out.println("Nombre : " + nombre);
        System.out.println("Vida : " + vida);
        System.out.println("Daño : " + daño);
        System.out.println("Arma : " + arma);
    }

    public void InfoPersonaje()
    {
        System.out.println("Nombre : " + nombre);
        System.out.println("Vida : " + getVida());
        System.out.println("Daño : " + getDaño());
    }
}
