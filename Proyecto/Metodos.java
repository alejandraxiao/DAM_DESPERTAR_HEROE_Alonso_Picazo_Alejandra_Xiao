package Proyecto;

//---imports---
import java.util.Random;
import java.util.Scanner;

import Clases.ClasePersonaje;       //personaje base sin herencias
import Mounstruos.*;                //Todos los mounstruos
import Armas.*;                     //Todas las armas
import Objetos.*;                   //Todos los objetos
import Clases.ClaseMoustruo;        //Clase mounstruo

//---Métodos---
public class Metodos 
{
    Scanner sc = new Scanner(System.in);
    Random r = new Random();
    String guia = "Bigotín";

    //---Imports de clases de Herencias---

    //-----------------------------------PROTAGONISTA-------------------------------------------
    ClasePersonaje CP = new ClasePersonaje(null, null, null, 100, 15, 100, 0);

    //-------------------------------------ARMAS------------------------------------------------
    //---Arcos---
    ArcoSimple AS = new ArcoSimple(null, 1, 15, null);
    ArcoTriple AT = new ArcoTriple(null, 1, 17, null);
    //---Espada--
    EspadaLigera EL = new EspadaLigera(null, 1, 17, null);
    EspadaPesada EP = new EspadaPesada(null, 1, 20, null);
    //---Poder---
    PoderLuz PL = new PoderLuz(null, 1, 16, null);
    PoderOscuridad PO = new PoderOscuridad(null, 1, 19, null);

    //-----------------------------------MOUNSTRUOS-----------------------------------------------
    Nivel1 N1 = new Nivel1("Hada corrupta", 50, 1, 10, 0);
    Nivel2 N2 = new Nivel2("Espectro de Maná", 100, 2, 15, 0);
    Nivel3 N3 = new Nivel3("Guardian de la Torre", 120, 3, 25, 0);

    //-----------------------------------OBJETOS--------------------------------------------------
    //---Maldiciones---
    MaldicionAlmas MA = new MaldicionAlmas("Maldición de las Almas", 1, 0, null);
    MaldicionMuertos MM = new MaldicionMuertos("Maldición de los Muertos", 1, 0, null);
    MaldicionTorre MR = new MaldicionTorre("Maldición de la Torre", 1, 0, 0, null);

    //---Pociones---
    PocionCuracion PC = new PocionCuracion("Poción de Curación", 1, 0, null);


    //---------------------------------------------------------------------------------------------------------------------------

    //----------------------------------------PARTES DEL JUEGO-------------------------------------------------------------------
    //---Juego---

    public void Juego()
    {
       //---Partida nueva: todo vuelve a empezar---
        CP.setVida(100);
        N1 = new Nivel1("Hada corrupta", 50, 1, 20, 0);
        N2 = new Nivel2("Espectro de maná", 100, 2, 30, 0);
        N3 = new Nivel3("Guardián de la Torre", 150, 3, 35, 0);

        //---Nuestro inventario al inicio---
        String[] inventario = new String[10];
        inventario [0] = "Poción de curación";
        inventario [1] = "Poción de curación";
        inventario [2] = "Poción de curación";

        //---Abandono de la partida---
        boolean abandonado = false;

        //---Inicio con Bigotín---
        EleccionPersonaje(CP.getNombre(), guia);
        TorreExplicacion(guia);
        Ataque(guia);

        //---INICIO DEL JUEGO---
        System.out.println("Nivel 1");
        if (!Combate(N1))
        {
            return;
        }
        ElegirPasillo();
        System.out.println("Nivel 2");
        if (!Combate(N2))
        {
            return;
        }
        ElegirPasillo();
        System.out.println("Nivel 3");
        if (!Combate(N3))
        {
            return;
        }
        PartidaGanada(guia, CP.getNombre());
    }


