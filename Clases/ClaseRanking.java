package Clases;

//---imports---
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClaseRanking
{
    private final String RUTA_TXT = "ranking.txt";
    private final String RUTA_XML = "02_XML/score.xml";

    private ClaseJugador[] jugadores = new ClaseJugador[100];
    private int cantidad = 0;

    public ClaseRanking()
    {
        Cargar();       //al arrancar el juego recupera las partidas anteriores
    }

    //---Get---

    public int getCantidad() {
        return cantidad;
    }

    //--- Métodos ---

    //---Leer el fichero: cada línea es id;nombre;puntos---
    public void Cargar()
    {
        File fichero = new File(RUTA_TXT);

        if (!fichero.exists())
        {
            return;     //primera vez que se juega, no hay nada guardado
        }

        try (Scanner lector = new Scanner(fichero))
        {
            while (lector.hasNextLine() && cantidad < jugadores.length)
            {
                String[] partes = lector.nextLine().split(";");
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                int puntos = Integer.parseInt(partes[2]);

                jugadores[cantidad] = new ClaseJugador(id, nombre, puntos);
                cantidad++;
            }
        }
        catch (IOException e)
        {
            System.out.println("No se pudo leer el ranking");
        }
    }

    //---Añadir la partida que acaba de terminar---
    public void NuevoJugador(String nombre, int puntos)
    {
        if (cantidad == jugadores.length)
        {
            System.out.println("El ranking está lleno");
            return;
        }

        jugadores[cantidad] = new ClaseJugador(cantidad + 1, nombre, puntos);
        cantidad++;

        Ordenar();
        Guardar();
        GuardarXml();
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

    //---Guardar en el txt (sobrescribe todo con el ranking ya ordenado)---
    public void Guardar()
    {
        try (FileWriter escritor = new FileWriter(RUTA_TXT))
        {
            for (int i = 0; i < cantidad; i++)
            {
                escritor.write(jugadores[i].getId() + ";" + jugadores[i].getNombre() + ";" + jugadores[i].getPuntos() + "\n");
            }
        }
        catch (IOException e)
        {
            System.out.println("No se pudo guardar el ranking");
        }
    }

    //---Guardar en el xml con la misma estructura que score.dtd---
    public void GuardarXml()
    {
        try (FileWriter escritor = new FileWriter(RUTA_XML))
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

    //---Mostrar por pantalla---
    public void Mostrar()
    {
        if (cantidad == 0)
        {
            System.out.println("Todavía no hay partidas en el ranking");
            return;
        }

        System.out.println("---------- RANKING ----------");
        for (int i = 0; i < cantidad; i++)
        {
            System.out.println((i + 1) + ". " + jugadores[i].getNombre() + " - " + jugadores[i].getPuntos() + " puntos");
        }
    }
}
