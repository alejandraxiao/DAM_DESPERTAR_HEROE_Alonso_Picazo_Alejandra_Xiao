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

    //---Si el xml tiene errores no se guarda encima, para no perder los datos---
    private boolean archivoCorrecto = true;

    public ClaseRanking()
    {
        //---Si el juego se ejecuta desde la carpeta Proyecto, el xml está una carpeta más arriba---
        //---Se mira la carpeta y no el archivo, así si el xml aún no existe se crea en su sitio---
        File carpeta = new File("02_XML");
        if (!carpeta.exists())
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
            System.out.println("No se encontró el xml del ranking, se empieza con un ranking vacío");
            return;
        }

        String nombre = "";
        String error = "";          //si se queda vacío es que el archivo está bien

        try (Scanner lector = new Scanner(fichero))
        {
            while (lector.hasNextLine() && error.equals(""))
            {
                String linea = lector.nextLine().trim();

                if (linea.startsWith("<nombre>"))
                {
                    nombre = Desescapar(linea.replace("<nombre>", "").replace("</nombre>", ""));
                }
                else if (linea.startsWith("<puntos>"))
                {
                    String textoPuntos = linea.replace("<puntos>", "").replace("</puntos>", "");

                    //---Comprobaciones antes de meter el jugador en los arrays---
                    if (cantidad == MAXIMO)
                    {
                        error = "hay más de " + MAXIMO + " jugadores";
                    }
                    else if (nombre.trim().equals(""))
                    {
                        error = "hay un jugador sin nombre";
                    }
                    else
                    {
                        try
                        {
                            int puntosLeidos = Integer.parseInt(textoPuntos);

                            if (puntosLeidos < 0)
                            {
                                error = "hay una puntuación negativa (" + puntosLeidos + ")";
                            }
                            else
                            {
                                //---Jugador correcto: se pone en la misma posición de los dos arrays---
                                nombres[cantidad] = nombre;
                                puntos[cantidad] = puntosLeidos;
                                cantidad++;
                                nombre = "";        //el siguiente jugador tiene que traer su propio nombre
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            error = "la puntuación '" + textoPuntos + "' no es un número";
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            error = "no se pudo leer el archivo";
        }

        //---Si algo está mal se avisa, se empieza vacío y el archivo no se sobrescribe---
        if (!error.equals(""))
        {
            System.out.println("Error en el ranking: " + error + ".");
            System.out.println("El archivo no se va a modificar hasta que se corrija. Esta vez las partidas no se guardarán.");
            archivoCorrecto = false;
            cantidad = 0;
        }
    }

    //---Al guardar: & < > se escriben como &amp; &lt; &gt; para que el xml siga bien formado---
    public String Escapar(String texto)
    {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    //---Al cargar: se hace al revés (el &amp; el último para no estropear los otros)---
    public String Desescapar(String texto)
    {
        return texto.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
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
        //---No se pisa un archivo que tenía errores---
        if (!archivoCorrecto)
        {
            System.out.println("La partida no se guarda porque el xml del ranking tiene errores");
            return;
        }

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
                escritor.write("            <nombre>" + Escapar(nombres[i]) + "</nombre>\n");
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
