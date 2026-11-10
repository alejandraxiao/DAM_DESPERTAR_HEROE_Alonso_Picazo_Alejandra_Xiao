# Cambios para que tu juego compile y funcione

Basado en tu Metodos.java actual (10/11/2026). Los números de línea son los de TU archivo ahora mismo.

**Importante:** haz los cambios en el orden de este archivo, que va **de abajo hacia arriba**. Así, al añadir o borrar líneas abajo, los números de las líneas de arriba no cambian.

---

## PARTE 1. Metodos.java

### Paso 1. ArmaArquero: leer el arco elegido (después de la línea 531)

Debajo de `System.out.println("(2) Arco Ligero");` añade:

```java
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
```

Por qué: ahora mismo enseñas las opciones pero nunca lees cuál elige ni la guardas en el personaje.

### Paso 2. ArmaCaballero: leer la espada elegida (después de la línea 516)

Debajo de `System.out.println("(2) Espada ligera");` añade:

```java
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
```

### Paso 3. ArmaMago: leer el poder elegido (después de la línea 501)

Debajo de `System.out.println("(2) Oscuridad");` añade:

```java
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
```

### Paso 4. Emergencia: abandonar (línea 479)

Cambia:
```java
                PartidaAbandonada(null, null);
```
por:
```java
                abandonado = true;
```

Por qué: aquí solo marcamos que ha abandonado. El mensaje de abandono ya lo saca Combate() (paso 10), con el nombre del guía y del jugador en vez de `null`.

### Paso 5. TorreExplicacion: el bucle nunca entra (línea 434)

Cambia:
```java
        while (respuesta)
```
por:
```java
        while (!respuesta)
```

Por qué: `respuesta` empieza en `false`, así que `while (respuesta)` no entra nunca y la explicación no se ve. Con `!respuesta` se repite hasta que el jugador pulse 1.

### Paso 6. SaludoInicial: número del arquero (línea 425)

Cambia `(4) El arquero` por `(3) El arquero`.

### Paso 7. ConsumirCura (líneas 340 a 345 y 353)

**Borra** las líneas 340 a 345:
```java
        String [] inventario = new String[3];

        inventario [0] = "pocion de curacion";
        inventario [1] = "pocion de curacion";
        inventario [2] = "pocion de curacion";

```

Por qué: este array se crea nuevo cada vez que te curas, así que siempre tendrías 3 pociones (infinitas). Al borrarlo, el método usa el `inventario` del atributo (paso 15).

Opcional, debajo de la línea 353 (`CP.setVida(Math.min(...));`) añade para que el jugador vea su vida:
```java
                System.out.println("Te has curado. Tu vida: " + CP.getVida());
```

### Paso 8. VisualizaciónAtaque: abandonar (línea 331)

Cambia:
```java
                    PartidaAbandonada(null, null);
```
por:
```java
                    abandonado = true;
```

### Paso 9. VisualizaciónAtaque: recibir el monstruo y leer dentro del bucle (líneas 305 a 312)

Ahora mismo tienes (305-312):
```java
    public void VisualizaciónAtaque()
    {
        System.out.println("(1) Atacar \n" + "\n" + "(2) Usar cura \n" + "\n" + "(3) Salir de la torre");

        int respuestaAtaque = sc.nextInt();
        boolean respuesta = true;
        while (respuesta)
        {
```

Déjalo así:
```java
    public void VisualizaciónAtaque(ClaseMoustruo M)
    {
        boolean respuesta = true;
        while (respuesta)
        {
            System.out.println("(1) Atacar \n" + "\n" + "(2) Usar cura \n" + "\n" + "(3) Salir de la torre");

            int respuestaAtaque = sc.nextInt();
```

Qué cambia:
1. `(ClaseMoustruo M)`: el método recibe el monstruo con el que peleas. Por eso la línea 321 `M.setVida(...)` ya no da error.
2. El menú y el `sc.nextInt()` pasan **dentro** del while. Si están fuera, al elegir "2) Usar cura" vuelve a repetir la opción 2 para siempre (bucle infinito) porque nunca vuelve a preguntar.

Opcional, debajo de la línea 321 (`M.setVida(M.getVida() - numeroDaño);`) añade:
```java
                    System.out.println("Haces " + numeroDaño + " de daño. Vida de " + M.getNombre() + ": " + M.getVida());
```

### Paso 10. Crear el método Combate (encima de la línea 305)

Debajo de la línea 303 (`//----PARTE DE COMBATES----`) pega este método nuevo:

```java
    //---Combate por turnos: devuelve true si ganas y false si pierdes o abandonas---
    public boolean Combate(ClaseMoustruo M)
    {
        System.out.println("Aparece " + M.getNombre() + " con " + M.getVida() + " de vida");

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
                return true;
            }

            //---Turno del mounstruo---
            CP.setVida(CP.getVida() - M.getFuerza());
            System.out.println(M.getNombre() + " te hace " + M.getFuerza() + " de daño. Tu vida: " + CP.getVida());

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
```

Cómo funciona, paso a paso:
1. Mientras los dos sigan vivos, se repite el turno.
2. Tú eliges en VisualizaciónAtaque(M). Curarte no acaba el turno; atacar o abandonar sí.
3. Si abandonaste: mensaje de abandono y `return false` (vuelve al menú).
4. Si el monstruo llega a 0: `return true` (Juego() pasa a elegir pasillo).
5. Si no, el monstruo te pega su `fuerza`.
6. Si llegas a 0: mensaje de perder y `return false`.
7. Si te quedan 25 o menos, sale Emergencia().

Juego() ya usa este método en `if (!Combate(N1))`, así que ese error desaparece.

### Paso 11. Mostrar el score en los mensajes finales (líneas 299, 291 y 281)

En las tres líneas cambia:
```java
        InfoScore();
```
por:
```java
        System.out.println("Score : " + InfoScore());
```

Por qué: InfoScore() **devuelve** un número, pero si no lo imprimes no se ve en pantalla.

### Paso 12. InfoScore (líneas 249 a 273)

Déjalo así (sustituye desde la línea 249 hasta la 273):

```java
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
```

Qué estaba mal:
1. Sin `else`, los tres `if` se cumplen a la vez y el último pisa a los demás: siempre saldría 100.
2. `&` funciona, pero en condiciones se usa `&&`.
3. `== 0`: la vida del monstruo puede quedar en negativo (por ejemplo -5), por eso `<= 0`.
4. `scoreAbandono = -10` y luego `- scoreAbandono` es restar un negativo, o sea **sumar** 10. Y además se restaba siempre, no solo al abandonar.
5. Se borra el `if (CP.getVida() == 0 & N1.getVida() > 0)` porque scorePartida ya empieza en 0.

### Paso 13. Crear el método Ranking (después de la línea 243)

Debajo de `//---ranking---` añade:

```java
    public void Ranking()
    {
        System.out.println("Score : " + InfoScore());
    }
```

Por qué: Inicio() llama a `Ranking()` en la línea 136 y no existe, por eso no compila. De momento solo muestra el score; guardar en Score.xml lo hacemos después.

### Paso 14. CofreAleatorio: aplicar lo que sale (después de la línea 214)

Debajo de `System.out.println("Has ganado : " + objeto);` añade:

```java
        switch (ganado)
        {
            case 0:
                MA.ponerMaldiciónAlmas(CP);
                if (CP.getVida() < 1)
                {
                    CP.setVida(1);              //una maldición no te puede matar
                }
                System.out.println("Tu vida: " + CP.getVida());
                break;
            case 1:
                MM.ponerMaldicionMuertos(CP);
                if (CP.getVida() < 1)
                {
                    CP.setVida(1);
                }
                System.out.println("Tu vida: " + CP.getVida());
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
```

Por qué: ahora el cofre solo dice lo que has ganado, pero no hace nada. `ganado` es la posición del array: 0 Almas, 1 Muertos, 2 Torre, 3 Poción. La poción se mete en el primer hueco vacío del inventario. Necesita la PARTE 2 (objetos) para compilar.

### Paso 15. Inicio: devolver un valor (después de la línea 146)

Entre la `}` del while (línea 146) y la `}` del método (línea 147) añade:
```java
        return false;
```

Por qué: el método es `boolean`, así que tiene que devolver algo al terminar.

### Paso 16. Inicio: limpiar el Enter (después de la línea 127)

Debajo de `int eleccion = sc.nextInt();` añade:
```java
            sc.nextLine();
```

Por qué: `nextInt()` lee el número pero deja el Enter. Luego `nextLine()` del nombre (SaludoInicial) se come ese Enter y el nombre queda vacío.

### Paso 17. Juego(): quitar el tipo a inventario y abandonado (líneas 65 y 71)

Línea 65, cambia:
```java
        String[] inventario = new String[10];
```
por:
```java
        inventario = new String[10];
```

