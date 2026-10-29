package Proyecto;

public class ClasePersonaje 
{
    private String nombre;
    private int edad;
    private final int vidaMaxima; //Constante
    private int vida;
    private int fuerza;
    private int altura;
    private String clase;
    private String arma;

    public ClasePersonaje(String nombre, int edad,int vidaMaxima, int vida, int fuerza, int altura, String clase, String arma)
    {
        this.nombre = nombre;
        this.edad = 16;
        this.vidaMaxima = 100;
        this.vida = 100;
        this.fuerza = fuerza;
        this.altura = 160;
        this.clase = clase;
        this.arma = arma;
    }

    //---Get / Set---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
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

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
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

    //--- Métodos ---

    public void InfoPersonaje()
    {
        System.out.println("Nombre : " + nombre);
        System.out.println("Edad : " + edad);
        System.out.println("Altura : " + altura);
        System.out.println("Clase : " + clase);
        System.out.println("Arma : " + arma);
    }
    
}