    //---Introducción de la historia---
    public void Historia()
    {
        System.out.println
        ("En el reino de Kiadur, la magia fluye por cada rincón. El maná recorre la tierra, los ríos y el aire, y cada persona nace con la capacidad de absorber una cantidad distinta. Pero no todo lo que habita este mundo es bondadoso: espíritus errantes y hadas malignas acechan en los bosques y las ruinas, alimentándose del maná de quienes se atreven a cruzar su camino.\n" + //
         "\n" + 
         "Hoy es el día más importante para los jóvenes de Kiadur: el Despertar. Al alcanzar la edad adecuada, cada joven se presenta ante el altar del maná para descubrir cuánto poder puede absorber y elegir el inicio de su camino. Algunos se convertirán en magos, otros en guerreros, escuderos o curanderos. Esta elección sellará su destino para siempre.\n" + //
         "\n" + 
         "Tú eres uno de esos jóvenes. Tras tu Despertar, tendrás que demostrar tu valía enfrentándote a tres criaturas que amenazan el reino, cada una más poderosa que la anterior. Solo quien sobreviva a los tres encuentros será recordado como un verdadero héroe.");
    }

    //--Menú de inicio---
    public boolean Inicio()
    {
        boolean jugando = true;

        while(jugando)
        {
            System.out.println("   Bienvenido a tu zona personal  ");
            System.out.println("            (1) Jugar             ");
            System.out.println("            (2) Ranking           ");
            System.out.println("            (3) Salir             ");

            System.out.println("Elija que acción quiere hacer: ");
            int eleccion = sc.nextInt();

            switch (eleccion)
            {
                case 1:
                    Juego();
                    jugando = true;
                    break;
                case 2:
                    Ranking();
                    jugando = true;
                    break;
                case 3:
                    jugando = false;
                    break;
                default:
                    System.out.println("Error");
                    jugando = true;
            }
        }
    }

    //---Creación del panel informativo durante el videojuego---
    public void PanelInfo()
    {
        boolean respuesta = false;

        while (respuesta)
        {
            System.out.println("    Panel de información");
            System.out.println("    (1) Personaje");
            System.out.println("    (2) Mosuntruos");
            System.out.println("    (3) Volver al juego");
    
            System.out.println("Elija su siguiente acción: ");
    
            int eleccion = sc.nextInt();
    
            switch (eleccion) {
                case 1:
                    InfoPersonaje();
                    break;
                case 2:
                    InfoMounstruos();
                    break;
                case 3:
                    respuesta  = true;
                    break;
                default:
                    System.out.println("Elija una de las opciones disponibles");
                    break;
            }

        }
    }
    
    //--------------------------------------------FUNCIONES DEL VIDEOJUEGO----------------------------------------------------------

    public void InfoPersonaje()
    {
        CP.getNombre();
        CP.getVida();
        CP.getDaño();
        InfoScore();
    }

    public void InfoMounstruos()
    {
        System.out.println("nivel 1");
        N1.getNivel();
        N1.getVida();
        N1.getFuerza();
        System.out.println("Nivel 2");
        N2.getNivel();
        N2.getVida();
        N2.getFuerza();
        System.out.println("Nivel 3");
        N3.getNivel();
        N3.getVida();
        N3.getFuerza();
    }

    public String CofreAleatorio()
    {
        String [] objetoGanado = {"Maldición Almas", "Maldición de Muertos" , "Maldición de la Torre", "Poción de curación"};
        int ganado = r.nextInt(objetoGanado.length);
        String objeto  = objetoGanado[ganado];
        System.out.println("Has ganado : " + objeto);
        return objetoGanado[ganado];
    }

    public int PasilloAleatorio()
    {
        int pasillo = r.nextInt(2);
        return pasillo;
    }

