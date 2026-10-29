package Proyecto;

    public class ClaseObjeto 
{
    private String nombre;
    private int puntosExtra;
    private int nivel;
    private String descripcion;

    public ClaseObjeto(String nombre, int puntosExtra,int nivel, String descripcion)
    {
        this.nombre = nombre;
        this.puntosExtra = puntosExtra;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    //---Get / Set---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosExtra() {
        return puntosExtra;
    }

    public void setPuntosExtra(int puntosExtra) {
        this.puntosExtra = puntosExtra;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //---Métodos---

    public void InfoObjeto()
    {
        System.out.println("Nombre : " + nombre);
        System.out.println("Plus : " + puntosExtra);
        System.out.println("Nivel : " + nivel);
        System.out.println("Descrpción : " + descripcion);
    }
}
