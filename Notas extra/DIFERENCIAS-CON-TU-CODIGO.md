# Diferencias entre tu código y el de la carpeta de prueba

Comparo tu `Metodos.java` original (`01_PROYECTO DE PROGRAMACIÓN - LENGUAJES - ENTORNOS`, tal como está el 10/11 a las 12:50) con el que funciona en `01_1PRUEBA PROYECTO ia`. Los números de línea son los de **tu** archivo.

**Haz los cambios en el orden de este archivo (de abajo hacia arriba)**: así los números de las líneas de arriba no se mueven.

Métodos que no cambian: `PasilloAleatorio` y `EleccionPersonaje`.

---

## PARTE 1. Metodos.java

### Paso 1. ArmaArquero (líneas 545 a 566)

**Qué cambia:** Falta el `else` para el Arco Simple: si eliges 2, ahora no se guarda ningún arma. Además, `Pausa();` después de cada frase.

Sustituye las líneas 545 a 566 por:

```java
    public void ArmaArquero()
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Arquero! Seguro tendrás una puntería increible.");
        Pausa();
        System.out.println("Los arqueros siempre tienen a su mejor amigo ¡EL ARCO!");
        Pausa();
        System.out.println("Y como buen arquero es tu turno de elegir a tu compañero de vida");
        Pausa();
        System.out.println("Muchos osados eligen el Arco Triple ya que hace mucho daño.");
        Pausa();
        System.out.println("¡PERO CUIDADO!");
        Pausa();
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        Pausa();
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        Pausa();
        System.out.println("Sino ve a lo seguro como la mayoría,el Arco Ligero, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");
        Pausa();

        System.out.println("(1) Arco Triple");
        System.out.println("(2) Arco Ligero");

        int eleccionArma = sc.nextInt();
        CP.setClase("Arquero");
        if (eleccionArma == 1)
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
```

### Paso 2. ArmaCaballero (líneas 516 a 543)

**Qué cambia:** Solo cambia: `Pausa();` después de cada frase de Bigotín.

Sustituye las líneas 516 a 543 por:

```java
    public void ArmaCaballero()
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Caballero! Seguro serás el mejor.");
        Pausa();
        System.out.println("Los caballeros siempre tienen a su mejor amiga ¡LA ESPADA!");
        Pausa();
        System.out.println("Y como buen caballero es tu turno de elegir a tu compañera de vida");
        Pausa();
        System.out.println("Muchos fortachones eligen la Espada Pesada ya que hace mucho daño.");
        Pausa();
        System.out.println("¡PERO CUIDADO!");
        Pausa();
        System.out.println("Te vas a cansar antes y tu maná volará con cada movimiento que hagas.");
        Pausa();
        System.out.println("Asi que debes de tener cuidado ehhhhhhhhhhh.");
        Pausa();
        System.out.println("Sino ve a lo seguro como la mayoría, la Espada Ligera, menos cansancio y menos uso de maná, eso sí, te tocará atacar más por tener menos fuerza.");
        Pausa();

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
```

### Paso 3. ArmaMago (líneas 488 a 514)

**Qué cambia:** Solo cambia: `Pausa();` después de cada frase de Bigotín.

Sustituye las líneas 488 a 514 por:

```java
    public void ArmaMago()
    {
        System.out.println("MIAUUUUU! Que buena elección nuevo Mago! Seguro serás el mejor.");
        Pausa();
        System.out.println("Es importante que sepas que los magos son muy extraños a veces, te acostumbrarás.");
        Pausa();
        System.out.println("Como sabrás, hace más de mil años se han diferenciado por dos grupos: 'Magos Ocuros' y 'Magos de luz'");
        Pausa();
        System.out.println("Ahora es tu turno de elegir que tipo de mágia quieres aprender.");
        Pausa();
        System.out.println("¡PERO CUIDADO!");
        Pausa();
        System.out.println("Los magos de de luz aunque tengan menos fuerza tendrás más ataques al gastar menos maná y más resistencias.");
        Pausa();
        System.out.println("Los magos oscuros con su fuerza bruta gastan más maná y se cansarán antes");
        Pausa();

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
```

