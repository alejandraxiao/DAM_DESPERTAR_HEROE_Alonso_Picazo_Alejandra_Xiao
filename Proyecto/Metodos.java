package Proyecto;

//---imports---
import java.util.Scanner;

import Clases.ClasePersonaje;       //personaje base sin herencias
import Mounstruos.*;                //Todos los mounstruos
import Armas.*;                     //Todas las armas
import Objetos.*;                   //Todos los objetos
import Clases.ClaseMoustruo;        //Clase mounstruo
import Clases.ClaseRanking;         //Ranking de partidas

//---Métodos---
public class Metodos 
{
    //---Teclado compartido por todos los métodos (no guarda datos de la partida)---
    static Scanner sc = new Scanner(System.in);

    //---Constante con el nombre del guía---
    static final String GUIA = "Bigotín";

    //---Ya no hay variables globales de la partida: el personaje, los mounstruos,
    //---el inventario y el ranking se crean dentro de los métodos y se pasan por parámetro---

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

    public static void Juego(ClaseRanking ranking)
    {
        //---Partida nueva: todo vuelve a empezar (se crean aquí y se pasan a los demás métodos)---
        ClasePersonaje CP = new ClasePersonaje(null, null, null, 100, 15, 100, 0);
        Nivel1 N1 = new Nivel1("Hada corrupta", 50, 1, 10, 0);
        Nivel2 N2 = new Nivel2("Espectro de maná", 100, 2, 15, 0);
        Nivel3 N3 = new Nivel3("Guardián de la Torre", 150, 3, 18, 0);

        //---Nuestro inventario al inicio: array fijo y contador de pociones---
        String [] inventario = new String[3];
        inventario [0] = "Poción de curación";
        inventario [1] = "Poción de curación";
        inventario [2] = "Poción de curación";
        int pociones = 3;       //posiciones ocupadas: siempre están al principio del array (0 .. pociones-1)

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
        if (FinPartida(CP, N1, N1, N2, N3, ranking, guia))
        {
            return;
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
        if (FinPartida(CP, N2, N1, N2, N3, ranking, guia))
        {
            return;
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
        if (FinPartida(CP, N3, N1, N2, N3, ranking, guia))
        {
            return;
        }

        //---Si llega aquí ha ganado los tres combates---
        int score = InfoScore(CP, N1, N2, N3, false);
        PartidaGanada(guia, CP.getNombre(), score);
        GuardarPartida(ranking, CP.getNombre(), score);
    }

    //---Mira cómo ha terminado el combate: devuelve true si la partida se acaba (perdida o abandonada)---
    public static boolean FinPartida(ClasePersonaje CP, ClaseMoustruo M, Nivel1 N1, Nivel2 N2, Nivel3 N3, ClaseRanking ranking, String guia)
    {
        if (CP.getVida() <= 0)
        {
            int score = InfoScore(CP, N1, N2, N3, false);
            PartidaPerdida(guia, CP.getNombre(), score);
            GuardarPartida(ranking, CP.getNombre(), score);
            return true;
        }

        //---El héroe sigue vivo y el mounstruo también: solo puede ser que haya abandonado---
        if (M.getVida() > 0)
        {
            int score = InfoScore(CP, N1, N2, N3, true);
            PartidaAbandonada(guia, CP.getNombre(), score);
            GuardarPartida(ranking, CP.getNombre(), score);
            return true;
        }

        return false;
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
        //---El ranking se carga una vez al arrancar y se pasa a los métodos que lo usan---
        ClaseRanking ranking = new ClaseRanking();

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
            System.out.println("            (1) Jugar             ");
            System.out.println("            (2) Ranking           ");
            System.out.println("            (3) Salir             ");

            System.out.println("Elija que acción quiere hacer: ");
            eleccion = sc.nextLine();

            switch (eleccion)
            {
                case "1":
                    Juego(ranking);
                    break;
                case "2":
                    Ranking(ranking);
                    break;
                case "3":
                    System.out.println("Hasta pronto, héroe");
                    break;
                default:
                    System.out.println("Error");
                    Pausa();
                    break;
            }
        }
        while (!eleccion.equals("3"));      //si no se elige Salir, el menú vuelve a salir
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

    public static void Ranking(ClaseRanking ranking)
    {
        System.out.println("");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("|                                          |");
        System.out.println("|                 RANKING                  |");
        System.out.println("|                                          |");
        System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
        System.out.println("");
        Pausa();
        ranking.Ver();
        Pausa();
    }

    //---Guarda la partida terminada en el ranking---
    public static void GuardarPartida(ClaseRanking ranking, String nombre, int score)
    {
        ranking.Modificar(nombre, score);
    }

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

            if (accion.equals("2"))
            {
                pociones = ConsumirCura(CP, inventario, pociones);      //curarse no gasta el turno
            }
            else if (accion.equals("3"))
            {
                abandonado = true;          //Juego() se encarga de llamar a PartidaAbandonada
            }
            else if (M.getVida() <= 0)
            {
                System.out.println("Has derrotado a " + M.getNombre());
                Pausa();
            }
            else
            {
                //---Turno del mounstruo---
                int dañoMounstruo = (int)(Math.random() * (M.getFuerza() - 5 + 1)) + 5;      //entre 5 y su fuerza, los dos incluidos
                CP.setVida(CP.getVida() - dañoMounstruo);
                System.out.println(M.getNombre() + " te hace " + dañoMounstruo + " de daño. Tu vida: " + CP.getVida());
                Pausa();

                //---Vida baja con el aviso de Bigotín---
                if (CP.getVida() > 0 && CP.getVida() <= 25)
                {
                    String respuestaEmergencia = Emergencia(guia);

                    if (respuestaEmergencia.equals("1"))
                    {
                        pociones = ConsumirCura(CP, inventario, pociones);
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

                    M.setVida(M.getVida() - numeroDaño);
                    System.out.println("Le haces " + numeroDaño + " de daño a " + M.getNombre() + ". Su vida: " + Math.max(M.getVida(), 0));
                    Pausa();
                    return "1";

                case "2":
                    return "2";             //Combate() gasta la poción

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
        if (CP.getVida() >= 100)
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
        CP.setVida(Math.min(CP.getVida() + 25, 100));
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

        System.out.println("(1) Luz");
        System.out.println("(2) Oscuridad");

        String eleccionArma = sc.nextLine();
        CP.setClase("Mago");
        PoderLuz PL = new PoderLuz(null, 1, 16, null);
        PoderOscuridad PO = new PoderOscuridad(null, 1, 19, null);

        if (eleccionArma.equals("1"))
        {
            CP.setArma(PL.getNombre());
            CP.setDaño(PL.getDaño());
        }
        else
        {
            CP.setArma(PO.getNombre());
            CP.setDaño(PO.getDaño());
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

        System.out.println("(1) Espada pesada");
        System.out.println("(2) Espada ligera");

        String eleccionArma = sc.nextLine();
        CP.setClase("Caballero");
        EspadaPesada EP = new EspadaPesada(null, 1, 20, null);
        EspadaLigera EL = new EspadaLigera(null, 1, 17, null);

        if (eleccionArma.equals("1"))
        {
            CP.setArma(EP.getNombre());
            CP.setDaño(EP.getDaño());
        }
        else
        {
            CP.setArma(EL.getNombre());
            CP.setDaño(EL.getDaño());
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

        System.out.println("(1) Arco Triple");
        System.out.println("(2) Arco Ligero");

        String eleccionArma = sc.nextLine();
        CP.setClase("Arquero");
        ArcoTriple AT = new ArcoTriple(null, 1, 17, null);
        ArcoSimple AS = new ArcoSimple(null, 1, 15, null);
        if (eleccionArma.equals("1"))
        {
            CP.setArma(AT.getNombre());
            CP.setDaño(AT.getDaño());
        }
        else
        {
            CP.setArma(AS.getNombre());
            CP.setDaño(AS.getDaño());
        }
    }

}