    public void ElegirPasillo()
    {
        int pasilloCofre = PasilloAleatorio();   // dónde está el cofre esta vez

        System.out.println("(1) Pasillo A \n" + "\n" + "(2) Pasillo B");
        int eleccion = sc.nextInt();
        int pasilloElegido = eleccion - 1;

        if (pasilloElegido == pasilloCofre)
        {
            System.out.println("¡Has encontrado un cofre!");
            CofreAleatorio();
        }
        else
        {
            System.out.println("No hay cofre en este pasillo. Pasas al siguiente nivel.");
        }
    }

  //---ranking---

    public int InfoScore()
    {
        int scorePartida = 0;
        int scoreVida = 0;
        int scoreAbandono = -10;

        scoreVida = CP.getVida();
        if (CP.getVida() >= 0 & N1.getVida() == 0 & N2.getVida() == 0 & N3.getVida() == 0)
        {
            scorePartida = 1000;
        }

        if (CP.getVida() >= 0 & N1.getVida() == 0 & N2.getVida() == 0)
        {
            scorePartida = 450;
        }

        if (CP.getVida() >= 0 & N1.getVida() == 0)
        {
            scorePartida = 100;
        }

        if (CP.getVida() == 0 & N1.getVida() > 0)
        {
            scorePartida = 0;
        }


        int scoreTotal = (scorePartida + scoreVida) - scoreAbandono;
        return scoreTotal;
    }

    public void PartidaPerdida(String guia,String nombre)
    {
        System.out.println(guia + " : MIAUUUU! Menudo golpe ese, aunque hayas perdido la primera prueba no te pongas triste, la puedes volver a hacer.");
        System.out.println(guia + " : " + nombre + " te dejo tu score de esta partida.");
        InfoScore();
        System.out.println(guia + " : Seguiremos en contacto, por ahora descansa un poco, que ese último golpe debió doler.");
    }

    public void PartidaGanada (String guia, String nombre)
    {
        System.out.println(guia + " : MIAUUU! Ese golpe habla de quien manda aquí!!");
        System.out.println(guia + " : Felicidades por pasar la primera torre de tu vida, más en el fututo conocerás muchas más torres y convertirte en un gran heroe reconocido de nuestro reino.");
        System.out.println(guia + " : Te dejo tu score por aquí:");
        System.out.println("");
        InfoScore();
    }

    public void PartidaAbandonada(String guia, String nombre)
    {
        System.out.println(guia + " : MIAUUUUU!! Que susto ese, no te preocupes, es tu primera prueba y es normal que no la finalices.");
        System.out.println(guia + " : Sigues teniendo tu score te la dejo por aquí para que la tengas:");
        System.out.println("");
        InfoScore();
    }
    

    //-------------------------------------------PARTE DE COMBATES------------------------------------------------------------------

    public void VisualizaciónAtaque()
    {
        System.out.println("(1) Atacar \n" + "\n" + "(2) Usar cura \n" + "\n" + "(3) Salir de la torre");

        int respuestaAtaque = sc.nextInt();
        boolean respuesta = true;
        while (respuesta)
        {

            switch (respuestaAtaque)
            {
                case 1:
                    int maximo = 21;
                    int minimo = 10;
                    int numeroDaño = r.nextInt(minimo,maximo);

                    M.setVida(M.getVida() - numeroDaño);
                    respuesta = false;
                    break;
    
                case 2:
                    ConsumirCura();
                    respuesta = true;
                    break;
    
                case 3:
                    PartidaAbandonada(null, null);
                    respuesta = false;
                    break;
            }
        }
    }

    public void ConsumirCura()
    {
        String [] inventario = new String[3];

        inventario [0] = "pocion de curacion";
        inventario [1] = "pocion de curacion";
        inventario [2] = "pocion de curacion";

        boolean curado = false;

        for (int i = inventario.length - 1; i >= 0; i--)
        {
            if (inventario[i] != null)
            {
                inventario[i] = null;               
                CP.setVida(Math.min(CP.getVida() + 25, 100));
                curado = true;
                break;                             
            }
        }

        if (!curado)
        {
            System.out.println("No te quedan pociones");
        }
    }