### Paso 4. Emergencia (líneas 462 a 484)

**Qué cambia:** `case 2` pasa de `PartidaAbandonada(null, null);` a `abandonado = true;` (el mensaje de abandono ya lo saca Combate con el nombre del guía y del jugador). Además, `Pausa();` tras cada frase.

Sustituye las líneas 462 a 484 por:

```java
    public void Emergencia(String guia)
    {
        System.out.println(guia + " : MIAUUUUU!!! Ten cuidado!!!");
        Pausa();
        System.out.println(guia + " : Tu vida está en números críticos, puede ser que no soportes el siguiente golpe.");
        Pausa();
        System.out.println(guia + " : Opta en este turno en tomar una poción de curación desde tu inventario, llamar a los profesores o seguir hasta el final.");
        Pausa();
        System.out.println("(1) Beber poción \n" + "\n" + "(2) Abandonar prueba \n" + "\n" + "(3) Seguir partida");

        int respuestaEmergencia = sc.nextInt();

        switch (respuestaEmergencia) 
        {
            case 1:
                ConsumirCura();
                break;
            case 2:
                abandonado = true;
                break;
            default:
                break;
        }
        

    }
```

### Paso 5. Ataque (líneas 455 a 460)

**Qué cambia:** Solo cambia: `Pausa();` después de cada frase.

Sustituye las líneas 455 a 460 por:

```java
    public void Ataque(String guia)
    {
        System.out.println(guia + " : Ya estamos dentro de la torre!!!");
        Pausa();
        System.out.println(guia + " : Este es un pequeñó recordatorio, puedes atacar, salir de la prueba o pedir información, se te abrirá un pequeño panel con la información que necesites.");
        Pausa();
        System.out.println(guia + " : Pues buena suertee, MIAUUUU!!");
        Pausa();
    }
```

### Paso 6. TorreExplicacion (líneas 428 a 453)

**Qué cambia:** Solo cambia: `Pausa();` después de cada frase (no en la pregunta final).

Sustituye las líneas 428 a 453 por:

```java
    public void TorreExplicacion(String guia)
    {
        boolean respuesta = false;

        while (!respuesta)
        {
            System.out.println(guia + " : " + "Que emoción " + CP.getNombre() + " ya tenemos nuestro destino elegido, y como primera prueba para ver tu nivel, los profesores han preparado la 'prueba de la torre'.");
            Pausa();
            System.out.println(guia + " : No te preocupes de nada, generalmente la mayoría de estudiantes la pasan sin problema y al ser una prueba de inicio lo único que estás haciendo es poner tu consciencia dentro de la torre.");
            Pausa();
            System.out.println(guia + " : Acuerdate que esto es solo una pequeña prueba, los profesores y yo estaremos aquí para cuelquier duda o ayuda que necesites.");
            Pausa();
            System.out.println(guia + " : Las normas sin fáciles, si tu vida llega a 0 los profesores te sacarán inmediatamente de la torre, si ganas tu solo pasaste la prueba y podrás salir sin ningún problema.");
            Pausa();
            System.out.println(guia + " : En caso de que tu conscienda se salga sola sin querer se te guardará tu score o incluso si te ves atrapado puedes obtar por abandonar la prueba, eso si, decidelo bien porque si abandonas se te restará 10 puntos de tu nota.");
            Pausa();
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
```

### Paso 7. SaludoInicial (líneas 392 a 426)

**Qué cambia:** Solo cambia: `Pausa();` después de cada frase y de cada dibujo (no después de pedir el nombre).

Sustituye las líneas 392 a 426 por:

```java
    public void SaludoInicial()
    {
        System.out.println("Introduce tu nombre futuro heroe: ");
        String nombre = sc.nextLine();
        CP.setNombre(nombre);
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
        System.out.println("""
          *
         /^\\
        /_*_\\
        (o.o)           (1) Este es el mago, ¡y tendrás una fuerza extraordinaria! Eres un crack con el maná y una fuerza sin límites, pero e cansarás bastante rápido, al final dependes de tu maná.
        /| |\\
        """);
        Pausa();
        System.out.println("----------------------------------------");
        System.out.println("""
            |
          O    |
         [#]===+-       (2) El Caballero! Un clásico, si te gusta moverte es lo tuyo. Tiene mucha agilidad y resistencia. Su única debilidad es que su fuerza es en base a tu condición física.
         / \\
        """);
        Pausa();
        System.out.println("-----------------------------------------");
        System.out.println("""
          O   )
         /|\\--->       (3) El arquero hace que tengas muchas posibilidades, ni te cansas tanto ni gastas tanto maná, eso sí, tu fuerza si que es menor que al del caballero.
         / \\  )
        """);
        Pausa();
    }
```

### Paso 8. ConsumirCura (líneas 336 a 361)

**Qué cambia:** Se borra el array local de 3 pociones (con él las pociones eran infinitas, porque se volvía a crear cada vez). Ahora usa el `inventario` del atributo. Además imprime tu vida al curarte.

Sustituye las líneas 336 a 361 por:

```java
    public void ConsumirCura()
    {
        boolean curado = false;

        for (int i = inventario.length - 1; i >= 0; i--)
        {
            if (inventario[i] != null)
            {
                inventario[i] = null;               
                CP.setVida(Math.min(CP.getVida() + 25, 100));
                System.out.println("Te has curado. Tu vida: " + CP.getVida());
                Pausa();
                curado = true;
                break;                             
            }
        }

        if (!curado)
        {
            System.out.println("No te quedan pociones");
            Pausa();
        }
    }
```

### Paso 9. Combate (nuevo) + VisualizaciónAtaque (líneas 303 a 334)

**Qué cambia:** Recibe el monstruo `(ClaseMoustruo M)` (por eso daba error `M`). El menú y el `nextInt()` van dentro del `while` (si no, la opción 2 se repetía para siempre). Opción 3 marca `abandonado = true`. Nueva opción 4 "Información" que abre `PanelInfo()` sin gastar turno. Imprime el daño que haces.

`Combate()` es nuevo: hace los turnos (tú atacas, el monstruo te pega un daño aleatorio entre 5 y su fuerza, avisa de Emergencia con 25 o menos de vida) y devuelve `true` si ganas o `false` si pierdes o abandonas. Juego() ya lo usa.

Sustituye las líneas 303 a 334 (todo el método `VisualizaciónAtaque`) por estos dos métodos:

```java
    public boolean Combate(ClaseMoustruo M)
    {
        System.out.println("Aparece " + M.getNombre() + " con " + M.getVida() + " de vida");
        Pausa();

        while (CP.getVida() > 0 && M.getVida() > 0)
        {
            //---Turno del personaje---
            VisualizaciónAtaque(M);

            if (abandonado)
            {
                PartidaAbandonada(guia, CP.getNombre());
                return false;
            }

            if (M.getVida() <= 0)
            {
                System.out.println("Has derrotado a " + M.getNombre());
                Pausa();
                return true;
            }

            //---Turno del mounstruo---
            int dañoMounstruo = r.nextInt(5, M.getFuerza() + 1);       //daño aleatorio entre 5 y su fuerza
            CP.setVida(CP.getVida() - dañoMounstruo);
            System.out.println(M.getNombre() + " te hace " + dañoMounstruo + " de daño. Tu vida: " + CP.getVida());
            Pausa();

            if (CP.getVida() <= 0)
            {
                PartidaPerdida(guia, CP.getNombre());
                return false;
            }

            //---Vida baja: aviso de Bigotín---
            if (CP.getVida() <= 25)
            {
                Emergencia(guia);

                if (abandonado)
                {
                    PartidaAbandonada(guia, CP.getNombre());
                    return false;
                }
            }
        }
        return CP.getVida() > 0;
    }

    public void VisualizaciónAtaque(ClaseMoustruo M)
    {
        boolean respuesta = true;
        while (respuesta)
        {
            System.out.println("(1) Atacar \n" + "\n" + "(2) Usar cura \n" + "\n" + "(3) Salir de la torre \n" + "\n" + "(4) Información");

            int respuestaAtaque = sc.nextInt();

            switch (respuestaAtaque)
            {
                case 1:
                    int maximo = 21;
                    int minimo = 10;
                    int numeroDaño = r.nextInt(minimo,maximo);

                    M.setVida(M.getVida() - numeroDaño);
                    System.out.println("Haces " + numeroDaño + " de daño. Vida de " + M.getNombre() + ": " + M.getVida());
                    Pausa();
                    respuesta = false;
                    break;
    
                case 2:
                    ConsumirCura();
                    respuesta = true;
                    break;
    
                case 3:
                    abandonado = true;
                    respuesta = false;
                    break;

                case 4:
                    PanelInfo();
                    respuesta = true;           //consultar información NO gasta el turno
                    break;
            }
        }
    }
```

### Paso 10. PartidaAbandonada (líneas 292 a 298)

**Qué cambia:** El score se imprime con `System.out.println("Score : " + InfoScore());` (antes se calculaba pero no se veía). Además, `Pausa();`.

Sustituye las líneas 292 a 298 por:

```java
    public void PartidaAbandonada(String guia, String nombre)
    {
        System.out.println(guia + " : MIAUUUUU!! Que susto ese, no te preocupes, es tu primera prueba y es normal que no la finalices.");
        Pausa();
        System.out.println(guia + " : Sigues teniendo tu score te la dejo por aquí para que la tengas:");
        Pausa();
        System.out.println("");
        System.out.println("Score : " + InfoScore());
        Pausa();
    }
```

### Paso 11. PartidaGanada (líneas 283 a 290)

**Qué cambia:** Igual que PartidaAbandonada: imprimir el score y `Pausa();`.

Sustituye las líneas 283 a 290 por:

```java
    public void PartidaGanada (String guia, String nombre)
    {
        System.out.println(guia + " : MIAUUU! Ese golpe habla de quien manda aquí!!");
        Pausa();
        System.out.println(guia + " : Felicidades por pasar la primera torre de tu vida, más en el fututo conocerás muchas más torres y convertirte en un gran heroe reconocido de nuestro reino.");
        Pausa();
        System.out.println(guia + " : Te dejo tu score por aquí:");
        Pausa();
        System.out.println("");
        System.out.println("Score : " + InfoScore());
        Pausa();
    }
```

### Paso 12. PartidaPerdida (líneas 275 a 281)

**Qué cambia:** Igual que PartidaAbandonada: imprimir el score y `Pausa();`.

Sustituye las líneas 275 a 281 por:

```java
    public void PartidaPerdida(String guia,String nombre)
    {
        System.out.println(guia + " : MIAUUUU! Menudo golpe ese, aunque hayas perdido la primera prueba no te pongas triste, la puedes volver a hacer.");
        Pausa();
        System.out.println(guia + " : " + nombre + " te dejo tu score de esta partida.");
        Pausa();
        System.out.println("Score : " + InfoScore());
        Pausa();
        System.out.println(guia + " : Seguiremos en contacto, por ahora descansa un poco, que ese último golpe debió doler.");
        Pausa();
    }
```

### Paso 13. InfoScore (líneas 243 a 273) y Ranking nuevo

**Qué cambia:** Los `if` pasan a `else if` (antes el último pisaba a los otros y siempre daba 100). `&` pasa a `&&`. `== 0` pasa a `<= 0` (la vida del monstruo puede quedar negativa). `scoreAbandono` empieza en 0 y solo vale 10 si has abandonado (antes restabas -10, que es sumar 10, y siempre).

