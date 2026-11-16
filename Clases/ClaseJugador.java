package Clases;

public class ClaseJugador
{
    private int id;
    private String nombre;
    private int puntos;

    public ClaseJugador(int id, String nombre, int puntos)
    {
        this.id = id;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    //---Get / Set---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    //--- Métodos ---

    public void InfoJugador()
    {
        System.out.println("Id : " + id);
        System.out.println("Nombre : " + nombre);
        System.out.println("Puntos : " + puntos);
    }
}