    //-------------------------------------------BIGOTÍN--------------------------------------------------------------------------

    //---Elección del personaje---
    public void EleccionPersonaje(String nombre, String guia)
    {
        SaludoInicial();

        System.out.println("Escoja su personaje: \n" +
        "\n" + "1)Mago \n" + "\n" + "2) Caballero \n" + "\n" + "3) Arquero \n");

        int eleccionPersonaje = sc.nextInt();

        switch (eleccionPersonaje) {
            case 1:
                ArmaMago();
                break;
            case 2:
                ArmaCaballero();
                break;
            case 3:
                ArmaArquero();
                break;
            default:
                System.out.println(guia + " : " + nombre + "debes elegir un personaje para poder continuar con tu destino");
                break;
        }
    }

    //---Diálogos del Bigotín---
    public void SaludoInicial()
    {
        System.out.println("Introduce tu nombre futuro heroe: ");
        String nombre = sc.nextLine();
        CP.setNombre(nombre);
        String guia = "Bigotín";
        System.out.println("?? : ¡Bienvenido " + CP.getNombre() + "!");
        System.out.println("");
        System.out.println("?? : ¡Soy Bigotín! Tu compañero durante tus siguientes años escolares.");
        System.out.println(" ");
        System.out.println(guia + " : Se que estás muy nervioso y para eso estoy aquí, yo te ayudaré a que no te arrepientas de tus decisiones.");
        System.out.println("");
        System.out.println(guia + " : Esto es muy importante ya que no podrás cambiar tu decisión y quedará de por vida, así que ten en cuenta las siguientes cosas.");
        System.out.println("");
        System.out.println("""
          *
         /^\\
        /_*_\\
        (o.o)           (1) Este es el mago, ¡y tendrás una fuerza extraordinaria! Eres un crack con el maná y una fuerza sin límites, pero e cansarás bastante rápido, al final dependes de tu maná.
        /| |\\
        """);
        System.out.println("----------------------------------------");
        System.out.println("""
            |
          O    |
         [#]===+-       (2) El Caballero! Un clásico, si te gusta moverte es lo tuyo. Tiene mucha agilidad y resistencia. Su única debilidad es que su fuerza es en base a tu condición física.
         / \\
        """);
        System.out.println("-----------------------------------------");
        System.out.println("""
          O   )
         /|\\--->       (4) El arquero hace que tengas muchas posibilidades, ni te cansas tanto ni gastas tanto maná, eso sí, tu fuerza si que es menor que al del caballero.
         / \\  )
        """);
    }

    public void TorreExplicacion(String guia)
    {
        boolean respuesta = false;

        while (respuesta)
        {
            System.out.println(guia + " : " + "Que emoción " + CP.getNombre() + " ya tenemos nuestro destino elegido, y como primera prueba para ver tu nivel, los profesores han preparado la 'prueba de la torre'.");
            System.out.println(guia + " : No te preocupes de nada, generalmente la mayoría de estudiantes la pasan sin problema y al ser una prueba de inicio lo único que estás haciendo es poner tu consciencia dentro de la torre.");
            System.out.println(guia + " : Acuerdate que esto es solo una pequeña prueba, los profesores y yo estaremos aquí para cuelquier duda o ayuda que necesites.");
            System.out.println(guia + " : Las normas sin fáciles, si tu vida llega a 0 los profesores te sacarán inmediatamente de la torre, si ganas tu solo pasaste la prueba y podrás salir sin ningún problema.");
            System.out.println(guia + " : En caso de que tu conscienda se salga sola sin querer se te guardará tu score o incluso si te ves atrapado puedes obtar por abandonar la prueba, eso si, decidelo bien porque si abandonas se te restará 10 puntos de tu nota.");
            System.out.println(guia + " : Lo entendiste??");
            System.out.println(CP.getNombre() + " : (1) Todo entendido!  (2) La verdad es que es mucha información");

            int respuestaNuestra = sc.nextInt();
            
            if (respuestaNuestra == 1)
            {
                respuesta = true;
            }
            else
            {
                respuesta = false;
            }
        }
    }