Línea 71, cambia:
```java
        boolean abandonado = false;
```
por:
```java
        abandonado = false;
```

Por qué: si pones el tipo delante, creas una variable **nueva** que solo existe dentro de Juego(). Combate(), ConsumirCura(), etc. no la pueden ver. Sin el tipo, usas el atributo de la clase (paso 18) y solo lo reinicias para la partida nueva.

### Paso 18. Declarar inventario y abandonado como atributos (después de la línea 48)

Debajo de la línea del `PocionCuracion PC = ...` añade:

```java

    //---Inventario---
    String [] inventario = new String[10];

    //---Abandono de la partida---
    boolean abandonado = false;
```

Por qué: al estar fuera de los métodos, todos los métodos de Metodos los pueden usar.

### Paso 19. Maldiciones con su valor (líneas 43 a 45)

Cambia el `0` por los valores reales:
```java
    MaldicionAlmas MA = new MaldicionAlmas("Maldición de las Almas", 1, -20, null);
    MaldicionMuertos MM = new MaldicionMuertos("Maldición de los Muertos", 1, -15, null);
    MaldicionTorre MR = new MaldicionTorre("Maldición de la Torre", 1, 20, 5, null);
```

Por qué: el constructor guarda el número que le pasas. Con 0, la maldición quita 0 de vida.

### Paso 20. Armas con nombre (líneas 27 a 34)

Cambia el primer `null` de cada arma por su nombre:
```java
    ArcoSimple AS = new ArcoSimple("Arco Simple", 1, 15, null);
    ArcoTriple AT = new ArcoTriple("Arco Triple", 1, 17, null);
    EspadaLigera EL = new EspadaLigera("Espada Ligera", 1, 17, null);
    EspadaPesada EP = new EspadaPesada("Espada Pesada", 1, 20, null);
    PoderLuz PL = new PoderLuz("Poder de Luz", 1, 16, null);
    PoderOscuridad PO = new PoderOscuridad("Poder de Oscuridad", 1, 19, null);
```

Por qué: en los pasos 1 a 3 haces `CP.setArma(AT.getNombre())`; con `null` el arma no tendría nombre.

---

## PARTE 2. Objetos (en cada archivo de la carpeta Objetos)

La idea: ahora cada objeto crea su **propio** personaje o monstruo (`new ClasePersonaje(...)`), que no es el que juega. Por eso la maldición no te afecta. Hay que borrar ese `new` y que el método **reciba** el personaje o monstruo de verdad.

### MaldicionAlmas.java
- **Borra** la línea 8: `ClasePersonaje CP = new ClasePersonaje(null, null, null, 0, 0, 0, 0);`
- Línea 22: `public void ponerMaldiciónAlmas()` → `public void ponerMaldiciónAlmas(ClasePersonaje CP)`

### MaldicionMuertos.java
- **Borra** la línea 8: `ClasePersonaje CP = new ClasePersonaje(null, null, null, 0, 0, 0, 0);`
- Línea 30: `public void ponerMaldicionMuertos()` → `public void ponerMaldicionMuertos(ClasePersonaje CP)`

### MaldicionTorre.java
- **Borra** las líneas 9 y 10 (`Nivel2 N2 = new ...` y `Nivel3 N3 = new ...`).
- Línea 39: `public void ponerMaldicionMounstruosPrimerCofre()` → `public void ponerMaldicionMounstruosPrimerCofre(Nivel2 N2, Nivel3 N3)`
- Línea 47: `public void ponerMaldicionMounstruosSegundoCofre()` → `public void ponerMaldicionMounstruosSegundoCofre(Nivel3 N3)`
- Líneas 42, 45 y 50: son las de la fuerza y pone `setVida`. Cámbialo por `setFuerza`:
```java
        N2.setFuerza(N2.getFuerza() + dañoMounstruo);
        N3.setFuerza(N3.getFuerza() + dañoMounstruo);
```

### MejoraArma.java
- **Borra** la línea 8: `ClasePersonaje CP = new ClasePersonaje(null, null, null, 0, 0, 0, 0);`
- Línea 44: `public void ponerNivelArma()` → `public void ponerNivelArma(ClasePersonaje CP)`

(Los `import` de arriba se quedan, porque siguen usando ClasePersonaje, Nivel2 y Nivel3.)

---

## PARTE 3. Codigo.java

Ahora la clase está vacía y no tiene `main`, así que el juego no arranca. Déjala así:

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

## Cuando acabes

Dale a Run en Codigo.java. Si algo da error, mándame el mensaje de error y la línea.
