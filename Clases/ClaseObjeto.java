package Clases;

public class ClaseObjeto 
{
    private String nombre;
    private int nivel;
    
    public ClaseObjeto(String nombre, int nivel)
    {
        this.nombre = nombre;
        this.nivel = nivel;
    }

    //--------Get/Set------------

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

    //----------Métodos--------------

    public void MostrarInfo()
    {
        System.out.println("Nombre : " + nombre);
        System.out.println("Nivel : " + nivel);
    }

    

}