`Ranking()` no existía y Inicio() lo llama: por eso no compilaba. De momento solo muestra el score.

Sustituye las líneas 243 a 273 por:

```java
    public void Ranking()
    {
        System.out.println("Score : " + InfoScore());
    }

    public int InfoScore()
    {
        int scorePartida = 0;
        int scoreVida = 0;
        int scoreAbandono = 0;

        scoreVida = CP.getVida();
        if (CP.getVida() > 0 && N1.getVida() <= 0 && N2.getVida() <= 0 && N3.getVida() <= 0)
        {
            scorePartida = 1000;
        }
        else if (N1.getVida() <= 0 && N2.getVida() <= 0)
        {
            scorePartida = 450;
        }
        else if (N1.getVida() <= 0)
        {
            scorePartida = 100;
        }

        if (abandonado)
        {
            scoreAbandono = 10;
        }

        int scoreTotal = (scorePartida + scoreVida) - scoreAbandono;
        return scoreTotal;
    }
```

### Paso 14. ElegirPasillo (líneas 222 a 239)

**Qué cambia:** Solo cambia: `Pausa();` después de los mensajes del cofre.

Sustituye las líneas 222 a 239 por:

```java
    public void ElegirPasillo()
    {
        int pasilloCofre = PasilloAleatorio();   // dónde está el cofre esta vez

        System.out.println("(1) Pasillo A \n" + "\n" + "(2) Pasillo B");
        int eleccion = sc.nextInt();
        int pasilloElegido = eleccion - 1;

        if (pasilloElegido == pasilloCofre)
        {
            System.out.println("¡Has encontrado un cofre!");
            Pausa();
            CofreAleatorio();
        }
        else
        {
            System.out.println("No hay cofre en este pasillo. Pasas al siguiente nivel.");
            Pausa();
        }
    }
```

### Paso 15. CofreAleatorio (líneas 207 a 214)

**Qué cambia:** Se añade el `switch (ganado)` que aplica lo que sale: 0 Almas y 1 Muertos te quitan vida (nunca por debajo de 1), 2 Torre da más vida y fuerza a los monstruos que quedan, 3 mete una poción en el primer hueco libre del inventario.

Sustituye las líneas 207 a 214 por:

```java
    public String CofreAleatorio()
    {
        String [] objetoGanado = {"Maldición Almas", "Maldición de Muertos" , "Maldición de la Torre", "Poción de curación"};
        int ganado = r.nextInt(objetoGanado.length);
        String objeto  = objetoGanado[ganado];
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
                for (int i = 0; i < inventario.length; i++)
                {
                    if (inventario[i] == null)
                    {
                        inventario[i] = "Poción de curación";
                        break;
                    }
                }
                break;
        }
        return objetoGanado[ganado];
    }
```

### Paso 16. InfoMounstruos (líneas 191 a 205)

**Qué cambia:** Antes solo llamaba a los `get` y no salía nada en pantalla. Ahora los imprime.

Sustituye las líneas 191 a 205 por:

```java
    public void InfoMounstruos()
    {
        System.out.println("nivel 1");
        System.out.println("Nombre : " + N1.getNombre());
        System.out.println("Vida : " + N1.getVida());
        System.out.println("Fuerza máxima : " + N1.getFuerza());
        System.out.println("Nivel 2");
        System.out.println("Nombre : " + N2.getNombre());
        System.out.println("Vida : " + N2.getVida());
        System.out.println("Fuerza máxima : " + N2.getFuerza());
        System.out.println("Nivel 3");
        System.out.println("Nombre : " + N3.getNombre());
        System.out.println("Vida : " + N3.getVida());
        System.out.println("Fuerza máxima : " + N3.getFuerza());
    }
```

### Paso 17. InfoPersonaje (líneas 183 a 189)

