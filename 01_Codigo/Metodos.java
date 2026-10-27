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

    
}