    public void Ataque(String guia)
    {
        System.out.println(guia + " : Ya estamos dentro de la torre!!!");
        System.out.println(guia + " : Este es un pequeñó recordatorio, puedes atacar, salir de la prueba o pedir información, se te abrirá un pequeño panel con la información que necesites.");
        System.out.println(guia + " : Pues buena suertee, MIAUUUU!!");
    }

    public void Emergencia(String guia)
    {
        System.out.println(guia + " : MIAUUUUU!!! Ten cuidado!!!");
        System.out.println(guia + " : Tu vida está en números críticos, puede ser que no soportes el siguiente golpe.");
        System.out.println(guia + " : Opta en este turno en tomar una poción de curación desde tu inventario, llamar a los profesores o seguir hasta el final.");
        System.out.println("(1) Beber poción \n" + "\n" + "(2) Abandonar prueba \n" + "\n" + "(3) Seguir partida");

        int respuestaEmergencia = sc.nextInt();

        switch (respuestaEmergencia) 
        {
            case 1:
                ConsumirCura();
                break;
            case 2:
                PartidaAbandonada(null, null);
                break;
            default:
                break;
        }
        

    }


    //---Explicación de  tipo de armas---
    public void ArmaMago()
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Mago! Seguro serás el mejor.");
        System.out.println("Es importante que sepas que los magos son muy extraños a veces, te acostumbrarás.");
        System.out.println("Como sabrás, hace más de mil años se han diferenciado por dos grupos: 'Magos Ocuros' y 'Magos de luz'");
        System.out.println("Ahora es tu turno de elegir que tipo de mágia quieres aprender.");
        System.out.println("¡PERO CUIDADO!");
        System.out.println("Los magos de de luz aunque tengan menos fuerza tendrás más ataques al gastar menos maná y más resistencias.");
        System.out.println("Los magos oscuros con su fuerza bruta gastan más maná y se cansarán antes");

        System.out.println("(1) Luz");
        System.out.println("(2) Oscuridad");

        int eleccionArma = sc.nextInt();
        CP.setClase("Mago");

        if (eleccionArma == 1)
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

    public void ArmaCaballero()
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Caballero! Seguro serás el mejor.");
        System.out.println("Los caballeros siempre tienen a su mejor amiga ¡LA ESPADA!");
        System.out.println("Y como buen caballero es tu turno de elegir a tu compañera de vida");
        System.out.println("Muchos fortachones eligen la Espada Pesada ya que hace mucho daño.");
        System.out.println("¡PERO CUIDADO!");
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        System.out.println("Sino ve a lo seguro como la mayoría, la Espada Ligera, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");

        System.out.println("(1) Espada pesada");
        System.out.println("(2) Espada ligera");

        int eleccionArma = sc.nextInt();
        CP.setClase("Caballero");

        if (eleccionArma == 1)
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

    public void ArmaArquero()
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Arquero! Seguro tendrás una puntería increible.");
        System.out.println("Los arqueros siempre tienen a su mejor amigo ¡EL ARCO!");
        System.out.println("Y como buen arquero es tu turno de elegir a tu compañero de vida");
        System.out.println("Muchos osados eligen el Arco Triple ya que hace mucho daño.");
        System.out.println("¡PERO CUIDADO!");
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        System.out.println("Sino ve a lo seguro como la mayoría,el Arco Ligero, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");

        System.out.println("(1) Arco Triple");
        System.out.println("(2) Arco Ligero");

        int eleccionArma = sc.nextInt();
        CP.setClase("Arquero");
        if (eleccionArma == 1)
        {
            CP.setArma(AT.getNombre());
            CP.setDaño(AT.getDaño());
        }
    }

}
