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

    //---Pausa de 1,5 segundos entre textos para que se puedan leer---
    //---Parámetros: ninguno. Devuelve: nada---
    public static void pausa()
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

    //---Juega una partida entera: crea el héroe, los tres mounstruos y el inventario y hace los tres combates---
    //---Parámetros: los arrays del ranking (nombres y puntos), cuántos jugadores hay y si el xml se puede sobrescribir---
    //---Devuelve: cuántos jugadores tiene el ranking después de guardar la partida---
    public static int juego(String [] nombres, int [] puntos, int cantidad, boolean archivoCorrecto)
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
        eleccionPersonaje(CP, guia);
        torreExplicacion(CP.getNombre(), guia);
        ataque(guia);

        //---INICIO DEL JUEGO---
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 NIVEL 1                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        //---combate() devuelve las pociones que quedan (int es primitivo: hay que recoger el valor)---
        pociones = combate(CP, N1, N1, N2, N3, inventario, pociones, guia);
        int score = finPartida(CP, N1, N1, N2, N3, guia);
        if (score >= 0)
        {
            return guardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
        }
        pociones = elegirPasillo(CP, N2, N3, inventario, pociones);
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 NIVEL 2                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        pociones = combate(CP, N2, N1, N2, N3, inventario, pociones, guia);
        score = finPartida(CP, N2, N1, N2, N3, guia);
        if (score >= 0)
        {
            return guardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
        }
        pociones = elegirPasillo(CP, N2, N3, inventario, pociones);
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 NIVEL 3                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        pociones = combate(CP, N3, N1, N2, N3, inventario, pociones, guia);
        score = finPartida(CP, N3, N1, N2, N3, guia);
        if (score >= 0)
        {
            return guardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
        }

        //---Si llega aquí ha ganado los tres combates---
        score = infoScore(CP, N1, N2, N3, false);
        partidaGanada(guia, CP.getNombre(), score);
        return guardarPartida(nombres, puntos, cantidad, CP.getNombre(), score, archivoCorrecto);
    }

    //---Comprueba si la partida se acaba después de un combate (héroe sin vida o abandono)---
    //---Parámetros: el héroe, el mounstruo del combate, los tres mounstruos (para el score) y el nombre del guía---
    //---Devuelve: el score si la partida se acaba o -1 si sigue---
    public static int finPartida(ClasePersonaje CP, ClaseMoustruo M, Nivel1 N1, Nivel2 N2, Nivel3 N3, String guia)
    {
        if (CP.getVida() <= 0)
        {
            int score = infoScore(CP, N1, N2, N3, false);
            partidaPerdida(guia, CP.getNombre(), score);
            return score;
        }

        //---El héroe sigue vivo y el mounstruo también: solo puede ser que haya abandonado---
        if (M.getVida() > 0)
        {
            int score = infoScore(CP, N1, N2, N3, true);
            partidaAbandonada(guia, CP.getNombre(), score);
            return score;
        }

        return -1;      //el score nunca es negativo, así que -1 significa que la partida continúa
    }


    //---Muestra la introducción de la historia del reino de Kiadur---
    //---Parámetros: ninguno. Devuelve: nada---
    public static void historia()
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 HISTORIA                 |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        //---Un párrafo cada vez, con pausa entre ellos---
        System.out.println("En el reino de Kiadur, la magia fluye por cada rincón. El maná recorre la tierra, los ríos y el aire, y cada persona nace con la capacidad de absorber una cantidad distinta. Pero no todo lo que habita este mundo es bondadoso: espíritus errantes y hadas malignas acechan en los bosques y las ruinas, alimentándose del maná de quienes se atreven a cruzar su camino.");
        pausa();
        System.out.println("");
        System.out.println("Hoy es el día más importante para los jóvenes de Kiadur: el Despertar. Al alcanzar la edad adecuada, cada joven se presenta ante el altar del maná para descubrir cuánto poder puede absorber y elegir el inicio de su camino. Algunos se convertirán en magos, otros en guerreros, escuderos o curanderos. Esta elección sellará su destino para siempre.");
        pausa();
        System.out.println("");
        System.out.println("Tú eres uno de esos jóvenes. Tras tu Despertar, tendrás que demostrar tu valía enfrentándote a tres criaturas que amenazan el reino, cada una más poderosa que la anterior. Solo quien sobreviva a los tres encuentros será recordado como un verdadero héroe.");
        pausa();
    }

    //---Menú principal con do-while y switch: Nueva partida, Consultar ranking, Ayuda y Salir---
    //---Crea los arrays del ranking, carga el xml una vez y se los pasa a los demás métodos---
    //---Parámetros: ninguno. Devuelve: nada---
    public static void inicio()
    {
        //---Ranking: dos arrays paralelos (la posición i de los dos es el mismo jugador) y un contador---
        String [] nombres = new String[MAXIMO_RANKING];
        int [] puntos = new int[MAXIMO_RANKING];

        //---Se carga una vez al arrancar: devuelve cuántos jugadores hay, o -1 si el xml tiene errores---
        int cantidad = cargarRanking(nombres, puntos);
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
            pausa();
            System.out.println("            (1) Nueva partida     ");
            System.out.println("            (2) Consultar ranking ");
            System.out.println("            (3) Ayuda             ");
            System.out.println("            (4) Salir             ");

            System.out.println("Elija que acción quiere hacer: ");
            eleccion = sc.nextLine();

            switch (eleccion)
            {
                case "1":
                    cantidad = juego(nombres, puntos, cantidad, archivoCorrecto);
                    break;
                case "2":
                    ranking(nombres, puntos, cantidad);
                    break;
                case "3":
                    ayuda();
                    break;
                case "4":
                    System.out.println("Hasta pronto, héroe");
                    break;
                default:
                    System.out.println("Error");
                    pausa();
                    break;
            }
        }
        while (!eleccion.equals("4"));      //si no se elige Salir, el menú vuelve a salir
    }

    //---Explica las reglas, las acciones del combate y la puntuación---
    //---Parámetros: ninguno. Devuelve: nada---
    public static void ayuda()
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                  AYUDA                   |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        System.out.println("REGLAS");
        System.out.println("- Tienes que superar tres mounstruos seguidos dentro de la torre, cada uno más fuerte que el anterior.");
        System.out.println("- Empiezas con " + VIDA_MAXIMA + " de vida y " + POCIONES_INICIALES + " pociones que curan " + CURACION_POCION + " de vida (nunca más de " + VIDA_MAXIMA + ").");
        System.out.println("- Los mounstruos son el Hada corrupta (50 de vida, fuerza 10), el Espectro de maná (100 de vida, fuerza 15) y el Guardián de la Torre (150 de vida, fuerza 18).");
        System.out.println("- La partida termina si ganas al tercer mounstruo, si tu vida llega a 0 o si abandonas.");
        System.out.println("- Entre nivel y nivel eliges un pasillo: puede haber un cofre con una poción o con una maldición.");
        pausa();
        System.out.println("");
        System.out.println("ACCIONES EN TU TURNO");
        System.out.println("(1) Atacar: haces entre 5 menos y 5 más que el daño de tu arma.");
        System.out.println("(2) Usar cura: bebes una poción. Si no te quedan o tienes la vida al máximo, no pierdes el turno.");
        System.out.println("(3) Salir de la torre: abandonas la partida.");
        System.out.println("(4) Panel de información y (5) Ver inventario: no gastan el turno.");
        System.out.println("Si el mounstruo sigue vivo después de atacar o curarte, te ataca una vez con entre 5 y su fuerza de daño.");
        pausa();
        System.out.println("");
        System.out.println("PUNTUACIÓN");
        System.out.println("- Si derrotas a 1 mounstruo consigues 100 puntos, a 2 consigues 450 y a los 3 consigues 1000.");
        System.out.println("- A esos puntos se les suma la vida que te quede.");
        System.out.println("- Si abandonas se restan 10 puntos (el score nunca baja de 0).");
        System.out.println("- Toda partida terminada (también si abandonas) se guarda en el ranking de los " + MAXIMO_RANKING + " mejores.");
        pausa();
    }

    //---Panel de información del combate: deja ver los datos del héroe o de los mounstruos sin gastar el turno---
    //---Parámetros: el héroe y los tres mounstruos. Devuelve: nada---
    public static void panelInfo(ClasePersonaje CP, Nivel1 N1, Nivel2 N2, Nivel3 N3)
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
                    infoPersonaje(CP, N1, N2, N3);
                    break;
                case "2":
                    infoMounstruos(N1, N2, N3);
                    break;
                case "3":
                    respuesta  = true;
                    break;
                default:
                    System.out.println("Elija una de las opciones disponibles");
                    pausa();
                    break;
            }

        }
    }
    
    //--------------------------------------------FUNCIONES DEL VIDEOJUEGO----------------------------------------------------------

    //---Muestra el nombre, la vida, el daño y el score actual del héroe---
    //---Parámetros: el héroe y los tres mounstruos (para calcular el score). Devuelve: nada---
    public static void infoPersonaje(ClasePersonaje CP, Nivel1 N1, Nivel2 N2, Nivel3 N3)
    {
        System.out.println("");
        System.out.println("--------------=[ PERSONAJE ]=---------------");
        System.out.println("");
        System.out.println("Nombre : " + CP.getNombre());
        System.out.println("Vida : " + CP.getVida());
        System.out.println("Daño : " + CP.getDaño());
        System.out.println("Score : " + infoScore(CP, N1, N2, N3, false));
        pausa();
    }

    //---Muestra la vida y la fuerza de los tres mounstruos---
    //---Parámetros: los tres mounstruos. Devuelve: nada---
    public static void infoMounstruos(Nivel1 N1, Nivel2 N2, Nivel3 N3)
    {
        System.out.println("");
        System.out.println("---------------=[ NIVEL 1 ]=----------------");
        System.out.println("");
        System.out.println("Nombre : " + N1.getNombre());
        System.out.println("Vida : " + N1.getVida());
        System.out.println("Fuerza máxima : " + N1.getFuerza());
        pausa();
        System.out.println("");
        System.out.println("---------------=[ NIVEL 2 ]=----------------");
        System.out.println("");
        System.out.println("Nombre : " + N2.getNombre());
        System.out.println("Vida : " + N2.getVida());
        System.out.println("Fuerza máxima : " + N2.getFuerza());
        pausa();
        System.out.println("");
        System.out.println("---------------=[ NIVEL 3 ]=----------------");
        System.out.println("");
        System.out.println("Nombre : " + N3.getNombre());
        System.out.println("Vida : " + N3.getVida());
        System.out.println("Fuerza máxima : " + N3.getFuerza());
        pausa();
    }

    //---Abre un cofre al azar: una de las tres maldiciones o una poción (solo si hay hueco en el inventario)---
    //---Parámetros: el héroe, los mounstruos que quedan, el array del inventario y las pociones que hay---
    //---Devuelve: las pociones que quedan, porque el cofre puede dar una---
    public static int cofreAleatorio(ClasePersonaje CP, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones)
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
        pausa();

        switch (ganado)
        {
            case 0:
                MA.ponerMaldiciónAlmas(CP);
                if (CP.getVida() < 1)
                {
                    CP.setVida(1);              //una maldición no te puede matar
                }
                System.out.println("Tu vida: " + CP.getVida());
                pausa();
                break;
            case 1:
                MM.ponerMaldicionMuertos(CP);
                if (CP.getVida() < 1)
                {
                    CP.setVida(1);
                }
                System.out.println("Tu vida: " + CP.getVida());
                pausa();
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
                pausa();
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
                pausa();
                break;
        }
        
        return pociones;
    }

    //---Elige al azar en cuál de los dos pasillos está el cofre---
    //---Parámetros: ninguno. Devuelve: 0 o 1---
    public static int pasilloAleatorio()
    {
        int pasillo = (int)(Math.random() * 2);      //0 o 1
        return pasillo;
    }

    //---Entre nivel y nivel el jugador elige un pasillo; si coincide con el del cofre, se abre---
    //---Parámetros: el héroe, los mounstruos que quedan, el array del inventario y las pociones que hay---
    //---Devuelve: las pociones que quedan (si hay cofre puede cambiar)---
    public static int elegirPasillo(ClasePersonaje CP, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones)
    {
        int pasilloCofre = pasilloAleatorio();   // dónde está el cofre esta vez

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
            pausa();
            pociones = cofreAleatorio(CP, N2, N3, inventario, pociones);
        }
        else
        {
            System.out.println("No hay cofre en este pasillo. Pasas al siguiente nivel.");
            pausa();
        }
        return pociones;
    }

  //---ranking---

    //---Pantalla de Consultar ranking del menú---
    //---Parámetros: los arrays del ranking y cuántos jugadores hay. Devuelve: nada---
    public static void ranking(String [] nombres, int [] puntos, int cantidad)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 RANKING                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        verRanking(nombres, puntos, cantidad);
        pausa();
    }

    //---Guarda la partida terminada en el ranking (arrays y xml)---
    //---Parámetros: los arrays del ranking, cuántos jugadores hay, el nombre, el score y si el xml se puede sobrescribir---
    //---Devuelve: cuántos jugadores hay ahora en el ranking---
    public static int guardarPartida(String [] nombres, int [] puntos, int cantidad, String nombre, int score, boolean archivoCorrecto)
    {
        cantidad = modificarRanking(nombres, puntos, cantidad, nombre, score);
        guardarXml(nombres, puntos, cantidad, archivoCorrecto);
        return cantidad;
    }

    //---Busca la carpeta 02_XML: si el juego se ejecuta desde la carpeta Proyecto, está una carpeta más arriba---
    //---Parámetros: ninguno. Devuelve: la ruta de la carpeta---
    public static String carpetaXml()
    {
        File carpeta = new File("02_XML");
        if (carpeta.exists())
        {
            return "02_XML/";
        }
        return "../02_XML/";
    }

    //---Ruta del xml del ranking---
    //---Parámetros: ninguno. Devuelve: la ruta de ranking.xml---
    public static String rutaXml()
    {
        return carpetaXml() + "ranking.xml";
    }

    //---Lee el xml línea a línea y mete cada jugador en los arrays---
    //---Parámetros: los arrays del ranking (se rellenan por referencia)---
    //---Devuelve: cuántos jugadores ha cargado, o -1 si el archivo tiene errores---
    public static int cargarRanking(String [] nombres, int [] puntos)
    {
        File fichero = new File(rutaXml());

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
                    nombre = desescapar(linea.replace("<nombre>", "").replace("</nombre>", ""));
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

    //---Muestra el ranking por pantalla o un aviso si está vacío---
    //---Parámetros: los arrays del ranking y cuántos jugadores hay. Devuelve: nada---
    public static void verRanking(String [] nombres, int [] puntos, int cantidad)
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

    //---Mete la partida nueva en su sitio (de mayor a menor; en un empate el más antiguo queda delante)---
    //---Si está lleno, solo entra si supera al décimo. Los arrays son referencias, el contador es int y hay que devolverlo---
    //---Parámetros: los arrays del ranking, cuántos jugadores hay, el nombre y los puntos nuevos---
    //---Devuelve: cuántos jugadores hay ahora---
    public static int modificarRanking(String [] nombres, int [] puntos, int cantidad, String nombre, int puntosNuevos)
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

    //---Reescribe el xml con todos los jugadores, con la misma estructura que ranking.dtd---
    //---Parámetros: los arrays del ranking, cuántos jugadores hay y si el xml se puede sobrescribir. Devuelve: nada---
    public static void guardarXml(String [] nombres, int [] puntos, int cantidad, boolean archivoCorrecto)
    {
        //---No se pisa un archivo que tenía errores---
        if (!archivoCorrecto)
        {
            System.out.println("La partida no se guarda porque el xml del ranking tiene errores");
            return;
        }

        try (FileWriter escritor = new FileWriter(rutaXml()))
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
                escritor.write("            <nombre>" + escapar(nombres[i]) + "</nombre>\n");
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

    //---Al guardar, & < > se escriben como &amp; &lt; &gt; para que el xml siga bien formado---
    //---Parámetros: el texto. Devuelve: el texto escapado---
    public static String escapar(String texto)
    {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    //---Al cargar se hace al revés (el &amp; el último para no estropear los otros)---
    //---Parámetros: el texto del xml. Devuelve: el texto normal---
    public static String desescapar(String texto)
    {
        return texto.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
    }

    //---Calcula el score: 1000, 450 o 100 puntos si se derrotan 3, 2 o 1 mounstruos, más la vida que queda---
    //---Si se abandona se restan 10 puntos; el score nunca es negativo---
    //---Parámetros: el héroe, los tres mounstruos y si ha abandonado. Devuelve: el score---
    public static int infoScore(ClasePersonaje CP, Nivel1 N1, Nivel2 N2, Nivel3 N3, boolean abandonado)
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

    //---Pantalla final cuando la vida del héroe llega a 0---
    //---Parámetros: el nombre del guía, el nombre del héroe y su score. Devuelve: nada---
    public static void partidaPerdida(String guia,String nombre, int score)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|              PRUEBA FALLIDA              |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        System.out.println(guia + " : MIAUUUU! Menudo golpe ese, aunque hayas perdido la primera prueba no te pongas triste, la puedes volver a hacer.");
        pausa();
        System.out.println("");
        System.out.println(guia + " : " + nombre + " te dejo tu score de esta partida.");
        pausa();
        System.out.println("");
        System.out.println("-------------=[ SCORE : " + score + " ]=-------------");
        System.out.println("");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Seguiremos en contacto, por ahora descansa un poco, que ese último golpe debió doler.");
        pausa();
    }

    //---Pantalla final cuando se derrota al tercer mounstruo---
    //---Parámetros: el nombre del guía, el nombre del héroe y su score. Devuelve: nada---
    public static void partidaGanada (String guia, String nombre, int score)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|            ¡PRUEBA SUPERADA!             |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        System.out.println(guia + " : MIAUUU! Ese golpe habla de quien manda aquí!!");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Felicidades por pasar la primera torre de tu vida, más en el fututo conocerás muchas más torres y convertirte en un gran heroe reconocido de nuestro reino.");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Te dejo tu score por aquí:");
        pausa();
        System.out.println("");
        System.out.println("-------------=[ SCORE : " + score + " ]=-------------");
        System.out.println("");
        pausa();
    }

    //---Pantalla final cuando el héroe sale de la torre---
    //---Parámetros: el nombre del guía, el nombre del héroe y su score. Devuelve: nada---
    public static void partidaAbandonada(String guia, String nombre, int score)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|            PRUEBA ABANDONADA             |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        System.out.println(guia + " : MIAUUUUU!! Que susto ese, no te preocupes, es tu primera prueba y es normal que no la finalices.");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Sigues teniendo tu score te la dejo por aquí para que la tengas:");
        pausa();
        System.out.println("");
        System.out.println("-------------=[ SCORE : " + score + " ]=-------------");
        System.out.println("");
        pausa();
    }
    

    //-------------------------------------------PARTE DE COMBATES------------------------------------------------------------------

    //---Combate por turnos con while: sigue mientras los dos tengan vida y no se abandone---
    //---CP y M son referencias: su vida cambia también fuera de este método; pociones es int y hay que devolverlo---
    //---Parámetros: el héroe, el mounstruo del combate, los tres mounstruos, el inventario, las pociones y el guía---
    //---Devuelve: las pociones que quedan---
    public static int combate(ClasePersonaje CP, ClaseMoustruo M, Nivel1 N1, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones, String guia)
    {
        boolean abandonado = false;

        System.out.println("");
        System.out.println("---------------=[ COMBATE ]=----------------");
        System.out.println("");
        System.out.println("Aparece " + M.getNombre() + " con " + M.getVida() + " de vida");
        pausa();

        while (CP.getVida() > 0 && M.getVida() > 0 && !abandonado)
        {
            //---Turno del personaje: devuelve la acción elegida---
            String accion = visualizacionAtaque(CP, M, N1, N2, N3, inventario, pociones);
            boolean turnoGastado = true;        //solo un ataque o una curación que funciona gastan el turno

            if (accion.equals("2"))
            {
                //---Si la curación no se puede hacer (sin pociones o con la vida al máximo) el contador no baja---
                int pocionesAntes = pociones;
                pociones = consumirCura(CP, inventario, pociones);
                turnoGastado = pociones < pocionesAntes;      //curación válida: el mounstruo responde
            }
            else if (accion.equals("3"))
            {
                abandonado = true;          //juego() se encarga de llamar a partidaAbandonada()
                turnoGastado = false;
            }

            //---Si el turno no se ha gastado, el mounstruo no ataca y se vuelve a pedir la acción---
            if (turnoGastado && M.getVida() <= 0)
            {
                System.out.println("Has derrotado a " + M.getNombre());
                pausa();
            }
            else if (turnoGastado)
            {
                //---Turno del mounstruo---
                ataqueMounstruo(CP, M);

                //---Vida baja con el aviso de Bigotín---
                if (CP.getVida() > 0 && CP.getVida() <= VIDA_CRITICA)
                {
                    String respuestaEmergencia = emergencia(guia);

                    if (respuestaEmergencia.equals("1"))
                    {
                        //---Beber la poción aquí también es la acción del turno: si se ha podido curar, el mounstruo responde una vez---
                        //---Si no se ha podido (sin pociones o con la vida al máximo) no se gasta el turno---
                        int pocionesAntes = pociones;
                        pociones = consumirCura(CP, inventario, pociones);
                        if (pociones < pocionesAntes && M.getVida() > 0)
                        {
                            ataqueMounstruo(CP, M);
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
    //---Parámetros: el héroe y el mounstruo (referencias: el cambio de vida se ve también en combate()). Devuelve: nada---
    public static void ataqueMounstruo(ClasePersonaje CP, ClaseMoustruo M)
    {
        int dañoMounstruo = (int)(Math.random() * (M.getFuerza() - 5 + 1)) + 5;      //entre 5 y su fuerza, los dos incluidos
        CP.setVida(Math.max(CP.getVida() - dañoMounstruo, 0));      //la vida nunca baja de 0
        System.out.println(M.getNombre() + " te hace " + dañoMounstruo + " de daño. Tu vida: " + CP.getVida());
        pausa();
    }

    //---Menú de tu turno: atacar, curarse, salir, panel de información o inventario (estos dos no gastan el turno)---
    //---Parámetros: el héroe, el mounstruo del combate, los tres mounstruos, el inventario y las pociones---
    //---Devuelve: "1" si has atacado, "2" si quieres curarte o "3" si sales de la torre---
    public static String visualizacionAtaque(ClasePersonaje CP, ClaseMoustruo M, Nivel1 N1, Nivel2 N2, Nivel3 N3, String [] inventario, int pociones)
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
                    pausa();
                    return "1";

                case "2":
                    return "2";             //combate() gasta la poción y, si se ha podido curar, el mounstruo responde

                case "3":
                    return "3";             //combate() se encarga de salir

                case "4":
                    panelInfo(CP, N1, N2, N3);      //mirar la información no gasta el turno
                    break;

                case "5":
                    verInventario(inventario, pociones);      //mirar el inventario no gasta el turno
                    break;

                default:
                    System.out.println("Elija una de las opciones disponibles");
                    pausa();
                    break;
            }
        }
    }

    //---Muestra el inventario recorriendo solo las posiciones ocupadas del array---
    //---Parámetros: el array del inventario y las pociones que hay. Devuelve: nada---
    public static void verInventario(String [] inventario, int pociones)
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
        pausa();
    }

    //---Bebe la última poción ocupada y deja su hueco vacío (sin pociones o con la vida al máximo no se gasta)---
    //---Parámetros: el héroe, el array del inventario (se cambia por referencia) y las pociones que hay---
    //---Devuelve: las pociones que quedan---
    public static int consumirCura(ClasePersonaje CP, String [] inventario, int pociones)
    {
        //---Con la vida al máximo no se gasta la poción---
        if (CP.getVida() >= VIDA_MAXIMA)
        {
            System.out.println("Tienes la vida al máximo, no necesitas curarte");
            pausa();
            return pociones;
        }

        //---Si el contador está a 0 no hay ninguna posición ocupada que consumir---
        if (pociones == 0)
        {
            System.out.println("No te quedan pociones");
            pausa();
            return pociones;
        }

        pociones--;                         //la última poción está en la posición pociones-1
        inventario[pociones] = null;        //se retira del inventario
        CP.setVida(Math.min(CP.getVida() + CURACION_POCION, VIDA_MAXIMA));      //nunca pasa de la vida máxima
        System.out.println("Te has curado. Tu vida: " + CP.getVida() + ". Pociones: " + pociones + "/" + inventario.length);
        pausa();
        return pociones;
    }

    //-------------------------------------------BIGOTÍN--------------------------------------------------------------------------

    //---Elección del personaje (mago, caballero o arquero); se repite hasta elegir uno válido---
    //---Parámetros: el héroe y el nombre del guía. Devuelve: nada---
    public static void eleccionPersonaje(ClasePersonaje CP, String guia)
    {
        saludoInicial(CP);

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
                    armaMago(CP);
                    elegido = true;
                    break;
                case "2":
                    armaCaballero(CP);
                    elegido = true;
                    break;
                case "3":
                    armaArquero(CP);
                    elegido = true;
                    break;
                default:
                    System.out.println(guia + " : " + CP.getNombre() + " debes elegir un personaje para poder continuar con tu destino");
                    pausa();
                    break;
            }
        }
    }

    //---Pide el nombre del héroe (no puede estar vacío) y Bigotín se presenta---
    //---Parámetros: el héroe. Devuelve: nada---
    public static void saludoInicial(ClasePersonaje CP)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|               EL DESPERTAR               |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
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
        pausa();
        System.out.println("");
        System.out.println("?? : ¡Soy Bigotín! Tu compañero durante tus siguientes años escolares.");
        pausa();
        System.out.println(" ");
        System.out.println(guia + " : Se que estás muy nervioso y para eso estoy aquí, yo te ayudaré a que no te arrepientas de tus decisiones.");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Esto es muy importante ya que no podrás cambiar tu decisión y quedará de por vida, así que ten en cuenta las siguientes cosas.");
        pausa();
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|             LOS TRES CAMINOS             |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
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
        pausa();
        System.out.println("");
        System.out.println(" ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~");
        System.out.println("");
        pausa();
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
        pausa();
        System.out.println("");
        System.out.println(" ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~ * ~");
        System.out.println("");
        pausa();
        System.out.println("""
          O   )
         /|\\--->
         / \\  )
        """);
        System.out.println("(3) El arquero hace que tengas muchas posibilidades,");
        System.out.println("    ni te cansas tanto ni gastas tanto maná,");
        System.out.println("    eso sí, tu fuerza sí que es menor que la del caballero.");
        System.out.println("");
        pausa();
    }

    //---Bigotín explica las normas de la torre y lo repite hasta que el jugador lo entiende---
    //---Parámetros: el nombre del héroe y el nombre del guía. Devuelve: nada---
    public static void torreExplicacion(String nombre, String guia)
    {
        boolean respuesta = false;

        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|          LA PRUEBA DE LA TORRE           |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();

        while (!respuesta)
        {
            System.out.println(guia + " : " + "Que emoción " + nombre + " ya tenemos nuestro destino elegido, y como primera prueba para ver tu nivel, los profesores han preparado la 'prueba de la torre'.");
            pausa();
            System.out.println("");
            System.out.println(guia + " : No te preocupes de nada, generalmente la mayoría de estudiantes la pasan sin problema y al ser una prueba de inicio lo único que estás haciendo es poner tu consciencia dentro de la torre.");
            pausa();
            System.out.println("");
            System.out.println(guia + " : Acuerdate que esto es solo una pequeña prueba, los profesores y yo estaremos aquí para cuelquier duda o ayuda que necesites.");
            pausa();
            System.out.println("");
            System.out.println(guia + " : Las normas sin fáciles, si tu vida llega a 0 los profesores te sacarán inmediatamente de la torre, si ganas tu solo pasaste la prueba y podrás salir sin ningún problema.");
            pausa();
            System.out.println("");
            System.out.println(guia + " : En caso de que tu conscienda se salga sola sin querer se te guardará tu score o incluso si te ves atrapado puedes obtar por abandonar la prueba, eso si, decidelo bien porque si abandonas se te restará 10 puntos de tu nota.");
            pausa();
            System.out.println("");
            System.out.println(guia + " : Lo entendiste??");
            pausa();
            System.out.println("");
            System.out.println(nombre + " : (1) Todo entendido!  (2) La verdad es que es mucha información");
            pausa();

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

    //---Bigotín explica cómo funciona el combate---
    //---Parámetros: el nombre del guía. Devuelve: nada---
    public static void ataque(String guia)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|            DENTRO DE LA TORRE            |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        pausa();
        System.out.println(guia + " : Ya estamos dentro de la torre!!!");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Este es un pequeñó recordatorio, puedes atacar, salir de la prueba o pedir información, se te abrirá un pequeño panel con la información que necesites.");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Pues buena suertee, MIAUUUU!!");
        pausa();
    }

    //---Aviso de Bigotín cuando la vida está en números críticos---
    //---Parámetros: el nombre del guía---
    //---Devuelve: la opción elegida: "1" beber poción, "2" abandonar, otra cosa seguir---
    public static String emergencia(String guia)
    {
        System.out.println("");
        System.out.println("-------------=[ ¡EMERGENCIA! ]=-------------");
        System.out.println("");
        System.out.println(guia + " : MIAUUUUU!!! Ten cuidado!!!");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Tu vida está en números críticos, puede ser que no soportes el siguiente golpe.");
        pausa();
        System.out.println("");
        System.out.println(guia + " : Opta en este turno en tomar una poción de curación desde tu inventario, llamar a los profesores o seguir hasta el final.");
        pausa();
        System.out.println("");
        System.out.println("(1) Beber poción \n" + "\n" + "(2) Abandonar prueba \n" + "\n" + "(3) Seguir partida");
        pausa();

        String respuestaEmergencia = sc.nextLine();
        return respuestaEmergencia;         //combate() hace lo que se ha elegido
    }


    //---Explicación de las armas del mago y elección entre Luz y Oscuridad (se repite si la opción no es válida)---
    //---Parámetros: el héroe (se le pone la clase, el arma y su daño). Devuelve: nada---
    public static void armaMago(ClasePersonaje CP)
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Mago! Seguro serás el mejor.");
        pausa();
        System.out.println("");
        System.out.println("Es importante que sepas que los magos son muy extraños a veces, te acostumbrarás.");
        pausa();
        System.out.println("");
        System.out.println("Como sabrás, hace más de mil años se han diferenciado por dos grupos: 'Magos Ocuros' y 'Magos de luz'");
        pausa();
        System.out.println("");
        System.out.println("Ahora es tu turno de elegir que tipo de mágia quieres aprender.");
        pausa();
        System.out.println("");
        System.out.println("¡PERO CUIDADO!");
        pausa();
        System.out.println("");
        System.out.println("Los magos de de luz aunque tengan menos fuerza tendrás más ataques al gastar menos maná y más resistencias.");
        pausa();
        System.out.println("");
        System.out.println("Los magos oscuros con su fuerza bruta gastan más maná y se cansarán antes");
        pausa();
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
                pausa();
            }
        }
    }

    //---Explicación de las armas del caballero y elección entre espada pesada y ligera (se repite si la opción no es válida)---
    //---Parámetros: el héroe (se le pone la clase, el arma y su daño). Devuelve: nada---
    public static void armaCaballero(ClasePersonaje CP)
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Caballero! Seguro serás el mejor.");
        pausa();
        System.out.println("");
        System.out.println("Los caballeros siempre tienen a su mejor amiga ¡LA ESPADA!");
        pausa();
        System.out.println("");
        System.out.println("Y como buen caballero es tu turno de elegir a tu compañera de vida");
        pausa();
        System.out.println("");
        System.out.println("Muchos fortachones eligen la Espada Pesada ya que hace mucho daño.");
        pausa();
        System.out.println("");
        System.out.println("¡PERO CUIDADO!");
        pausa();
        System.out.println("");
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        pausa();
        System.out.println("");
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        pausa();
        System.out.println("");
        System.out.println("Sino ve a lo seguro como la mayoría, la Espada Ligera, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");
        pausa();
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
                pausa();
            }
        }
    }

    //---Explicación de las armas del arquero y elección entre arco triple y ligero (se repite si la opción no es válida)---
    //---Parámetros: el héroe (se le pone la clase, el arma y su daño). Devuelve: nada---
    public static void armaArquero(ClasePersonaje CP)
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Arquero! Seguro tendrás una puntería increible.");
        pausa();
        System.out.println("");
        System.out.println("Los arqueros siempre tienen a su mejor amigo ¡EL ARCO!");
        pausa();
        System.out.println("");
        System.out.println("Y como buen arquero es tu turno de elegir a tu compañero de vida");
        pausa();
        System.out.println("");
        System.out.println("Muchos osados eligen el Arco Triple ya que hace mucho daño.");
        pausa();
        System.out.println("");
        System.out.println("¡PERO CUIDADO!");
        pausa();
        System.out.println("");
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        pausa();
        System.out.println("");
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        pausa();
        System.out.println("");
        System.out.println("Sino ve a lo seguro como la mayoría,el Arco Ligero, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");
        pausa();
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
                pausa();
            }
        }
    }

}
