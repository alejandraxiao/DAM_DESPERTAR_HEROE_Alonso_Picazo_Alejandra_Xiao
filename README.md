# El despertar del héroe

Aventura textual por turnos en Java para la 1.ª evaluación de 1.º DAM (Programación, Entornos de desarrollo y Lenguaje de marcas).

El jugador crea a su héroe con la ayuda de Bigotín, su guía, y entra en la prueba de la torre. Allí tiene que vencer a tres mounstruos seguidos, cada uno con más vida que el anterior. Puede atacar, beber pociones o abandonar. Toda partida terminada se guarda en un ranking de las 10 mejores puntuaciones, que se conserva en un fichero XML.

## Requisitos

- **Java 15 o superior** (el código usa bloques de texto `"""`). Probado con **Java 26** (`javac 26.0.2.1`).
- Una terminal: PowerShell, CMD o la terminal de VS Code.

Para comprobar la versión instalada:

```powershell
java -version
```

## Compilar y ejecutar

Todos los comandos se escriben desde la carpeta principal del proyecto (la que contiene `Proyecto`, `Clases` y `02_XML`).

1. Compilar (los `.class` van a la carpeta `bin`, que no se sube a git):

```powershell
javac -encoding UTF-8 -d bin Armas/*.java Clases/*.java Mounstruos/*.java Objetos/*.java Proyecto/*.java
```

2. Ejecutar:

```powershell
java -cp bin Proyecto.Codigo
```

Si las tildes y la ñ se ven mal en la consola de Windows, cambia la consola a UTF-8 antes de ejecutar:

```powershell
chcp 65001
```

El juego busca la carpeta `02_XML` para leer y guardar el ranking. Funciona igual si se ejecuta desde la carpeta principal o desde la carpeta `Proyecto`.

## Cómo se juega

Menú principal:

| Opción | Qué hace |
|---|---|
| 1. Nueva partida | Empieza una partida desde cero: nombre, personaje, arma y la torre |
| 2. Consultar ranking | Muestra las 10 mejores puntuaciones |
| 3. Ayuda | Explica las reglas, las acciones y la puntuación |
| 4. Salir | Cierra el juego |

En cada turno de combate:

| Opción | Qué hace | ¿Gasta el turno? |
|---|---|---|
| 1. Atacar | Daño aleatorio entre 5 menos y 5 más que el daño del arma | Sí, y el mounstruo responde si sigue vivo |
| 2. Usar cura | Cura 25 de vida, sin pasar de 100 | Solo si se ha podido curar (sin pociones o con la vida llena no) |
| 3. Salir de la torre | Abandona la partida (se guarda en el ranking) | — |
| 4. Panel de información | Datos del personaje y de los mounstruos | No |
| 5. Ver inventario | Muestra las pociones que quedan | No |

Reglas principales (todas son constantes en `Proyecto/Metodos.java`):

- El héroe empieza con 100 de vida y 3 pociones de 25.
- Los mounstruos son el Hada corrupta (50 de vida, fuerza 10), el Espectro de maná (100 de vida, fuerza 15) y el Guardián de la Torre (150 de vida, fuerza 18). Cada ataque suyo hace entre 5 y su fuerza de daño.
- La vida nunca baja de 0 ni pasa de 100.
- Entre nivel y nivel se elige un pasillo; en uno de los dos hay un cofre que puede dar una poción o una maldición.
- **Puntuación:** 100 puntos si se derrota a 1 mounstruo, 450 si se derrota a 2 y 1000 si se derrota a los 3, más la vida que queda. Si se abandona se restan 10 puntos y el score nunca baja de 0.

## Estructura del proyecto

```
Proyecto/      Codigo.java (main) y Metodos.java (todos los métodos static del juego)
Clases/        Clases base: personaje, mounstruo, arma y objeto
Armas/         Armas de cada personaje (poder de luz y oscuridad, espadas y arcos)
Mounstruos/    Los tres niveles de mounstruo
Objetos/       Poción y maldiciones del cofre
02_XML/        Ficheros de Lenguaje de marcas (ranking e inventario)
Notas extra/   Diagrama de flujo
```

## Parte de Lenguaje de marcas (carpeta `02_XML`)

| Fichero | Para qué sirve |
|---|---|
| `score.xml` | Ranking. Lo lee el juego al arrancar y lo reescribe al terminar cada partida |
| `score.dtd` | Estructura válida del ranking (puede estar vacío; cada jugador tiene id, nombre y puntos). El juego guarda como máximo 10 |
| `score.xslt` | Transforma el ranking en una tabla HTML ordenada de mayor a menor |
| `score.html` | Resultado de aplicar `score.xslt` a `score.xml` |
| `inventario.xml` | Inventario de la partida. El juego lo reescribe cada vez que cambia (al empezar, al beber una poción y al encontrar una en un cofre) |
| `inventario.dtd` | Estructura válida del inventario (capacidad, pociones ocupadas y cada poción) |
| `inventario.xslt` | Transforma el inventario en una tabla HTML |
| `inventario.html` | Resultado de aplicar `inventario.xslt` a `inventario.xml` |
| `score.css` | Diseño común de los dos HTML |

Los dos XML enlazan su DTD (`<!DOCTYPE>`) y su XSLT (`<?xml-stylesheet?>`), así que se pueden validar y transformar con la extensión XSLT/XPath de VS Code o abriéndolos en un navegador que aplique XSLT a ficheros locales (por ejemplo Firefox).

Si al leer `score.xml` el juego encuentra datos incorrectos (puntos negativos, puntos que no son números, un jugador sin nombre o más de 10 jugadores), avisa y no lo sobrescribe hasta que se corrija. Si no existe, empieza con el ranking vacío.
