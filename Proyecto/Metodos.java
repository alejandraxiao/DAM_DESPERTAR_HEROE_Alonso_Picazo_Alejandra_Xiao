package Proyecto;

//---imports---
import java.util.Random;
import java.util.Scanner;

import Clases.ClasePersonaje;       //personaje base sin herencias
import Mounstruos.*;                //Todos los mounstruos
import Armas.*;                     //Todas las armas
import Objetos.*;                   //Todos los objetos

//---Métodos---
public class Metodos 
{
    Scanner sc = new Scanner(System.in);
    Random r = new Random();

    //---Imports de clases de Herencias---

    //-----------------------------------PROTAGONISTA-------------------------------------------
    ClasePersonaje CP = new ClasePersonaje(null, null, null, 0, 0, 0, 0);

    //-------------------------------------ARMAS------------------------------------------------
    //---Arcos---
    ArcoSimple AS = new ArcoSimple(null, 0, 0, null);
    ArcoTriple AT = new ArcoTriple(null, 0, 0, null);
    //---Espada--
    EspadaLigera EL = new EspadaLigera(null, 0, 0, null);
    EspadaPesada EP = new EspadaPesada(null, 0, 0, null);
    //---Fé---
    Fe fe = new Fe(null, 0, 0, null);
    Sacrificios S = new Sacrificios(null, 0, 0, null);
    //---Poder---
    PoderLuz PL = new PoderLuz(null, 0, 0, null);
    PoderOscuridad PO = new PoderOscuridad(null, 0, 0, null);

    //-----------------------------------MOUNSTRUOS-----------------------------------------------
    Nivel1 N1 = new Nivel1(null, 0, 0, 0, 0);
    Nivel2 N2 = new Nivel2(null, 0, 0, 0, 0);
    Nivel3 N3 = new Nivel3(null, 0, 0, 0, 0);

    //-----------------------------------OBJETOS--------------------------------------------------
    //---Maldiciones---
    MaldicionAlmas MA = new MaldicionAlmas(null, 0, 0, null);
    MaldicionMuertos MM = new MaldicionMuertos(null, 0, 0, null);
    MaldicionOscuridad MO = new MaldicionOscuridad(null, 0, 0, null);
    MaldicionTorre MR = new MaldicionTorre(null, 0, 0, 0, null);

    //---Pociones---
    PocionCuracion PC = new PocionCuracion(null, 0, 0, null);
    PocionFe PF = new PocionFe(null, 0, 0, null);
    PocionResistencia PR = new PocionResistencia(null, 0, 0, null); 


    //---------------------------------------------------------------------------------------------------------------------------

    //----------------------------------------PARTES DEL JUEGO-------------------------------------------------------------------
    //---Juego---
    public void FinalJuego()
    {
        boolean juego = true;
    
        while (juego)
        {
            Historia();
            do 
            {
                Inicio();
            } 
            while ();
        }
    }

    public void Juego()
    {
        //Inicio del juego con Bigotín, es la EXPLICACIÓN DE TODO EL JUEGO
        SaludoInicial();
        EleccionPersonaje(null, null);
        TorreExplicacion(null, null);
        Ataque(null, null);
        Emergencia(null, null);

        //PARTE DEL JUEGO CON ATAQUES

        System.out.println("Nivel 1");
        boolean juego = true;
        boolean mounstruo = false;
        boolean personaje = false;
        
        while (juego)
        {
            if (personaje == true)
            {
                while(mounstruo)
                {

                }
            }
        }
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
                    return true;
                    break;
                case 2:
                    Ranking();
                    jugando = true;
                    return true;
                    break;
                case 3:
                    System.exit(0);
                    jugando = false;
                    return false;
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

    public void InfoPersonaje(int eleccionPersonaje)
    {
        CP.InfoPersonaje();
        InfoScore();
    }

    public void InfoMounstruos()
    {
    }

  //---ranking---

    public int InfoScore()
    {
        int scorePartida = 0;
        int scoreVida = 0;
        int scoreAbandono = -10;

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
                    int maximo = 20;
                    int minimo = 10;
                    int numeroDaño = r.nextInt(minimo,maximo);
    
                    respuesta = false;
                    break;
    
                case 2:
                    ConsumirCura();
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
            CP.setVida(CP.getVida() + 25);
            curado = true;
            break;                             
        }
    }

if (!curado)
{
    System.out.println("No te quedan pociones");
}


    //-------------------------------------------BIGOTÍN--------------------------------------------------------------------------

    //---Elección del personaje---
    public void EleccionPersonaje(String nombre, String guia)
    {
        SaludoInicial();

        System.out.println("Escoja su personaje: \n" +
        "\n" + "1)Mago \n" + "\n" + "2) Caballero \n" + "\n" + "3) Sacerdote \n" + "\n" + "4) Arquero");

        int eleccionPersonaje = sc.nextInt();

        switch (eleccionPersonaje) {
            case 1:
                ArmaMago();
                break;
            case 2:
                ArmaCaballero();
                break;
            case 3:
                ArmaSacerdote();
                break;
            case 4:
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
        System.out.println("----------------------------------------");
        System.out.println("""
          ___      |
         (o_o)    -+-   (3) El sacerdote! No todos lo valen, mucha gente cree más en la mágia que en la fe, pero sigue habiendo gente  que cree y nace con un don "La divinidad"
         /| |\\    |        Contra más divinidad tengas mayor fuerza tendrás y lo mejor de todo! Tu divinidad dependerá de tu fe así que no baja mucho normalmente, solo que tu resistencia es algo más baja
        /_____\\   |
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
    }

    public void ArmaSacerdote()
    {
        System.out.println("MIAUUUUU! Asi que tenías escondido ese don, veremos que tan creyente eres.");
        System.out.println("No lo digo por nada pero muchos sacerdotes cayeron en la codícia y refinaron un método bastante curioso.");
        System.out.println("El SACRIFICIO");
        System.out.println("Este se basa en cuando matas a alguien se convierte en un sacrificio y te dará más poder divino.");
        System.out.println("Ya que los que teneis este don no teneis maná y actua algo diferente la divinidad, esta no se debilita pero si no consigues divinidad tu fuerza disminuirá.");
        System.out.println("El sacrficio hace que tengas menos reisitencia ya que no es una divinidad pura, pero será fácil de conseguir en las expediciones.");
        System.out.println("La fe dependerá de lo que tu creas, esta solo puede mejorarse o rezando o con unas pociones antiguas que otorgan divinidad pura.");
        System.out.println("Que curiosidad!! ¿Cual será tu elección?.");

        System.out.println("(1) Fe");
        System.out.println("(2) Sacrificios");
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
    }

}
