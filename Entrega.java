import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb "// TODO". L'enunciat de
 * cada un d'ells és al comentari de la seva signatura i exemples del seu funcionament als mètodes
 * `Tema1.tests`, `Tema2.tests`, etc.
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà un 0.
 *
 * - Si heu fet cap modificació que no sigui afegir un mètode, afegir proves als mètodes "tests()" o
 *   implementar els mètodes annotats amb "// TODO", la nota del grup serà un 0.
 *
 * - Principalment, la nota dependrà del correcte funcionament dels mètodes implemnetats (provant
 *   amb diferents entrades).
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . Algunes
 *   consideracions importants:
 *    + Entregau amb la mateixa codificació (UTF-8) i finals de línia (LF, no CR+LF)
 *    + Indentació i espaiat consistent
 *    + Bona nomenclatura de variables
 *    + Declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *      declaracions).
 *    + Convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for (int i = 0; ...))
 *      sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni qualificar classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 10.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Harpo Joan Alberola
 * - Nom 2: Antoni Contestí Coll
 * - Nom 3: Marc Ferrer Fernàndez
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * La majoria dels mètodes reben de paràmetre l'univers (representat com un array) i els predicats
   * adients (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un
   * element de l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si
   * `P(x)` és cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis (excepte el primer) us demanam que donat l'univers i els
   * predicats retorneu `true` o `false` segons si la proposició donada és certa (suposau que
   * l'univers és suficientment petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * Donat n > 1, en quants de casos (segons els valors de veritat de les proposicions p1,...,pn)
     * la proposició (...((p1 -> p2) -> p3) -> ...) -> pn és certa?
     *
     * Vegeu el mètode Tema1.tests() per exemples.
     */
    static int exercici1(int n) {
      // Matriz para almacenar los valores de verdad de las proposiciones
      int[] proposicions = new int[n];
      // Contador para el número de veces que la proposición completa es cierta
      int contador = 0;
      // Variable temporal para almacenar el resultado intermedio
      int temporal;

      // Vamos a iterar sobre todos los posibles valores de verdad (2^n combinaciones)
      for (int i = 0; i < (int) Math.pow(2, n); i++) {
        // Llenamos la matriz de proposiciones con los valores de verdad actuales
        for (int j = 0; j < n; j++) {
          // Asignar a proposicions[j] el j-ésimo bit de i (0 o 1)
          proposicions[j] = (i >> j) & 1;
        }
        // Inicializamos temporal con el valor de verdad de la última proposición pn
        temporal = proposicions[n - 1];
        // Evaluamos la expresión de derecha a izquierda
        for (int j = n - 2; j >= 0; j--) {
          // Evaluamos el implicador (p -> q) de la siguiente manera
          // Si temporal es 1 (true) y proposicions[j] es 0 (false), entonces resultado es 0 (false)
          // De lo contrario el  resultado es 1 (true)
          if (temporal == 1 && proposicions[j] == 0) {
            temporal = 0;
          } else {
            temporal = 1;
          }
        }
        // Si la expresión completa es cierta (temporal es 1), incrementamos el contador
        if (temporal != 0) {
          contador++;
        }
      }
      // Devolvemos el número de casos donde la proposición es cierta
      return contador;
    } 

    /*
     * És cert que ∀x : P(x) -> ∃!y : Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      // Iteram sobre tots els elements de l'univers
      for (int x : universe) {
        // Si P(x) és vertader, hem de comprovar el cas en que Q(x,y) no ho sigui
        if (p.test(x)) {
          boolean trobat = false;
          // Iteram sobre tots els elements y
          for (int y : universe) {
            // Comprovam el cas en que es compleixi Q(x,y)
            if (q.test(x, y)) {
              // Si ja haviem trobat y anteriorment, no és cert (més d'un y)
              if (trobat) {
                return false;
              }
              trobat = true;
            }
          }
          if (!trobat) {
            return false;
          }
        }
      }
      return true;
    }

    /*
     * És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
     */
    static boolean exercici3(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      // Iteram sobre tots els elements de l'univers
      for (int x : universe) {
        boolean existeix = true;
        // Iteram amb y sobre els elements de l'univers
        for (int y : universe) {
          // Si l'implicació és falsa, no existeix l'element x
          if (q.test(x, y) && !p.test(x)) {
            existeix = false;
          }
        }
        if (existeix) {
          return true;
        }
      }
    return false;
    }

    /*
     * És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
     */
    static boolean exercici4(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
      // Iteramos sobre cada elemento en el universo como el posible valor de x
      for (int var_x : universe) {
        // Para verificar si existe un único y que cumpla la condición
        boolean existe_y = false;
         // Contador para contar cuántos y cumplen la condición
        int contador_y = 0;
        // Iteramos sobre cada elemento en el universo como el posible valor de y
        for (int var_y : universe) {
          // Para verificar si para todos z, P(x, z) <-> Q(y, z) es verdadero
          boolean coincide = true;
          // Iteramos sobre cada elemento en el universo como el posible valor de z
          for (int var_z : universe) {
            // Verificar si P(x, z) es diferente de Q(y, z), en cuyo caso no coincide
            if (p.test(var_x, var_z) != q.test(var_y, var_z)) {
              coincide = false;
              // Si no coincide para algún z, salir del bucle
              break;  
            }
          }
          // Si para todos los z, P(x, z) <-> Q(y, z) es verdadero
          if (coincide) {
            // Marcamos que existe al menos un y que cumple la condición
            existe_y = true;  
            // Incrementamos el contador de y que cumplen la condición
            contador_y++;     
          }
        }
        // Si existe exactamente un y que cumple la condición para este x
        if (existe_y && contador_y == 1){
          // La proposición es verdadera, retornar true
          return true;  
        }
      }
      return false;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1

      // p1 -> p2 és cert exactament a 3 files
      // p1 p2
      // 0  0  <-
      // 0  1  <-
      // 1  0
      // 1  1  <-
      assertThat(exercici1(2) == 3);

      // (p1 -> p2) -> p3 és cert exactament a 5 files
      // p1 p2 p3
      // 0  0  0
      // 0  0  1  <-
      // 0  1  0
      // 0  1  1  <-
      // 1  0  0  <-
      // 1  0  1  <-
      // 1  1  0
      // 1  1  1  <-
      assertThat(exercici1(3) == 5);

      // Exercici 2
      // ∀x : P(x) -> ∃!y : Q(x,y)
      assertThat(
          exercici2(
            new int[] { 1, 2, 3 },
            x -> x % 2 == 0,
            (x, y) -> x+y >= 5
          )
      );

      assertThat(
          !exercici2(
            new int[] { 1, 2, 3 },
            x -> x < 3,
            (x, y) -> x-y > 0
          )
      );

      // Exercici 3
      // És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 3 != 0,
            (x, y) -> y % x == 0
          )
      );

      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 4 != 0,
            (x, y) -> (x*y) % 4 != 0
          )
      );

      // Exercici 4
      // És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
      assertThat(
          exercici4(
            new int[] { 0, 1, 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );

      assertThat(
          !exercici4(
            new int[] { 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {0,1}, {1,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   * Als tests utilitzarem extensivament la funció generateRel definida al final (també la podeu
   * utilitzar si la necessitau).
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam o bé amb el seu
   * graf o amb un objecte de tipus Function<Integer, Integer>. Sempre donarem el domini int[] a, el
   * codomini int[] b. En el cas de tenir un objecte de tipus Function<Integer, Integer>, per aplicar
   * f a x, és a dir, "f(x)" on x és d'A i el resultat f.apply(x) és de B, s'escriu f.apply(x).
   */
  static class Tema2 {
    /*
     * Calculau el nombre d'elements del conjunt (a u b) × (a \ c)
     *
     * Podeu soposar que `a`, `b` i `c` estan ordenats de menor a major.
     */
    static int exercici1(int[] a, int[] b, int[] c) {
      int union, diferencia;
      // Variable para contar elementos adicionales en la unión
      int elementos = 0;  

      // Calcular el número de elementos en a U b (unión de a y b)
      for (int ele_B : b) {
        // Si ele_B no está en a
        if (!esta_dentro(ele_B, a)) {
          // Incrementamos el contador de elementos adicionales
          elementos++;
        }
      }
      // Calcular el tamaño de la unión
      // La unión incluye todos los elementos de a más los elementos adicionales de b
      if (a.length > b.length) {
        union = a.length + elementos;
      } else {
        union = b.length + elementos;
      }
      // Reiniciar el contador para la diferencia
      elementos = 0;  
      // Calcular el número de elementos en a \ c (diferencia entre a y c)
      for (int ele_C : c) {
        // Si ele_C está en a
        if (esta_dentro(ele_C, a)) {
          // Incrementar el contador de elementos que están en a y c
          elementos++;   
        }
      }
      // La diferencia es el tamaño de a menos los elementos que están en c
      diferencia = a.length - elementos;  
      // Retornar el producto del tamaño de la unión y la diferencia
      return union * diferencia;
    }

    /*
    * Método auxiliar para verificar si un número está en una matriz
    */
    private static boolean esta_dentro(int num, int[] arr) {
      for (int i = 0; i < arr.length; i++) {
        // Si la matriz contiene el número que buscamos
        if (arr[i] == num) { 
          return true;
        }
      }
      return false;
    }

    /*
     * La clausura d'equivalència d'una relació és el resultat de fer-hi la clausura reflexiva, simètrica i
     * transitiva simultàniament, i, per tant, sempre és una relació d'equivalència.
     *
     * Trobau el cardinal d'aquesta clausura.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    public static int exercici2(int[] a, int[][] rel) {
      int n = a.length;
      boolean[][] clausura = new boolean[n][n];
      int cardinal = 0;

      // Inicialitzam la clausura a false
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          clausura[i][j] = false;
        }
      }

      // Omplim elements de la clausura amb les relacions que ho són
      for (int[] pair : rel) {
        int fila = pair[0];
        int columna = pair[1];

        // Trobam els índexs corresponents als valors de fila i columna
        int filaIndex = -1;
        int columnaIndex = -1;

        for (int i = 0; i < n; i++) {
            if (a[i] == fila) {
              filaIndex = i;
            }
            if (a[i] == columna) {
              columnaIndex = i;
            }
        }

        // Asseguram que els índexs estan dins dels límits de la matriu
        if (filaIndex != -1 && columnaIndex != -1) {
          clausura[filaIndex][columnaIndex] = true;
        }
      }

      // Aplicam la clausura reflexiva
      for (int i = 0; i < n; i++) {
        clausura[i][i] = true;
      }

      // Aplicam la clausura simètrica
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (clausura[i][j]) {
            clausura[j][i] = true;
          }
        }
      }

      // Aplicam la clausura transitiva (algorisme Floyd-Warshall)
      for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            clausura[i][j] = clausura[i][j] || (clausura[i][k] && clausura[k][j]);
          }
        }
      }

      // Comptar el cardinal
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (clausura[i][j]) {
            cardinal++;
          }
        }
      }

      return cardinal;
    }

    /*
     * Comprovau si la relació `rel` és un ordre total sobre `a`. Si ho és retornau el nombre
     * d'arestes del seu diagrama de Hasse. Sino, retornau -2.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici3(int[] a, int[][] rel) { 
      // Si no es ordreTotal, devolvemos -2, sino se construye el diagrama de Hasse y se devuelven las arestas 
      if (!esOrdreTotal(a, rel)){
        return -2;
      } else {
        int numArestes = construirHasse(a, rel);
        return numArestes;
      }
    }

    /*
     * Método auxiliar para verificar que es un orden total sobre a
     */
    static boolean esOrdreTotal(int[] a, int[][] rel) {
      int n = a.length;

      // Matriz de adyacencia para representar las relaciones
      boolean[][] matriz = new boolean[n][n];
      for (int[] par : rel) {
        int x = par[0];
        int y = par[1];
        matriz[x][y] = true;
      }

      // Verificación de reflexividad
      for (int i = 0; i < n; i++) {
        if (!matriz[a[i]][a[i]]) {
          return false;
        }
      }

      // Verificación de antisimetría y transitividad
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          int x = a[i];
          int y = a[j];
          if (x == y) continue;

          // Verificación de antisimetría
          if (matriz[x][y] && matriz[y][x] && x != y) {
            return false;
          }

          // Verificación de transitividad
          for (int k = 0; k < n; k++) {
            int z = a[k];
            if (matriz[x][y] && matriz[y][z] && !matriz[x][z]) {
              return false;
            }
          }
        }
      }

    return true;
    }
    
    /*
     * Método auxiliar que contruye el diagrama de Hasse con un orden total 'a' y una relación 'rel'
     */
    static int construirHasse(int[] a, int[][] rel) {
      int n = a.length;
      boolean[][] matriz = new boolean[n][n];
      for (int[] par : rel) {
        int x = par[0];
        int y = par[1];
        matriz[x][y] = true;
      }

      int numArestes = 0;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i != j && matriz[i][j]) {
            boolean esMinima = true;
              for (int k = 0; k < n; k++) {
                if (k != i && k != j && matriz[i][k] && matriz[k][j]) {
                  esMinima = false;
                  break;
                }
              }
            if (esMinima) {
              numArestes++;
            }
          }
        }
      }

      return numArestes;
    }
    

    /*
     * Comprovau si les relacions `rel1` i `rel2` són els grafs de funcions amb domini i codomini
     * `a`. Si ho son, retornau el graf de la composició `rel2 ∘ rel1`. Sino, retornau null.
     *
     * Podeu soposar que `a`, `rel1` i `rel2` estan ordenats de menor a major (les relacions,
     * lexicogràficament).
     */
    static int[][] exercici4(int[] a, int[][] rel1, int[][] rel2) {
      // Comprovamos que las dos relaciones son funciones, si lo son, devolvemos la composición de ambas
      if (!esFuncio(a, rel1) || !esFuncio(a, rel2)) {
        return null;
      } else {
        return composicio(rel1, rel2, a);
      }
    }

    /*
     * Método auxiliar que determina un dominio y codominio, junto a una relación, es una función
     */
    static boolean esFuncio(int[] a, int[][] rel) {
      // Verificamos que cada elemento del dominio tiene una única imagen
      for (int i = 0; i < a.length; i++) {
        int x = a[i];
        boolean encontrado = false;
        for (int j = 0; j < rel.length; j++) {
          if (rel[j][0] == x) {
            if (encontrado) {
              // Más de una imagen para el mismo x
              return false; 
            }
            encontrado = true;
          }
        }
        if (!encontrado) {
          // No tiene imagen
          return false; 
        }
      }
      return true;
    }

    /*
     * Método auxiliar que realiza la composición de dos funciones
     */
    static int[][] composicio(int[][] rel1, int[][] rel2, int[] a) {
      //Creación lista de la composición
      List<int[]> composicio = new ArrayList<>();
      
      //Recorre rel1 para encontrar todos los pares
      for (int i = 0; i < rel1.length; i++) {
        int x = rel1[i][0];
        int y = rel1[i][1];

        //Recorre rel2 para encontrar 'z'
        for (int j = 0; j < rel2.length; j++) {
          if (rel2[j][0] == y) {
            int z = rel2[j][1];
            //Añade a composición '(x,z)'
            composicio.add(new int[]{x, z});
            break; 
          }
        }
      }

      //Convertimos la lista a array
      int[][] resultado = new int[composicio.size()][2];
      for (int i = 0; i < composicio.size(); i++) {
        resultado[i] = composicio.get(i);
      }

      return resultado;
    }


    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) {
      //Si es biyectiva, realizamos su inversa y lo devolvemos
      if (esBiyectiva(dom, codom, f)) {
        int[][] graficoInversa = new int[codom.length][2];
        for (int i = 0; i < codom.length; i++) {
          int y = codom[i];
          int x = encontrarPreimagen(y, dom, f);
          graficoInversa[i][0] = y;
          graficoInversa[i][1] = x;
        }
        return graficoInversa;
      } else {
        return null;
      }
    }

    /*
     * Método auxiliar que verifica si una funcion con dominio 'dom' y codominio 'codom'
     * es biyectiva
     */
    static boolean esBiyectiva(int[] dom, int[] codom, Function<Integer, Integer> f) {
      boolean[] codomImagen = new boolean[codom.length];
      // Verificamos que cada x solo tiene un y en el codominio
      for (int x : dom) {
        int y = f.apply(x);
        int idx = encontrarIndice(codom, y);
        if (idx == -1 || codomImagen[idx]) {
          return false; 
        }
        codomImagen[idx] = true;
      }
      
      //Verificamos que todos los elementos tengan una imagen
      for (boolean imagen : codomImagen) {
        if (!imagen) {
          return false;
        }
      }
      return true;
    }

    /*
     * Método auxiliar que encuentra una preimagen de un valor en el codominio de una función
     */
    static int encontrarPreimagen(int y, int[] dom, Function<Integer, Integer> f) {
      for (int x : dom) {
        if (f.apply(x) == y) {
          return x;
        }
      }
      return -1;
    }

    /*
     * Método auxiliar que encuentra el índice de un elemento en una array
     */
    static int encontrarIndice(int[] arr, int elemento) {
      for (int i = 0; i < arr.length; i++) {
        if (arr[i] == elemento) {
          return i;
        }
      }
      return -1;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // |(a u b) × (a \ c)|
       
      assertThat(
          exercici1(
            new int[] { 0, 1, 2 },
            new int[] { 1, 2, 3 },
            new int[] { 0, 3 }
          )
          == 8
      );

      assertThat(
          exercici1(
            new int[] { 0, 1 },
            new int[] { 0 },
            new int[] { 0 }
          )
          == 2
      );
      
      // Exercici 2
      // nombre d'elements de la clausura d'equivalència

      final int[] int08 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(exercici2(int08, generateRel(int08, (x, y) -> y == x + 1)) == 81);

      final int[] int123 = { 1, 2, 3 };

      assertThat(exercici2(int123, new int[][] { {1, 3} }) == 5);

      // Exercici 3
      // Si rel és un ordre total, retornar les arestes del diagrama de Hasse

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(exercici3(int05, generateRel(int05, (x, y) -> x >= y)) == 5);
      assertThat(exercici3(int08, generateRel(int05, (x, y) -> x <= y)) == -2);

      // Exercici 4
      // Composició de grafs de funcions (null si no ho son)
      
       assertThat(
          exercici4(
            int05,
            generateRel(int05, (x, y) -> x*x == y),
            generateRel(int05, (x, y) -> x == y)
          )
          == null
      );


      var ex4test2 = exercici4(
          int05,
          generateRel(int05, (x, y) -> x + y == 5),
          generateRel(int05, (x, y) -> y == (x + 1) % 6)
        );

      assertThat(
          Arrays.deepEquals(
            lexSorted(ex4test2),
            generateRel(int05, (x, y) -> y == (5 - x + 1) % 6)
          )
      );
      
      // Exercici 5
      // trobar l'inversa (null si no existeix)

    assertThat(exercici5(int05, int08, x -> x + 3) == null);

      assertThat(
          Arrays.deepEquals(
            lexSorted(exercici5(int08, int08, x -> 8 - x)),
            generateRel(int08, (x, y) -> y == 8 - x)
          )
      );
     
    }
    
    /**
     * Ordena lexicogràficament un array de 2 dimensions
     * Per exemple:
     *  arr = {{1,0}, {2,2}, {0,1}}
     *  resultat = {{0,1}, {1,0}, {2,2}}
     */
    static int[][] lexSorted(int[][] arr) {
      if (arr == null)
        return null;

      var arr2 = Arrays.copyOf(arr, arr.length);
      Arrays.sort(arr2, Arrays::compare);
      return arr2;
    }

    /**
     * Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
     * Per exemple:
     *   as = {0, 1}
     *   bs = {0, 1, 2}
     *   pred = (a, b) -> a == b
     *   resultat = {{0,0}, {1,1}}
     */
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      var rel = new ArrayList<int[]>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }
      return rel.toArray(new int[][] {});
    }

    /// Especialització de generateRel per a = b
    static int[][] generateRel(int[] as, BiPredicate<Integer, Integer> pred) {
      return generateRel(as, as, pred);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Els (di)grafs vendran donats com llistes d'adjacència (és a dir, tractau-los com diccionaris
   * d'adjacència on l'índex és la clau i els vèrtexos estan numerats de 0 a n-1). Per exemple,
   * podem donar el graf cicle d'ordre 3 com:
   *
   * int[][] c3dict = {
   *   {1, 2}, // veïns de 0
   *   {0, 2}, // veïns de 1
   *   {0, 1}  // veïns de 2
   * };
   *
   * **NOTA: Els exercicis d'aquest tema conten doble**
   */
  static class Tema3 {
    /*
     * Determinau si el graf és connex. Podeu suposar que `g` no és dirigit.
     */
    static boolean exercici1(int[][] g) {
      int n = g.length;
      boolean[] visitats = new boolean[n];
      
      // Mètode de cerca profunda
      dfs(g, 0, visitats);
      
      // Comprovam que tots els nodes s'han vistat
      for (boolean nodeVisitat : visitats) {
        if (!nodeVisitat) {
          return false;
        }
      }
      return true;
    }

    /*
     * Mètode auxiliar que realitza una cerca dfs
     */
    static void dfs(int[][] g, int node, boolean[] visitat) {
      // El node actual l'hem visitat
      visitat[node] = true;
      
      // Recòrrer els nodes connectats
      for (int vei : g[node]) {
        if (!visitat[vei]) {
          // Si no hem visitat el vei, aplicam recursivament dfs
          dfs(g, vei, visitat);
        }
      }
    }

    /*
     * Donat un tauler d'escacs d'amplada `w` i alçada `h`, determinau quin és el mínim nombre de
     * moviments necessaris per moure un cavall de la casella `i` a la casella `j`.
     *
     * Les caselles estan numerades de `0` a `w*h-1` per files. Per exemple, si w=5 i h=2, el tauler
     * és:
     *   0 1 2 3 4
     *   5 6 7 8 9
     *
     * Retornau el nombre mínim de moviments, o -1 si no és possible arribar-hi.
     */
    static int exercici2(int w, int h, int i, int j) {
      // Movimientos posibles del caballo
      int[][] movimientos = {{-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {-1, -2}, {1, -2}};
      
      // Si el punto inicial es el mismo que el final, retornamos 0
      if (i == j) return 0;
      
      // Inicialización de la matriz de distancias
      int[][] distancias = new int[w][h];
      for (int[] fila : distancias) {
        Arrays.fill(fila, -1);
      }
      
      // Coordenadas en (x, y) del punto inicial y final
      int inicioX = i % w;
      int inicioY = i / w;
      int finalX = j % w;
      int finalY = j / w;
      
      // Configurar la distancia inicial
      distancias[inicioX][inicioY] = 0;
      
      // Marcar la celda inicial como visitada
      boolean[][] visitadas = new boolean[w][h];
      visitadas[inicioX][inicioY] = true;
      
      // Procesar todas las celdas posibles
      boolean iterar = true;
      while (iterar) {
        iterar = false;
          
        // Recorrer todas las celdas del tablero
        for (int x = 0; x < w; x++) {
          for (int y = 0; y < h; y++) {
            if (distancias[x][y] != -1) {
              // Realiza el movimiento
              for (int[] move : movimientos) {
                int newX = x + move[0];
                int newY = y + move[1];
                // Comprueba que la casilla a la que hemos ido es válida y no esta visitada
                if (esValid(newX, newY, w, h) && !visitadas[newX][newY]) {
                  int newDistance = distancias[x][y] + 1;
                  if (distancias[newX][newY] == -1 || newDistance < distancias[newX][newY]) {
                    distancias[newX][newY] = newDistance;
                    visitadas[newX][newY] = true;
                    // Indicar que hay que seguir iterando
                    iterar = true; 
                  }
                }
              }
            }
          }
        }
      }
      
      // Retornar la distancia mínima encontrada, o -1 si es inaccesible
      return distancias[finalX][finalY];
    }
  
    // Método auxiliar que verifica que una posición no se encuentra fuera de los límites del tablero 
    static boolean esValid(int x, int y, int w, int h) {
      return x >= 0 && x < w && y >= 0 && y < h;
    }
  
    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
      // Array per emmagatzemar l'ordre de visita en preordre per cada vèrtex
      int[] preorder = new int[g.length];
      // Inicialitzem amb -1 per indicar que encara no ha estat visitat
      Arrays.fill(preorder, -1);
      
      // Índex per assignar l'ordre de visita
      // Utilitzem un array per poder modificar el valor dins del mètode recursiu
      int[] index = {0}; 
      
      // Realitzem el recorregut en preordre començant per l'arrel r
      preOrderTraversal(g, r, preorder, index);
      
      // Comparar les etiquetes de preordre de u i v
      return preorder[u] <= preorder[v];
    }
   
    /*
     * Mètode auxiliar que comprova que és un preordre
     */
    private static void preOrderTraversal(int[][] g, int node, int[] preorder, int[] index) {
      // Assignem l'ordre de visita al vèrtex actual
      preorder[node] = index[0]++;
      
      // Recorrem els fills del vèrtex actual
      for (int vecino : g[node]) {
        // Si el veí encara no ha estat visitat
        if (preorder[vecino] == -1) { 
          preOrderTraversal(g, vecino, preorder, index);
        }
      }
    }

    /*
     * Donat un recorregut en preordre (per exemple, el primer vèrtex que hi apareix és `preord[0]`)
     * i el grau de cada vèrtex (per exemple, el vèrtex `i` té grau `d[i]`), trobau l'altura de
     * l'arbre corresponent.
     *
     * L'altura d'un arbre arrelat és la major distància de l'arrel a les fulles.
     */
    static int exercici4(int[] preord, int[] d) {
      // Cream un objecte arbre
      Arbre arbre = new Arbre();
      // Omplim l'arbre segons el preordre i els graus
      Node arrel = arbre.crearArbre(preord, d);
      // Calculam l'altura de l'arbre
      return arbre.calcularAltura(arrel);
    }

    /*
     * Classe que representa un node
     */
    static class Node {
      int valor;
      List<Node> fills;
  
      public Node(int valor) {
        this.valor = valor;
        this.fills = new ArrayList<>();
      }
  
      public void afegirFill(Node fill) {
        fills.add(fill);
      }
    }

    /*
     * Classe que representa un arbre
     */
    static class Arbre {
      Node arrel;
  
      public Node crearArbre(int[] preord, int[] d) {
        int[] index = {0};
        return construir(preord, d, index);
      }

      /*
       * Mètode recursiu que construeix un arbre
       */
      private Node construir(int[] preord, int[] d, int[] index) {
        if (index[0] >= preord.length) {
            return null;
        }
        Node node = new Node(preord[index[0]]);
        int numFills = d[node.valor];
        index[0]++;
        for (int i = 0; i < numFills; i++) {
            node.afegirFill(construir(preord, d, index));
        }
        return node;
      }

      /*
       * Mètode que calcula l'altura d'un arbre a partir d'un node determinat
       */
      public int calcularAltura(Node node) {
        if (node == null) {
          return -1;
        }
        int alturaMaxima = -1;
        for (Node fill : node.fills) {
          int alturaActual = calcularAltura(fill);
          if (alturaMaxima < alturaActual) {
            alturaMaxima = alturaActual;
          }
        }
        return alturaMaxima + 1;
      }
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // G connex?

      final int[][] B2 = { {}, {} };

      final int[][] C3 = { {1, 2}, {0, 2}, {0, 1} };

      final int[][] C3D = { {1}, {2}, {0} };

      assertThat(exercici1(C3));
      assertThat(!exercici1(B2));

      // Exercici 2
      // Moviments de cavall

      // Tauler 4x3. Moviments de 0 a 11: 3.
      // 0  1   2   3
      // 4  5   6   7
      // 8  9  10  11
      assertThat(exercici2(4, 3, 0, 11) == 3);

      // Tauler 3x2. Moviments de 0 a 2: (impossible).
      // 0 1 2
      // 3 4 5
      assertThat(exercici2(3, 2, 0, 2) == -1);

      // Exercici 3
      // u apareix abans que v al recorregut en preordre (o u=v)

      final int[][] T1 = {
        {1, 2, 3, 4},
        {5},
        {6, 7, 8},
        {},
        {9},
        {},
        {},
        {},
        {},
        {10, 11},
        {},
        {}
      };

      assertThat(exercici3(T1, 0, 5, 3));
      assertThat(!exercici3(T1, 0, 6, 2));

      // Exercici 4
      // Altura de l'arbre donat el recorregut en preordre

      final int[] P1 = { 0, 1, 5, 2, 6, 7, 8, 3, 4, 9, 10, 11 };
      final int[] D1 = { 4, 1, 3, 0, 1, 0, 0, 0, 0, 2,  0,  0 };

      final int[] P2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
      final int[] D2 = { 2, 0, 2, 0, 2, 0, 2, 0, 0 };

      assertThat(exercici4(P1, D1) == 3);
      assertThat(exercici4(P2, D2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * En aquest tema no podeu:
   *  - Utilitzar la força bruta per resoldre equacions: és a dir, provar tots els nombres de 0 a n
   *    fins trobar el que funcioni.
   *  - Utilitzar long, float ni double.
   *
   * Si implementau algun dels exercicis així, tendreu un 0 d'aquell exercici.
   */
  static class Tema4 {
    /*
     * Calculau el mínim comú múltiple de `a` i `b`.
     */
    static int exercici1(int a, int b) {
      int producte = a * b;
      // Si el producte és negatiu
      if (producte < 0) {
        // Canviar el signe
        producte = -producte;
      }
      return producte / gcd(a, b);
    }

    /*
     * Mètode auxiliar recursiu que calcula el MCD
     */
    static int gcd(int a, int b) {
      while (b != 0) {
          int aux = b;
          b = a % b;
          a = aux;
      }
      return a;
    }

    /*
     * Trobau totes les solucions de l'equació
     *
     *   a·x ≡ b (mod n)
     *
     * donant els seus representants entre 0 i n-1.
     *
     * Podeu suposar que `n > 1`. Recordau que no no podeu utilitzar la força bruta.
     */
    static int[] exercici2(int a, int b, int n) {
      int d = mcd_euclides(a, n);
      // Si d no divide a b, no hay solución
      if (b % d != 0) {
          return new int[]{};
      }

      // Simplificar la ecuación dividiendo por d
      a /= d;
      b /= d;
      n /= d;

      // Encontrar una solución particular usando el algoritmo extendido de Euclides
      int[] result = metodo_extendido_euclides(a, n);
      int gcd = result[0];
      int x0 = result[1];

      // Calcular la solución particular
      x0 = (x0 * b) % n;
      if (x0 < 0) {
          x0 += n;
      }

      // Generar todas las soluciones
      int[] solutions = new int[d];
      for (int i = 0; i < d; i++) {
          solutions[i] = (x0 + i * n) % (n * d);
      }

      return solutions;
    }

    // Función para calcular el MCD usando el algoritmo de Euclides
    static int mcd_euclides(int a, int b) {
      while (b != 0) {
          int t = b;
          b = a % b;
          a = t;
      }
      return a;
    }

    // Función para encontrar el inverso modular usando el algoritmo extendido de Euclides
    static int[] metodo_extendido_euclides(int a, int b) {
        if (b == 0) {
            return new int[]{a, 1, 0};
        }
        int[] valores = metodo_extendido_euclides(b, a % b);
        int d = valores[0];
        int x1 = valores[1];
        int y1 = valores[2];
        int x = y1;
        int y = x1 - (a / b) * y1;
        return new int[]{d, x, y};
    }

    /*
     * Donats `a != 0`, `b != 0`, `c`, `d`, `m > 1`, `n > 1`, determinau si el sistema
     *
     *   a·x ≡ c (mod m)
     *   b·x ≡ d (mod n)
     *
     * té solució.
     */
    static boolean exercici3(int a, int b, int c, int d, int m, int n) {
      // Verifica si las congruencias individuales tienen solución
      if (c % gcd(a, m) != 0 || d % gcd(b, n) != 0) {
          return false;
      }
      int[] sol1 = metodo_extendido_euclides(a, m);
      int[] sol2 = metodo_extendido_euclides(b, n);

      int x1 = (c / gcd(a, m)) * sol1[1];
      int x2 = (d / gcd(b, n)) * sol2[1];

      // Verifica la compatibilidad
      return (x1 % gcd(m, n)) == (x2 % gcd(m, n));
    }

    /*
     * Donats `n` un enter, `k > 0` enter, i `p` un nombre primer, retornau el residu de dividir n^k
     * entre p.
     *
     * Alerta perquè és possible que n^k sigui massa gran com per calcular-lo directament.
     * De fet, assegurau-vos de no utilitzar cap valor superior a max{n, p²}.
     *
     * Anau alerta també abusant de la força bruta, la vostra implementació hauria d'executar-se en
     * qüestió de segons independentment de l'entrada.
     */
    static int exercici4(int n, int k, int p) {
      // Si p és 1, qualsevol nombre % 1 és 0.
      if (p == 1) {
        return 0;
      }
      // Assegurem que n és no negatiu en cas que s'entri un valor negatiu.
      if (n < 0) {
        n = (n % p + p) % p;
      }
      // Inicialitzem el resultat
      int result = 1;   
      // Reduïm n amb p per assegurar que és dins el rang [0, p-1]
      n = n % p;        
    
      while (k > 0) {
        // Si k és imparell, multipliquem n amb el resultat actual
        if ((k % 2) == 1) {
          result = (result * n) % p;
        }
        // k es divideix per la meitat
        k = k >> 1;
        // n es multiplica per ell mateix
        n = (n * n) % p;
      }
      return result;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // mcm(a, b)

      assertThat(exercici1(35, 77) == 5*7*11);
      assertThat(exercici1(-8, 12) == 24);

      // Exercici 2
      // Solucions de a·x ≡ b (mod n)

      assertThat(Arrays.equals(exercici2(2, 2, 4), new int[] { 1, 3 }));
      assertThat(Arrays.equals(exercici2(3, 2, 4), new int[] { 2 }));

      // Exercici 3
      // El sistema a·x ≡ c (mod m), b·x ≡ d (mod n) té solució?

      assertThat(exercici3(3, 2, 2, 2, 5, 4));
      assertThat(!exercici3(3, 2, 2, 2, 10, 4));

      // Exercici 4
      // n^k mod p

      assertThat(exercici4(2018, 2018, 5) == 4);
      assertThat(exercici4(-2147483646, 2147483645, 46337) == 7435);
    }
  }

  /**
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  // Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b) throw new AssertionError();
  }
}