**Qué cambia:** Igual: ahora imprime nombre, vida, daño y score.

Sustituye las líneas 183 a 189 por:

```java
    public void InfoPersonaje()
    {
        System.out.println("Nombre : " + CP.getNombre());
        System.out.println("Vida : " + CP.getVida());
        System.out.println("Daño : " + CP.getDaño());
        System.out.println("Score : " + InfoScore());
    }
```

### Paso 18. Historia (líneas 102 a 110)

**Qué cambia:** El texto se divide en 3 `println`, uno por párrafo, con `Pausa();` entre ellos.

Sustituye las líneas 102 a 110 por:

```java
    public void Historia()
    {
        System.out.println
        ("En el reino de Kiadur, la magia fluye por cada rincón. El maná recorre la tierra, los ríos y el aire, y cada persona nace con la capacidad de absorber una cantidad distinta. Pero no todo lo que habita este mundo es bondadoso: espíritus errantes y hadas malignas acechan en los bosques y las ruinas, alimentándose del maná de quienes se atreven a cruzar su camino.\n");
        Pausa();
        System.out.println
        ("Hoy es el día más importante para los jóvenes de Kiadur: el Despertar. Al alcanzar la edad adecuada, cada joven se presenta ante el altar del maná para descubrir cuánto poder puede absorber y elegir el inicio de su camino. Algunos se convertirán en magos, otros en guerreros, escuderos o curanderos. Esta elección sellará su destino para siempre.\n");
        Pausa();
        System.out.println
        ("Tú eres uno de esos jóvenes. Tras tu Despertar, tendrás que demostrar tu valía enfrentándote a tres criaturas que amenazan el reino, cada una más poderosa que la anterior. Solo quien sobreviva a los tres encuentros será recordado como un verdadero héroe.");
        Pausa();
    }
```

### Paso 19. PanelInfo (línea 152)

**Qué cambia:** `while (respuesta)` pasa a `while (!respuesta)`. Como `respuesta` empieza en `false`, el bucle no entraba nunca y el panel no se abría.

Línea 152: `while (respuesta)` → `while (!respuesta)`

### Paso 20. Inicio (líneas 125 y 144)

**Qué cambia:**
1. Entre la línea 144 (`}` del while) y la 145 (`}` del método) añade `return false;`. El método es `boolean` y tiene que devolver algo.
2. Debajo de la línea 125 (`int eleccion = sc.nextInt();`) añade `sc.nextLine();`. `nextInt()` deja el Enter y luego el nombre se quedaba vacío.

Haz primero la 1 y luego la 2 (de abajo a arriba).

### Paso 21. Juego (líneas 72, 66 y 61 a 63)

**Qué cambia:**
1. Línea 72: `boolean abandonado = false;` → `abandonado = false;`
2. Línea 66: `String[] inventario = new String[10];` → `inventario = new String[10];`
   Con el tipo delante creas una variable nueva que solo existe dentro de Juego(); sin el tipo, usas el atributo de la clase (paso siguiente) y todos los métodos la ven.
3. Líneas 61 a 63: la fuerza baja a 10, 15 y 18 (ahora es el daño **máximo**, cada golpe es aleatorio entre 5 y ese número):
```java
        N1 = new Nivel1("Hada corrupta", 50, 1, 10, 0);
        N2 = new Nivel2("Espectro de maná", 100, 2, 15, 0);
        N3 = new Nivel3("Guardián de la Torre", 150, 3, 18, 0);
```

### Paso 22. Atributos nuevos y método Pausa (después de la línea 49)

Debajo de `PocionCuracion PC = new PocionCuracion(...);` (línea 49) añade:

```java

    //---Inventario---
    String [] inventario = new String[10];

    //---Abandono de la partida---
    boolean abandonado = false;

    //---Pausa entre textos para que no salga todo de golpe---
    public void Pausa()
    {
        try
        {
            Thread.sleep(1500);         //1500 milisegundos = 1,5 segundos
        }
        catch (InterruptedException e)
        {
            //si se interrumpe la espera, seguimos sin hacer nada
        }
    }
```

