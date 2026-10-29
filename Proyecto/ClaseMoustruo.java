package Proyecto;

public class ClaseMoustruo 
{
    private String nombre;
    private final int vidaMaxima;
    private int vida;
    private int nivel;
    private int fuerza;

    public ClaseMoustruo(String nombre, int vidaMaxima, int vida, int nivel, int fuerza)
    {
        this.nombre = nombre;
        this.vidaMaxima = 100;
        this.vida = vida;
        this.nivel = nivel;
        this.fuerza = fuerza;
    }

    //--- Get / Set---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    //--- Métodos ---

    public void InfoMounstruos()
    {
        System.out.println("Nombre : " + nombre);
        System.out.println("Vida : " + vida);
        System.out.println("Nivel : " + nivel);
        System.out.println("Fuerza : " + fuerza);
    }
}
