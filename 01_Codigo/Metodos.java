package 01_Codigo;

//---imports---
import java.util.Random;
import java.util.Scanner;

//---Métodos---
public class Metodos 
{
    Scanner sc = new Scanner(System.in);

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
    
    //--Inicio---
    public void Inicio()
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
                    System.exit(0);
                    jugando = false;
                default:
                    System.out.println("Error");
                    jugando = true;
            }
        }
    }

    //---Bigotín---

    public void SaludoInicial()
    {
        System.out.println("Introduce tu nombre futuro heroe: ");
        String nombre = sc.nextLine();
        String guia = "Bigotín";
        System.out.println("?? : ¡Bienvenido " + nombre + "!");
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

    
}
