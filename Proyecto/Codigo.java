package Proyecto;

public class Codigo 
{
    public static void main(String[] args) 
    {
        Metodos m = new Metodos();

        boolean jugar = true;

        while (jugar)
        {
            m.Historia();
            m.Inicio();
            while (juego)
            {
                m.EleccionPersonaje(null, null);
                m.TorreExplicacion(null, null);
                m.Ataque(null, null);
                m.Jugar();
            }
            
        }
    }
}