`inventario` y `abandonado` pasan a ser atributos (los usan Juego, Combate, ConsumirCura, CofreAleatorio e InfoScore). `Pausa()` espera 1,5 segundos (`Thread.sleep` va en `try/catch` porque Java lo obliga). Si lo quieres más lento o rápido, cambia el `1500`.

### Paso 23. Maldiciones con su valor (líneas 44 a 46)

```java
    MaldicionAlmas MA = new MaldicionAlmas("Maldición de las Almas", 1, -20, null);
    MaldicionMuertos MM = new MaldicionMuertos("Maldición de los Muertos", 1, -15, null);
    MaldicionTorre MR = new MaldicionTorre("Maldición de la Torre", 1, 20, 5, null);
```
Con 0, la maldición no hacía nada.

### Paso 24. Armas con nombre (líneas 28 a 35)

Cambia el primer `null` de cada arma por su nombre:
```java
    ArcoSimple AS = new ArcoSimple("Arco Simple", 1, 15, null);
    ArcoTriple AT = new ArcoTriple("Arco Triple", 1, 17, null);
    EspadaLigera EL = new EspadaLigera("Espada Ligera", 1, 17, null);
    EspadaPesada EP = new EspadaPesada("Espada Pesada", 1, 20, null);
    PoderLuz PL = new PoderLuz("Poder de Luz", 1, 16, null);
    PoderOscuridad PO = new PoderOscuridad("Poder de Oscuridad", 1, 19, null);
```
(Las líneas de comentario `//---Espada--` y `//---Poder---` se quedan donde están.)

### Paso 25. Import que sobra (línea 3)

Borra `import java.lang.annotation.Documented;`. No se usa (seguramente VS Code lo añadió solo).

---

## PARTE 2. Objetos

Cada objeto creaba su **propio** personaje o monstruo con `new`, que no es el que juega, por eso las maldiciones no te afectaban. Se borra ese `new` y el método **recibe** el personaje o monstruo de verdad.

- **MaldicionAlmas.java**: borra la línea 8 (`ClasePersonaje CP = new ...`). Línea 22: `ponerMaldiciónAlmas()` → `ponerMaldiciónAlmas(ClasePersonaje CP)`
- **MaldicionMuertos.java**: borra la línea 8. Línea 30: `ponerMaldicionMuertos()` → `ponerMaldicionMuertos(ClasePersonaje CP)`
- **MejoraArma.java**: borra la línea 8. Línea 44: `ponerNivelArma()` → `ponerNivelArma(ClasePersonaje CP)`
- **MaldicionTorre.java**: 
  - Líneas 42, 45 y 50: son las de la fuerza y ponen `setVida`; cámbialo por `setFuerza` (ej. `N2.setFuerza(N2.getFuerza() + dañoMounstruo);`).
  - Línea 47: `ponerMaldicionMounstruosSegundoCofre()` → `ponerMaldicionMounstruosSegundoCofre(Nivel3 N3)`
  - Línea 39: `ponerMaldicionMounstruosPrimerCofre()` → `ponerMaldicionMounstruosPrimerCofre(Nivel2 N2, Nivel3 N3)`
  - Borra las líneas 9 y 10 (`Nivel2 N2 = new ...` y `Nivel3 N3 = new ...`).

Los `import` se quedan.

## PARTE 3. Codigo.java

Ahora está vacía y sin `main`. Déjala así:

```java
package Proyecto;

public class Codigo 
{
    public static void main(String[] args) 
    {
        Metodos m = new Metodos();

        m.Historia();
        m.Inicio();
    }
}
```

---

Cuando acabes, Ctrl+Shift+P → "Java: Clean Java Language Server Workspace" y Run en `Proyecto/Codigo.java`. Si algo falla, mándame el error.
