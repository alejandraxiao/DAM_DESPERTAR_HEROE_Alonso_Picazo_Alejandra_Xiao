package Clases;

//---imports---
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClaseRanking
{
    //---Todo se guarda únicamente en el xml del score---
    private String rutaXml = "02_XML/score.xml";

    private ClaseJugador[] jugadores = new ClaseJugador[100];
    private int cantidad = 0;

    public ClaseRanking()
    {
        //---Si el juego se ejecuta desde la carpeta Proyecto, el xml está una carpeta más arriba---
        File fichero = new File(rutaXml);
        if (!fichero.exists())
        {
            rutaXml = "../02_XML/score.xml";
        }

        Cargar();       //al arrancar el juego recupera los jugadores que ya hay en el xml
    }

    //---Get---

    public int getCantidad() {
        return cantidad;
    }

    //--- Métodos ---

    //---Leer el xml línea a línea y sacar los datos de cada jugador---
    public void Cargar()
    {
        File fichero = new File(rutaXml);

        if (!fichero.exists())
        {
            System.out.println("No se encontró el xml del ranking");
            return;
        }

        int id = 0;
        String nombre = "";
        int puntos = 0;

        try (Scanner lector = new Scanner(fichero))
        {
            while (lector.hasNextLine() && cantidad < jugadores.length)
            {
                String linea = lector.nextLine().trim();

                if (linea.startsWith("<jugador "))
                {
                    id = Integer.parseInt(linea.split("\"")[1]);       //lo que hay entre las comillas
                }
                else if (linea.startsWith("<nombre>"))
                {
                    nombre = linea.replace("<nombre>", "").replace("</nombre>", "");
                }
                else if (linea.startsWith("<puntos>"))
                {
                    puntos = Integer.parseInt(linea.replace("<puntos>", "").replace("</puntos>", ""));

                    //---Con los puntos ya tenemos el jugador completo---
                    jugadores[cantidad] = new ClaseJugador(id, nombre, puntos);
                    cantidad++;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("No se pudo leer el ranking");
        }
    }

    //---Ver: mostrar el ranking por pantalla---
    public void Ver()
    {
        if (cantidad == 0)
        {
            System.out.println("Todavía no hay partidas en el ranking");
            return;
        }

        //---El título del ranking ya lo pone Metodos---
        for (int i = 0; i < cantidad; i++)
        {
            System.out.println((i + 1) + ". " + jugadores[i].getNombre() + " - " + jugadores[i].getPuntos() + " puntos");
        }
    }

    //---Modificar: añadir la partida que acaba de terminar y guardar el xml---
    public void Modificar(String nombre, int puntos)
    {
        if (cantidad == jugadores.length)
        {
            System.out.println("El ranking está lleno");
            return;
        }

        jugadores[cantidad] = new ClaseJugador(NuevoId(), nombre, puntos);
        cantidad++;

        Ordenar();
        GuardarXml();
    }

    //---El id nuevo es el mayor id que haya + 1, así nunca se repite---
    public int NuevoId()
    {
        int mayor = 0;

        for (int i = 0; i < cantidad; i++)
        {
            if (jugadores[i].getId() > mayor)
            {
                mayor = jugadores[i].getId();
            }
        }
        return mayor + 1;
    }

    //---Burbuja: de más puntos a menos---
    public void Ordenar()
    {
        for (int i = 0; i < cantidad - 1; i++)
        {
            for (int j = 0; j < cantidad - 1 - i; j++)
            {
                if (jugadores[j].getPuntos() < jugadores[j + 1].getPuntos())
                {
                    ClaseJugador aux = jugadores[j];
                    jugadores[j] = jugadores[j + 1];
                    jugadores[j + 1] = aux;
                }
            }
        }
    }

    //---Guardar en el xml con la misma estructura que score.dtd (se reescribe con todos los jugadores)---
    public void GuardarXml()
    {
        try (FileWriter escritor = new FileWriter(rutaXml))
        {
            escritor.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
            escritor.write("<?xml-stylesheet type=\"text/xsl\" href=\"score.xslt\"?>\n\n");
            escritor.write("<!DOCTYPE score SYSTEM \"score.dtd\">\n\n");
            escritor.write("<score>\n");
            escritor.write("    <jugadores>\n");

            for (int i = 0; i < cantidad; i++)
            {
                escritor.write("        <jugador id = \"" + jugadores[i].getId() + "\">\n");
                escritor.write("            <nombre>" + jugadores[i].getNombre() + "</nombre>\n");
                escritor.write("            <puntos>" + jugadores[i].getPuntos() + "</puntos>\n");
                escritor.write("        </jugador>\n");
            }

            escritor.write("    </jugadores>\n");
            escritor.write("</score>\n");
        }
        catch (IOException e)
        {
            System.out.println("No se pudo guardar el xml");
        }
    }
}
