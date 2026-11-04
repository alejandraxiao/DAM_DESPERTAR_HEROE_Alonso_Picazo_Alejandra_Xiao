package Clases;

public class ClaseArma 
{
    private String nombre;
    private int nivel;
    private int daño;

    public ClaseArma(String nombre, int nivel, int daño)
    {
        this.nombre = nombre;
        this.nivel = nivel;
        this.daño = daño;
    }

    //---------Get/Set---------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    
    //-----------Métodos-------------

    public void MostrarInfo()
    {
        System.out.println("Nombre : " + nombre);
        System.out.println("Nivel : " + nivel);
        System.out.println("Daño : " + daño);
    }
    
}
