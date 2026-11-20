package Proyecto;

//---imports---
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Clases.ClasePersonaje;       //personaje base sin herencias
import Mounstruos.*;                //Todos los mounstruos
import Armas.*;                     //Todas las armas
import Objetos.*;                   //Todos los objetos
import Clases.ClaseMoustruo;        //Clase mounstruo

//---Métodos---
public class Metodos 
{
    //---Teclado compartido por todos los métodos (no guarda datos de la partida)---
    static Scanner sc = new Scanner(System.in);

    //---Constante con el nombre del guía---
    static final String GUIA = "Bigotín";

    //---Máximo de partidas que caben en el ranking---
    static final int MAXIMO_RANKING = 10;

    //---Constantes del héroe---
    static final int VIDA_MAXIMA = 100;             //vida al empezar y máximo al curarse
    static final int POCIONES_INICIALES = 3;        //tamaño del inventario
    static final int CURACION_POCION = 25;          //vida que cura cada poción
    static final int VIDA_CRITICA = 25;             //a partir de aquí Bigotín avisa

    //---Método para la pausa entre textos---
    public static void Pausa()
    {
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException e)
        {

        }
    }

    //----------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------PARTES DEL JUEGO--------------------------------------------------------------------
    //---Juego---

    //---Juega una partida y devuelve cuántos jugadores tiene el ranking después de guardarla---
    public static int Juego(String [] nombres, int [] puntos, int cantidad, boolean archivoCorrecto)
    {
        //---Partida nueva: todo vuelve a empezar (se crean aquí y se pasan a los demás métodos)---
        ClasePersonaje CP = new ClasePersonaje(null, null, null, VIDA_MAXIMA, 15, VIDA_MAXIMA, 0);
        Nivel1 N1 = new Nivel1("Hada corrupta", 50, 1, 10, 0);
        Nivel2 N2 = new Nivel2("Espectro de maná", 100, 2, 15, 0);
        Nivel3 N3 = new Nivel3("Guardián de la Torre", 150, 3, 18, 0);

        //---Nuestro inventario al inicio: array fijo y contador de pociones---
        String [] inventario = new String[POCIONES_INICIALES];
        for (int i = 0; i < inventario.length; i++)
        {
            inventario[i] = "Poción de curación";
        }
        int pociones = POCIONES_INICIALES;       //posiciones ocupadas: siempre están al principio del array (0 .. pociones-1)

        String guia = GUIA;

        //---Inicio con Bigotín---
        EleccionPersonaje(CP, guia);
        TorreExplicacion(CP.getNombre(), guia);
        Ataque(guia);

        //---INICIO DEL JUEGO---
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 NIVEL 1                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        //---Combate devuelve las pociones que quedan (int es primitivo: hay que recoger el valor)---
        pociones = Combate(CP, N1, N1, N2, N3, inventario, pociones, guia);
        int score = FinPartida(CP, N1, N1, N2, N3, guia);
        if (score >= 0)
        {
            return GuardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
        }
        pociones = ElegirPasillo(CP, N2, N3, inventario, pociones);
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 NIVEL 2                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        pociones = Combate(CP, N2, N1, N2, N3, inventario, pociones, guia);
        score = FinPartida(CP, N2, N1, N2, N3, guia);
        if (score >= 0)
        {
            return GuardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
        }
        pociones = ElegirPasillo(CP, N2, N3, inventario, pociones);
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 NIVEL 3                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        pociones = Combate(CP, N3, N1, N2, N3, inventario, pociones, guia);
        score = FinPartida(CP, N3, N1, N2, N3, guia);
        if (score >= 0)
        {
            return GuardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
        }

        //---Si llega aquí ha ganado los tres combates---
        score = InfoScore(CP, N1, N2, N3, false);
        PartidaGanada(guia, CP.getNombre(), score);
        return GuardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
    }

    //---Devuelve el score si la partida se acaba (perdida o abandonada) o -1 si sigue---
    public static int FinPartida(ClasePersonaje CP, ClaseMoustruo M, Nivel1 N1, Nivel2 N2, Nivel3 N3, String guia)
    {
        if (CP.getVida() <= 0)
        {
            int score = InfoScore(CP, N1, N2, N3, false);
            PartidaPerdida(guia, CP.getNombre(), score);
            return score;
        }

        //---El héroe sigue vivo y el mounstruo también: solo puede ser que haya abandonado---
        if (M.getVida() > 0)
        {
            int score = InfoScore(CP, N1, N2, N3, true);
            PartidaAbandonada(guia, CP.getNombre(), score);
            return score;
        }

        return -1;      //el score nunca es negativo, así que -1 significa que la partida continúa
    }


    //---Introducción de la historia---
    public static void Historia()
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 HISTORIA                 |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        //---Un párrafo cada vez, con pausa entre ellos---
        System.out.println("En el reino de Kiadur, la magia fluye por cada rincón. El maná recorre la tierra, los ríos y el aire, y cada persona nace con la capacidad de absorber una cantidad distinta. Pero no todo lo que habita este mundo es bondadoso: espíritus errantes y hadas malignas acechan en los bosques y las ruinas, alimentándose del maná de quienes se atreven a cruzar su camino.");
        Pausa();
        System.out.println("");
        System.out.println("Hoy es el día más importante para los jóvenes de Kiadur: el Despertar. Al alcanzar la edad adecuada, cada joven se presenta ante el altar del maná para descubrir cuánto poder puede absorber y elegir el inicio de su camino. Algunos se convertirán en magos, otros en guerreros, escuderos o curanderos. Esta elección sellará su destino para siempre.");
        Pausa();
        System.out.println("");
        System.out.println("Tú eres uno de esos jóvenes. Tras tu Despertar, tendrás que demostrar tu valía enfrentándote a tres criaturas que amenazan el reino, cada una más poderosa que la anterior. Solo quien sobreviva a los tres encuentros será recordado como un verdadero héroe.");
        Pausa();
    }

    //--Menú de inicio---
    public static void Inicio()
    {
        //---Ranking: dos arrays paralelos (la posición i de los dos es el mismo jugador) y un contador---
        String [] nombres = new String[MAXIMO_RANKING];
        int [] puntos = new int[MAXIMO_RANKING];

        //---Se carga una vez al arrancar: devuelve cuántos jugadores hay, o -1 si el xml tiene errores---
        int cantidad = CargarRanking(nombres, puntos);
        boolean archivoCorrecto = true;

        if (cantidad == -1)
        {
            archivoCorrecto = false;        //el archivo con errores no se sobrescribe
            cantidad = 0;
        }

        String eleccion = "";      //se lee como texto para que una letra no cierre el programa

        //---do-while: el menú se muestra al menos una vez y se repite hasta elegir Salir---
        do
        {
            System.out.println("");
            System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
            System.out.println("|                                          |");
            System.out.println("|      BIENVENIDO A TU ZONA PERSONAL       |");
            System.out.println("|                                          |");
            System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
            System.out.println("");
            Pausa();
            System.out.println("            (1) Nueva partida     ");
            System.out.println("            (2) Consultar ranking ");
            System.out.println("            (3) Ayuda             ");
            System.out.println("            (4) Salir             ");

            System.out.println("Elija que acción quiere hacer: ");
            eleccion = sc.nextLine();

            switch (eleccion)
            {
                case "1":
                    cantidad = Juego(nombres, puntos, cantidad, archivoCorrecto);
                    break;
                case "2":
                    Ranking(nombres, puntos, cantidad);
                    break;
                case "3":
                    Ayuda();
                    break;
                case "4":
                    System.out.println("Hasta pronto, héroe");
                    break;
                default:
                    System.out.println("Error");
                    Pausa();
                    break;
            }
        }
        while (!eleccion.equals("4"));      //si no se elige Salir, el menú vuelve a salir
    }

    //---Ayuda: explica las reglas, las acciones del combate y la puntuación (usa las constantes para que siempre coincida)---
    public static void Ayuda()
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                  AYUDA                   |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        System.out.println("REGLAS");
        System.out.println("- Tienes que superar tres mounstruos seguidos dentro de la torre, cada uno más fuerte que el anterior.");
        System.out.println("- Empiezas con " + VIDA_MAXIMA + " de vida y " + POCIONES_INICIALES + " pociones que curan " + CURACION_POCION + " de vida (nunca más de " + VIDA_MAXIMA + ").");
        System.out.println("- Los mounstruos son el Hada corrupta (50 de vida, fuerza 10), el Espectro de maná (100 de vida, fuerza 15) y el Guardián de la Torre (150 de vida, fuerza 18).");
        System.out.println("- La partida termina si ganas al tercer mounstruo, si tu vida llega a 0 o si abandonas.");
        System.out.println("- Entre nivel y nivel eliges un pasillo: puede haber un cofre con una poción o con una maldición.");
        Pausa();
        System.out.println("");
        System.out.println("ACCIONES EN TU TURNO");
        System.out.println("(1) Atacar: haces entre 5 menos y 5 más que el daño de tu arma.");
        System.out.println("(2) Usar cura: bebes una poción. Si no te quedan o tienes la vida al máximo, no pierdes el turno.");
        System.out.println("(3) Salir de la torre: abandonas la partida.");
        System.out.println("(4) Panel de información y (5) Ver inventario: no gastan el turno.");
        System.out.println("Si el mounstruo sigue vivo después de atacar o curarte, te ataca una vez con entre 5 y su fuerza de daño.");
        Pausa();
        System.out.println("");
        System.out.println("PUNTUACIÓN");
        System.out.println("- Si derrotas a 1 mounstruo consigues 100 puntos, a 2 consigues 450 y a los 3 consigues 1000.");
        System.out.println("- A esos puntos se les suma la vida que te quede.");
        System.out.println("- Si abandonas se restan 10 puntos (el score nunca baja de 0).");
        System.out.println("- Toda partida terminada (también si abandonas) se guarda en el ranking de los " + MAXIMO_RANKING + " mejores.");
        Pausa();
    }

    //---Creación del panel informativo durante el videojuego---
    public static void PanelInfo(ClasePersonaje CP, Nivel1 N1, Nivel2 N2, Nivel3 N3)
    {
        boolean respuesta = false;

        while (!respuesta)
        {
            System.out.println("");
            System.out.println("---------=[ PANEL DE INFORMACIÓN ]=---------");
            System.out.println("");
            System.out.println("    (1) Personaje");
            System.out.println("    (2) Mounstruos");
            System.out.println("    (3) Volver al juego");
    
            System.out.println("Elija su siguiente acción: ");
    
            String eleccion = sc.nextLine();
    
            switch (eleccion) {
                case "1":
                    InfoPersonaje(CP, N1, N2, N3);
                    break;
                case "2":
                    InfoMounstruos(N1, N2, N3);
                    break;
                case "3":
                    respuesta  = true;
                    break;
                default:
                    System.out.println("Elija una de las opciones disponibles");
                    Pausa();
                    break;
            }

        }
    }
    
    //--------------------------------------------FUNCIONES DEL VIDEOJUEGO----------------------------------------------------------

    public static void InfoPersonaje(ClasePersonaje CP, Nivel1 N1, Nivel2 N2, Nivel3 N3)
    {
        System.out.println("");
        System.out.println("--------------=[ PERSONAJE ]=---------------");
        System.out.println("");
        System.out.println("Nombre : " + CP.getNombre());
        System.out.println("Vida : " + CP.getVida());
        System.out.println("Daño : " + CP.getDaño());
        System.out.println("Score : " + InfoScore(CP, N1, N2, N3, false));
        Pausa();
    }

    public static void InfoMounstruos(Nivel1 N1, Nivel2 N2, Nivel3 N3)
    {
        System.out.println("");
        System.out.println("---------------=[ NIVEL 1 ]=----------------");
        System.out.println("");
        System.out.println("Nombre : " + N1.getNombre());
        System.out.println("Vida : " + N1.getVida());
        System.out.println("Fuerza máxima : " + N1.getFuerza());
        Pausa();
        System.out.println("");
        System.out.println("---------------=[ NIVEL 2 ]=----------------");
        System.out.println("");
        System.out.println("Nombre : " + N2.getNombre());
        System.out.println("Vida : " + N2.getVida());
        System.out.println("Fuerza máxima : " + N2.getFuerza());
        Pausa();
        System.out.println("");
        System.out.println("---------------=[ NIVEL 3 ]=----------------");
        System.out.println("");
        System.out.println("Nombre : " + N3.getNombre());
        System.out.println("Vida : " + N3.getVida());
        System.out.println("Fuerza máxima : " + N3.getFuerza());
        Pausa();
    }

    //---Devuelve las pociones que quedan, porque el cofre puede dar una---
    public static int CofreAleatorio(ClasePersonaje CP, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones)
    {
        //---Las maldiciones solo se usan aquí, así que se crean aquí---
        MaldicionAlmas MA = new MaldicionAlmas("Maldición de las Almas", 1, -20, null);
        MaldicionMuertos MM = new MaldicionMuertos("Maldición de los Muertos", 1, -15, null);
        MaldicionTorre MR = new MaldicionTorre("Maldición de la Torre", 1, 20, 5, null);

        String [] objetoGanado = {"Maldición Almas", "Maldición de Muertos" , "Maldición de la Torre", "Poción de curación"};
        int ganado = (int)(Math.random() * objetoGanado.length);      //número entre 0 y 3
        String objeto  = objetoGanado[ganado];
        System.out.println("");
        System.out.println("----------------=[ COFRE ]=-----------------");
        System.out.println("");
        System.out.println("Has ganado : " + objeto);
        Pausa();

        switch (ganado)
        {
            case 0:
                MA.ponerMaldiciónAlmas(CP);
                if (CP.getVida() < 1)
                {
                    CP.setVida(1);              //una maldición no te puede matar
                }
                System.out.println("Tu vida: " + CP.getVida());
                Pausa();
                break;
            case 1:
                MM.ponerMaldicionMuertos(CP);
                if (CP.getVida() < 1)
                {
                    CP.setVida(1);
                }
                System.out.println("Tu vida: " + CP.getVida());
                Pausa();
                break;
            case 2:
                if (N2.getVida() > 0)               //primer cofre: quedan nivel 2 y 3
                {
                    MR.ponerMaldicionMounstruosPrimerCofre(N2, N3);
                }
                else                                //segundo cofre: solo queda el nivel 3
                {
                    MR.ponerMaldicionMounstruosSegundoCofre(N3);
                }
                System.out.println("Los mounstruos que quedan tienen más vida y fuerza");
                Pausa();
                break;
            case 3:
                //---Solo cabe si hay un hueco libre en el inventario (no se sale de los límites del array)---
                if (pociones < inventario.length)
                {
                    inventario[pociones] = "Poción de curación";
                    pociones++;
                    System.out.println("La guardas en tu inventario. Pociones: " + pociones + "/" + inventario.length);
                }
                else
                {
                    System.out.println("Tu inventario está lleno, no puedes guardar la poción");
                }
                Pausa();
                break;
        }
        
        return pociones;
    }

    public static int PasilloAleatorio()
    {
        int pasillo = (int)(Math.random() * 2);      //0 o 1
        return pasillo;
    }

    //---Devuelve las pociones que quedan (si hay cofre puede cambiar)---
    public static int ElegirPasillo(ClasePersonaje CP, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones)
    {
        int pasilloCofre = PasilloAleatorio();   // dónde está el cofre esta vez

        System.out.println("");
        System.out.println("-----------=[ ELIGE UN PASILLO ]=-----------");
        System.out.println("");
        System.out.println("(1) Pasillo A \n" + "\n" + "(2) Pasillo B");
        String eleccion = sc.nextLine();
        int pasilloElegido = -1;      //si no se escribe 1 o 2, no se elige ningún pasillo con cofre

        if (eleccion.equals("1"))
        {
            pasilloElegido = 0;
        }
        else if (eleccion.equals("2"))
        {
            pasilloElegido = 1;
        }

        if (pasilloElegido == pasilloCofre)
        {
            System.out.println("¡Has encontrado un cofre!");
            Pausa();
            pociones = CofreAleatorio(CP, N2, N3, inventario, pociones);
        }
        else
        {
            System.out.println("No hay cofre en este pasillo. Pasas al siguiente nivel.");
            Pausa();
        }
        return pociones;
    }

  //---ranking---

    public static void Ranking(String [] nombres, int [] puntos, int cantidad)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 RANKING                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        VerRanking(nombres, puntos, cantidad);
        Pausa();
    }

    //---Guarda la partida terminada en el ranking y devuelve cuántos jugadores hay ahora---
    public static int GuardarPartida(String [] nombres, int [] puntos, int cantidad, String nombre, int score, boolean archivoCorrecto)
    {
        cantidad = ModificarRanking(nombres, puntos, cantidad, nombre, score);
        GuardarXml(nombres, puntos, cantidad, archivoCorrecto);
        return cantidad;
    }

    //---Carpeta de los xml: si el juego se ejecuta desde la carpeta Proyecto, está una carpeta más arriba---
    public static String CarpetaXml()
    {
        File carpeta = new File("02_XML");
        if (carpeta.exists())
        {
            return "02_XML/";
        }
        return "../02_XML/";
    }

    //---Ruta del xml del ranking---
    public static String RutaXml()
    {
        return CarpetaXml() + "ranking.xml";
    }

    //---Cargar: lee el xml línea a línea y mete cada jugador en los arrays---
    //---Devuelve cuántos jugadores ha cargado, o -1 si el archivo tiene errores---
    public static int CargarRanking(String [] nombres, int [] puntos)
    {
        File fichero = new File(RutaXml());

        if (!fichero.exists())
        {
            System.out.println("No se encontró el xml del ranking, se empieza con un ranking vacío");
            return 0;
        }

        int cantidad = 0;
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
                    if (cantidad == MAXIMO_RANKING)
                    {
                        error = "hay más de " + MAXIMO_RANKING + " jugadores";
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

        //---Si algo está mal se avisa y el archivo no se sobrescribe---
        if (!error.equals(""))
        {
            System.out.println("Error en el ranking: " + error + ".");
            System.out.println("El archivo no se va a modificar hasta que se corrija. Esta vez las partidas no se guardarán.");
            return -1;
        }
        return cantidad;
    }

    //---Ver: mostrar el ranking por pantalla---
    public static void VerRanking(String [] nombres, int [] puntos, int cantidad)
    {
        if (cantidad == 0)
        {
            System.out.println("Todavía no hay partidas en el ranking");
            return;
        }

        for (int i = 0; i < cantidad; i++)
        {
            System.out.println((i + 1) + ". " + nombres[i] + " - " + puntos[i] + " puntos");
        }
    }

    //---Modificar: mete la partida nueva en su sitio y devuelve cuántos jugadores hay ahora---
    //---Los arrays son referencias (se cambian también fuera), el contador es int y hay que devolverlo---
    public static int ModificarRanking(String [] nombres, int [] puntos, int cantidad, String nombre, int puntosNuevos)
    {
        //---Si el ranking está lleno, solo entra si supera al último---
        if (cantidad == MAXIMO_RANKING && puntosNuevos <= puntos[cantidad - 1])
        {
            System.out.println("Tu puntuación no entra en el ranking, el último tiene " + puntos[cantidad - 1] + " puntos");
            return cantidad;
        }

        //---Buscar la posición: se pasa a todos los que tienen más o los mismos puntos---
        //---Así en un empate el más antiguo queda delante---
        int posicion = 0;
        while (posicion < cantidad && puntos[posicion] >= puntosNuevos)
        {
            posicion++;
        }

        //---Si hay hueco el ranking crece, si está lleno el último se pierde---
        if (cantidad < MAXIMO_RANKING)
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

        return cantidad;
    }

    //---Guardar en el xml con la misma estructura que ranking.dtd (se reescribe con todos los jugadores)---
    public static void GuardarXml(String [] nombres, int [] puntos, int cantidad, boolean archivoCorrecto)
    {
        //---No se pisa un archivo que tenía errores---
        if (!archivoCorrecto)
        {
            System.out.println("La partida no se guarda porque el xml del ranking tiene errores");
            return;
        }

        try (FileWriter escritor = new FileWriter(RutaXml()))
        {
            escritor.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
            escritor.write("<?xml-stylesheet type=\"text/xsl\" href=\"ranking.xsl\"?>\n\n");
            escritor.write("<!DOCTYPE score SYSTEM \"ranking.dtd\">\n\n");
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

    //---Al guardar: & < > se escriben como &amp; &lt; &gt; para que el xml siga bien formado---
    public static String Escapar(String texto)
    {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    //---Al cargar: se hace al revés (el &amp; el último para no estropear los otros)---
    public static String Desescapar(String texto)
    {
        return texto.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
    }

    //---Score: 1000, 450 o 100 puntos si se derrotan 3, 2 o 1 mounstruos, más la vida que queda---
    //---Si se abandona se restan 10 puntos; el score nunca es negativo---
    public static int InfoScore(ClasePersonaje CP, Nivel1 N1, Nivel2 N2, Nivel3 N3, boolean abandonado)
    {
        int scorePartida = 0;
        int scoreVida = 0;
        int scoreAbandono = 10;

        scoreVida = Math.max(CP.getVida(), 0);      //la vida negativa no resta puntos

        //---La vida del mounstruo puede quedar negativa, por eso <= 0---
        if (N3.getVida() <= 0)
        {
            scorePartida = 1000;
        }
        else if (N2.getVida() <= 0)
        {
            scorePartida = 450;
        }
        else if (N1.getVida() <= 0)
        {
            scorePartida = 100;
        }

        int scoreTotal = scorePartida + scoreVida;

        if (abandonado)
        {
            scoreTotal = scoreTotal - scoreAbandono;
        }

        //---El score nunca puede ser negativo (al abandonar con poca vida podría quedar por debajo de 0)---
        if (scoreTotal < 0)
        {
            scoreTotal = 0;
        }
        return scoreTotal;
    }

    public static void PartidaPerdida(String guia,String nombre, int score)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|              PRUEBA FALLIDA              |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        System.out.println(guia + " : MIAUUUU! Menudo golpe ese, aunque hayas perdido la primera prueba no te pongas triste, la puedes volver a hacer.");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : " + nombre + " te dejo tu score de esta partida.");
        Pausa();
        System.out.println("");
        System.out.println("-------------=[ SCORE : " + score + " ]=-------------");
        System.out.println("");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Seguiremos en contacto, por ahora descansa un poco, que ese último golpe debió doler.");
        Pausa();
    }

    public static void PartidaGanada (String guia, String nombre, int score)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|            ¡PRUEBA SUPERADA!             |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        System.out.println(guia + " : MIAUUU! Ese golpe habla de quien manda aquí!!");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Felicidades por pasar la primera torre de tu vida, más en el fututo conocerás muchas más torres y convertirte en un gran heroe reconocido de nuestro reino.");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Te dejo tu score por aquí:");
        Pausa();
        System.out.println("");
        System.out.println("-------------=[ SCORE : " + score + " ]=-------------");
        System.out.println("");
        Pausa();
    }

    public static void PartidaAbandonada(String guia, String nombre, int score)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|            PRUEBA ABANDONADA             |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        System.out.println(guia + " : MIAUUUUU!! Que susto ese, no te preocupes, es tu primera prueba y es normal que no la finalices.");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Sigues teniendo tu score te la dejo por aquí para que la tengas:");
        Pausa();
        System.out.println("");
        System.out.println("-------------=[ SCORE : " + score + " ]=-------------");
        System.out.println("");
        Pausa();
    }
    

    //-------------------------------------------PARTE DE COMBATES------------------------------------------------------------------

    //---Combate por turnos: devuelve las pociones que quedan---
    //---CP y M son referencias: su vida cambia también fuera de este método; pociones es int y hay que devolverlo---
    public static int Combate(ClasePersonaje CP, ClaseMoustruo M, Nivel1 N1, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones, String guia)
    {
        boolean abandonado = false;

        System.out.println("");
        System.out.println("---------------=[ COMBATE ]=----------------");
        System.out.println("");
        System.out.println("Aparece " + M.getNombre() + " con " + M.getVida() + " de vida");
        Pausa();

        while (CP.getVida() > 0 && M.getVida() > 0 && !abandonado)
        {
            //---Turno del personaje: devuelve la acción elegida---
            String accion = VisualizacionAtaque(CP, M, N1, N2, N3, inventario, pociones);
            boolean turnoGastado = true;        //solo un ataque o una curación que funciona gastan el turno

            if (accion.equals("2"))
            {
                //---Si la curación no se puede hacer (sin pociones o con la vida al máximo) el contador no baja---
                int pocionesAntes = pociones;
                pociones = ConsumirCura(CP, inventario, pociones);
                turnoGastado = pociones < pocionesAntes;      //curación válida: el mounstruo responde
            }
            else if (accion.equals("3"))
            {
                abandonado = true;          //Juego() se encarga de llamar a PartidaAbandonada
                turnoGastado = false;
            }

            //---Si el turno no se ha gastado, el mounstruo no ataca y se vuelve a pedir la acción---
            if (turnoGastado && M.getVida() <= 0)
            {
                System.out.println("Has derrotado a " + M.getNombre());
                Pausa();
            }
            else if (turnoGastado)
            {
                //---Turno del mounstruo---
                AtaqueMounstruo(CP, M);

                //---Vida baja con el aviso de Bigotín---
                if (CP.getVida() > 0 && CP.getVida() <= VIDA_CRITICA)
                {
                    String respuestaEmergencia = Emergencia(guia);

                    if (respuestaEmergencia.equals("1"))
                    {
                        //---Beber la poción aquí también es la acción del turno: si se ha podido curar, el mounstruo responde una vez---
                        //---Si no se ha podido (sin pociones o con la vida al máximo) no se gasta el turno---
                        int pocionesAntes = pociones;
                        pociones = ConsumirCura(CP, inventario, pociones);
                        if (pociones < pocionesAntes && M.getVida() > 0)
                        {
                            AtaqueMounstruo(CP, M);
                        }
                    }
                    else if (respuestaEmergencia.equals("2"))
                    {
                        abandonado = true;
                    }
                }
            }
        }
        return pociones;
    }

    //---Ataque del mounstruo: le quita al héroe entre 5 y su fuerza de vida (los dos incluidos)---
    //---CP y M son referencias, así que el cambio de vida se ve también en Combate()---
    public static void AtaqueMounstruo(ClasePersonaje CP, ClaseMoustruo M)
    {
        int dañoMounstruo = (int)(Math.random() * (M.getFuerza() - 5 + 1)) + 5;      //entre 5 y su fuerza, los dos incluidos
        CP.setVida(Math.max(CP.getVida() - dañoMounstruo, 0));      //la vida nunca baja de 0
        System.out.println(M.getNombre() + " te hace " + dañoMounstruo + " de daño. Tu vida: " + CP.getVida());
        Pausa();
    }

    //---Menú de tu turno: devuelve "1" si has atacado, "2" si quieres curarte o "3" si sales de la torre---
    public static String VisualizacionAtaque(ClasePersonaje CP, ClaseMoustruo M, Nivel1 N1, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones)
    {
        while (true)
        {
            //---El menú se vuelve a pedir en cada vuelta para poder corregir una opción no válida---
            System.out.println("");
            System.out.println("---------------=[ TU TURNO ]=---------------");
            System.out.println("");
            System.out.println("(1) Atacar \n" + "\n" + "(2) Usar cura \n" + "\n" + "(3) Salir de la torre \n" + "\n" + "(4) Panel de información \n" + "\n" + "(5) Ver inventario");

            String respuestaAtaque = sc.nextLine();

            switch (respuestaAtaque)
            {
                case "1":
                    //---El daño depende del arma elegida: entre 5 menos y 5 más que su daño---
                    int maximo = CP.getDaño() + 5;
                    int minimo = CP.getDaño() - 5;
                    int numeroDaño = (int)(Math.random() * (maximo - minimo + 1)) + minimo;      //entre minimo y maximo, los dos incluidos

                    M.setVida(Math.max(M.getVida() - numeroDaño, 0));      //la vida del mounstruo tampoco baja de 0
                    System.out.println("Le haces " + numeroDaño + " de daño a " + M.getNombre() + ". Su vida: " + M.getVida());
                    Pausa();
                    return "1";

                case "2":
                    return "2";             //Combate() gasta la poción y, si se ha podido curar, el mounstruo responde

                case "3":
                    return "3";             //Combate() se encarga de salir

                case "4":
                    PanelInfo(CP, N1, N2, N3);      //mirar la información no gasta el turno
                    break;

                case "5":
                    VerInventario(inventario, pociones);      //mirar el inventario no gasta el turno
                    break;

                default:
                    System.out.println("Elija una de las opciones disponibles");
                    Pausa();
                    break;
            }
        }
    }

    //---Ver inventario: recorre solo las posiciones ocupadas del array---
    public static void VerInventario(String [] inventario, int pociones)
    {
        System.out.println("");
        System.out.println("---------------=[ INVENTARIO ]=-------------");
        System.out.println("");

        if (pociones == 0)
        {
            System.out.println("Tu inventario está vacío");
        }
        else
        {
            for (int i = 0; i < pociones; i++)
            {
                System.out.println((i + 1) + ". " + inventario[i]);
            }
        }

        System.out.println("");
        System.out.println("Pociones: " + pociones + "/" + inventario.length);
        Pausa();
    }

    //---Consumir cura: se gasta la última poción ocupada y se deja su hueco vacío---
    //---Devuelve las pociones que quedan: el array se cambia por referencia, el contador hay que devolverlo---
    public static int ConsumirCura(ClasePersonaje CP, String [] inventario, int pociones)
    {
        //---Con la vida al máximo no se gasta la poción---
        if (CP.getVida() >= VIDA_MAXIMA)
        {
            System.out.println("Tienes la vida al máximo, no necesitas curarte");
            Pausa();
            return pociones;
        }

        //---Si el contador está a 0 no hay ninguna posición ocupada que consumir---
        if (pociones == 0)
        {
            System.out.println("No te quedan pociones");
            Pausa();
            return pociones;
        }

        pociones--;                         //la última poción está en la posición pociones-1
        inventario[pociones] = null;        //se retira del inventario
        CP.setVida(Math.min(CP.getVida() + CURACION_POCION, VIDA_MAXIMA));      //nunca pasa de la vida máxima
        System.out.println("Te has curado. Tu vida: " + CP.getVida() + ". Pociones: " + pociones + "/" + inventario.length);
        Pausa();
        return pociones;
    }

    //-------------------------------------------BIGOTÍN--------------------------------------------------------------------------

    //---Elección del personaje---
    public static void EleccionPersonaje(ClasePersonaje CP, String guia)
    {
        SaludoInicial(CP);

        boolean elegido = false;

        //---Se repite hasta que se elija un personaje válido---
        while (!elegido)
        {
            System.out.println("");
            System.out.println("----------=[ ELIGE TU PERSONAJE ]=----------");
            System.out.println("");
            System.out.println("Escoja su personaje: \n" +
            "\n" + "1)Mago \n" + "\n" + "2) Caballero \n" + "\n" + "3) Arquero \n");

            String eleccionPersonaje = sc.nextLine();

            switch (eleccionPersonaje) {
                case "1":
                    ArmaMago(CP);
                    elegido = true;
                    break;
                case "2":
                    ArmaCaballero(CP);
                    elegido = true;
                    break;
                case "3":
                    ArmaArquero(CP);
                    elegido = true;
                    break;
                default:
                    System.out.println(guia + " : " + CP.getNombre() + " debes elegir un personaje para poder continuar con tu destino");
                    Pausa();
                    break;
            }
        }
    }

    //---Diálogos del Bigotín---
    public static void SaludoInicial(ClasePersonaje CP)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|               EL DESPERTAR               |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        System.out.println("Introduce tu nombre futuro heroe: ");
        String nombre = sc.nextLine();

        //---El nombre no puede estar vacío ni tener solo espacios (trim() quita los espacios de los lados)---
        while (nombre.trim().equals(""))
        {
            System.out.println("El nombre no puede estar vacío, escríbelo de nuevo: ");
            nombre = sc.nextLine();
        }
        CP.setNombre(nombre.trim());
        String guia = "Bigotín";
        System.out.println("?? : ¡Bienvenido " + CP.getNombre() + "!");
        Pausa();
        System.out.println("");
        System.out.println("?? : ¡Soy Bigotín! Tu compañero durante tus siguientes años escolares.");
        Pausa();
        System.out.println(" ");
        System.out.println(guia + " : Se que estás muy nervioso y para eso estoy aquí, yo te ayudaré a que no te arrepientas de tus decisiones.");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Esto es muy importante ya que no podrás cambiar tu decisión y quedará de por vida, así que ten en cuenta las siguientes cosas.");
        Pausa();
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|             LOS TRES CAMINOS             |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        System.out.println("""
          *
         /^\\
        /_*_\\
        (o.o)
        /| |\\
        """);
        //---El texto va debajo del dibujo en líneas cortas para que la terminal no lo corte---
        System.out.println("(1) Este es el mago, ¡y tendrás una fuerza extraordinaria!");
        System.out.println("    Eres un crack con el maná y una fuerza sin límites,");
        System.out.println("    pero te cansarás bastante rápido, al final dependes de tu maná.");
        System.out.println("");
        Pausa();
        System.out.println("");
        System.out.println(" ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~");
        System.out.println("");
        Pausa();
        System.out.println("""
            |
          O    |
         [#]===+-
         / \\
        """);
        System.out.println("(2) El Caballero! Un clásico, si te gusta moverte es lo tuyo.");
        System.out.println("    Tiene mucha agilidad y resistencia.");
        System.out.println("    Su única debilidad es que su fuerza es en base a tu condición física.");
        System.out.println("");
        Pausa();
        System.out.println("");
        System.out.println(" ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~");
        System.out.println("");
        Pausa();
        System.out.println("""
          O   )
         /|\\--->
         / \\  )
        """);
        System.out.println("(3) El arquero hace que tengas muchas posibilidades,");
        System.out.println("    ni te cansas tanto ni gastas tanto maná,");
        System.out.println("    eso sí, tu fuerza sí que es menor que la del caballero.");
        System.out.println("");
        Pausa();
    }

    public static void TorreExplicacion(String nombre, String guia)
    {
        boolean respuesta = false;

        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|          LA PRUEBA DE LA TORRE           |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();

        while (!respuesta)
        {
            System.out.println(guia + " : " + "Que emoción " + nombre + " ya tenemos nuestro destino elegido, y como primera prueba para ver tu nivel, los profesores han preparado la 'prueba de la torre'.");
            Pausa();
            System.out.println("");
            System.out.println(guia + " : No te preocupes de nada, generalmente la mayoría de estudiantes la pasan sin problema y al ser una prueba de inicio lo único que estás haciendo es poner tu consciencia dentro de la torre.");
            Pausa();
            System.out.println("");
            System.out.println(guia + " : Acuerdate que esto es solo una pequeña prueba, los profesores y yo estaremos aquí para cuelquier duda o ayuda que necesites.");
            Pausa();
            System.out.println("");
            System.out.println(guia + " : Las normas sin fáciles, si tu vida llega a 0 los profesores te sacarán inmediatamente de la torre, si ganas tu solo pasaste la prueba y podrás salir sin ningún problema.");
            Pausa();
            System.out.println("");
            System.out.println(guia + " : En caso de que tu conscienda se salga sola sin querer se te guardará tu score o incluso si te ves atrapado puedes obtar por abandonar la prueba, eso si, decidelo bien porque si abandonas se te restará 10 puntos de tu nota.");
            Pausa();
            System.out.println("");
            System.out.println(guia + " : Lo entendiste??");
            Pausa();
            System.out.println("");
            System.out.println(nombre + " : (1) Todo entendido!  (2) La verdad es que es mucha información");
            Pausa();

            String respuestaNuestra = sc.nextLine();
            
            if (respuestaNuestra.equals("1"))
            {
                respuesta = true;
            }
            else
            {
                respuesta = false;
            }
        }
    }

    public static void Ataque(String guia)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|            DENTRO DE LA TORRE            |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        System.out.println(guia + " : Ya estamos dentro de la torre!!!");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Este es un pequeñó recordatorio, puedes atacar, salir de la prueba o pedir información, se te abrirá un pequeño panel con la información que necesites.");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Pues buena suertee, MIAUUUU!!");
        Pausa();
    }

    //---Devuelve la opción elegida: "1" beber poción, "2" abandonar, otra cosa seguir---
    public static String Emergencia(String guia)
    {
        System.out.println("");
        System.out.println("-------------=[ ¡EMERGENCIA! ]=-------------");
        System.out.println("");
        System.out.println(guia + " : MIAUUUUU!!! Ten cuidado!!!");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Tu vida está en números críticos, puede ser que no soportes el siguiente golpe.");
        Pausa();
        System.out.println("");
        System.out.println(guia + " : Opta en este turno en tomar una poción de curación desde tu inventario, llamar a los profesores o seguir hasta el final.");
        Pausa();
        System.out.println("");
        System.out.println("(1) Beber poción \n" + "\n" + "(2) Abandonar prueba \n" + "\n" + "(3) Seguir partida");
        Pausa();

        String respuestaEmergencia = sc.nextLine();
        return respuestaEmergencia;         //Combate() hace lo que se ha elegido
    }


    //---Explicación de  tipo de armas---
    public static void ArmaMago(ClasePersonaje CP)
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Mago! Seguro serás el mejor.");
        Pausa();
        System.out.println("");
        System.out.println("Es importante que sepas que los magos son muy extraños a veces, te acostumbrarás.");
        Pausa();
        System.out.println("");
        System.out.println("Como sabrás, hace más de mil años se han diferenciado por dos grupos: 'Magos Ocuros' y 'Magos de luz'");
        Pausa();
        System.out.println("");
        System.out.println("Ahora es tu turno de elegir que tipo de mágia quieres aprender.");
        Pausa();
        System.out.println("");
        System.out.println("¡PERO CUIDADO!");
        Pausa();
        System.out.println("");
        System.out.println("Los magos de de luz aunque tengan menos fuerza tendrás más ataques al gastar menos maná y más resistencias.");
        Pausa();
        System.out.println("");
        System.out.println("Los magos oscuros con su fuerza bruta gastan más maná y se cansarán antes");
        Pausa();
        System.out.println("");

        CP.setClase("Mago");
        PoderLuz PL = new PoderLuz(null, 1, 16, null);
        PoderOscuridad PO = new PoderOscuridad(null, 1, 19, null);

        //---Se repite hasta que elija una opción válida (1 o 2)---
        boolean armaElegida = false;
        while (!armaElegida)
        {
            System.out.println("(1) Luz");
            System.out.println("(2) Oscuridad");

            String eleccionArma = sc.nextLine();

            if (eleccionArma.equals("1"))
            {
                CP.setArma(PL.getNombre());
                CP.setDaño(PL.getDaño());
                armaElegida = true;
            }
            else if (eleccionArma.equals("2"))
            {
                CP.setArma(PO.getNombre());
                CP.setDaño(PO.getDaño());
                armaElegida = true;
            }
            else
            {
                System.out.println("Esa opción no es válida, elige 1 o 2");
                Pausa();
            }
        }
    }

    public static void ArmaCaballero(ClasePersonaje CP)
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Caballero! Seguro serás el mejor.");
        Pausa();
        System.out.println("");
        System.out.println("Los caballeros siempre tienen a su mejor amiga ¡LA ESPADA!");
        Pausa();
        System.out.println("");
        System.out.println("Y como buen caballero es tu turno de elegir a tu compañera de vida");
        Pausa();
        System.out.println("");
        System.out.println("Muchos fortachones eligen la Espada Pesada ya que hace mucho daño.");
        Pausa();
        System.out.println("");
        System.out.println("¡PERO CUIDADO!");
        Pausa();
        System.out.println("");
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        Pausa();
        System.out.println("");
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        Pausa();
        System.out.println("");
        System.out.println("Sino ve a lo seguro como la mayoría, la Espada Ligera, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");
        Pausa();
        System.out.println("");

        CP.setClase("Caballero");
        EspadaPesada EP = new EspadaPesada(null, 1, 20, null);
        EspadaLigera EL = new EspadaLigera(null, 1, 17, null);

        //---Se repite hasta que elija una opción válida (1 o 2)---
        boolean armaElegida = false;
        while (!armaElegida)
        {
            System.out.println("(1) Espada pesada");
            System.out.println("(2) Espada ligera");

            String eleccionArma = sc.nextLine();

            if (eleccionArma.equals("1"))
            {
                CP.setArma(EP.getNombre());
                CP.setDaño(EP.getDaño());
                armaElegida = true;
            }
            else if (eleccionArma.equals("2"))
            {
                CP.setArma(EL.getNombre());
                CP.setDaño(EL.getDaño());
                armaElegida = true;
            }
            else
            {
                System.out.println("Esa opción no es válida, elige 1 o 2");
                Pausa();
            }
        }
    }

    public static void ArmaArquero(ClasePersonaje CP)
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Arquero! Seguro tendrás una puntería increible.");
        Pausa();
        System.out.println("");
        System.out.println("Los arqueros siempre tienen a su mejor amigo ¡EL ARCO!");
        Pausa();
        System.out.println("");
        System.out.println("Y como buen arquero es tu turno de elegir a tu compañero de vida");
        Pausa();
        System.out.println("");
        System.out.println("Muchos osados eligen el Arco Triple ya que hace mucho daño.");
        Pausa();
        System.out.println("");
        System.out.println("¡PERO CUIDADO!");
        Pausa();
        System.out.println("");
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        Pausa();
        System.out.println("");
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        Pausa();
        System.out.println("");
        System.out.println("Sino ve a lo seguro como la mayoría,el Arco Ligero, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");
        Pausa();
        System.out.println("");

        CP.setClase("Arquero");
        ArcoTriple AT = new ArcoTriple(null, 1, 17, null);
        ArcoSimple AS = new ArcoSimple(null, 1, 15, null);

        //---Se repite hasta que elija una opción válida (1 o 2)---
        boolean armaElegida = false;
        while (!armaElegida)
        {
            System.out.println("(1) Arco Triple");
            System.out.println("(2) Arco Ligero");

            String eleccionArma = sc.nextLine();

            if (eleccionArma.equals("1"))
            {
                CP.setArma(AT.getNombre());
                CP.setDaño(AT.getDaño());
                armaElegida = true;
            }
            else if (eleccionArma.equals("2"))
            {
                CP.setArma(AS.getNombre());
                CP.setDaño(AS.getDaño());
                armaElegida = true;
            }
            else
            {
                System.out.println("Esa opción no es válida, elige 1 o 2");
                Pausa();
            }
        }
    }

}
