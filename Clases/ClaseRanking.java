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

    //---Máximo de partidas que caben en el ranking---
    private final int MAXIMO = 10;

    //---Dos arrays paralelos: la posición i de los dos arrays es el mismo jugador---
    private String[] nombres = new String[MAXIMO];
    private int[] puntos = new int[MAXIMO];

    //---Contador de posiciones ocupadas---
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

        String nombre = "";

        try (Scanner lector = new Scanner(fichero))
        {
            //---Como mucho se cargan 10 jugadores (el tamaño de los arrays)---
            while (lector.hasNextLine() && cantidad < MAXIMO)
            {
                String linea = lector.nextLine().trim();

                if (linea.startsWith("<nombre>"))
                {
                    nombre = linea.replace("<nombre>", "").replace("</nombre>", "");
                }
                else if (linea.startsWith("<puntos>"))
                {
                    int puntosLeidos = Integer.parseInt(linea.replace("<puntos>", "").replace("</puntos>", ""));

                    //---Con los puntos ya tenemos el jugador completo: se pone en la misma posición de los dos arrays---
                    nombres[cantidad] = nombre;
                    puntos[cantidad] = puntosLeidos;
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
            System.out.println((i + 1) + ". " + nombres[i] + " - " + puntos[i] + " puntos");
        }
    }

    //---Modificar: meter la partida que acaba de terminar en su sitio y guardar el xml---
    public void Modificar(String nombre, int puntosNuevos)
    {
        //---Si el ranking está lleno, solo entra si supera al último---
        if (cantidad == MAXIMO && puntosNuevos <= puntos[cantidad - 1])
        {
            System.out.println("Tu puntuación no entra en el ranking, el último tiene " + puntos[cantidad - 1] + " puntos");
            return;
        }

        //---Buscar la posición: se pasa a todos los que tienen más o los mismos puntos---
        //---Así en un empate el más antiguo queda delante---
        int posicion = 0;
        while (posicion < cantidad && puntos[posicion] >= puntosNuevos)
        {
            posicion++;
        }

        //---Si hay hueco el ranking crece, si está lleno el último se pierde---
        if (cantidad < MAXIMO)
        {
            cantidad++;
        }

        //---Mover una posición hacia abajo a los que van detrás (desde el final para no pisar datos)---
        for (int i = cantidad - 1; i > posicion; i--)
        {
            nombres[i] = nombres[i - 1];
            puntos[i] = puntos[i - 1];
        }

        //---Poner al nuevo jugador en su hueco, en la misma posición de los dos arrays---
        nombres[posicion] = nombre;
        puntos[posicion] = puntosNuevos;

        GuardarXml();
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

            //---Se guardan ya ordenados, así al volver a cargar se mantiene el orden y los empates---
            for (int i = 0; i < cantidad; i++)
            {
                escritor.write("        <jugador id = \"" + (i + 1) + "\">\n");
                escritor.write("            <nombre>" + nombres[i] + "</nombre>\n");
                escritor.write("            <puntos>" + puntos[i] + "</puntos>\n");
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
