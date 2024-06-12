import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

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

      int[] proposicions = new int[n];
      int contador = 0;
      int temporal;

      for (int i = 0; i < (int) Math.pow(2, n); i++) {

          for (int j = 0; j < n; j++) {
              proposicions[j] = (i >> j) & 1;
          }

          temporal = proposicions[n - 1];

          for (int j = n - 2; j >= 0; j--) {
              if (temporal == 1 && proposicions[j] == 0) {
                  temporal = 0;
              } else {
                  temporal = 1;
              }
          }

          if (temporal != 0) {
              contador++;
          }
      }
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
      for (int var_x : universe) {
          boolean existe_y = false;
          int contador_y = 0;
          for (int var_y : universe) {
              boolean coincide = true;
              for (int var_z : universe) {
                  if (p.test(var_x, var_z) != q.test(var_y, var_z)) {
                      coincide = false;
                      break;
                  }
              }
              if (coincide) {
                  existe_y = true;
                  contador_y++;
              }
          }
          if (existe_y && contador_y == 1)  return true;
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
           
      int union , diferencia ;
      int elementos = 0;


      for (int ele_B : b) {
          if (!esta_dentro(ele_B, a)) {
              elementos++;
          }
      }

      if (a.length > b.length) {
          union = a.length + elementos;
      } else {
          union = b.length + elementos;
      }

      elementos = 0;

      for (int ele_C : c) {
          if (esta_dentro(ele_C, a)) {
                 elementos++;
          }
      }
      diferencia = a.length - elementos;

      return union * diferencia;
  }

  private static boolean esta_dentro(int num, int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == num) { //Si l'array conté el nombre que cercam
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
      if (!esOrdreTotal(a, rel)){
        return -2;
      } else {
        int numArestes = construirHasse(a, rel);
        return numArestes;
      }
     
    }

    static boolean esOrdreTotal(int[] a, int[][] rel){
      // Crear un mapa para las relaciones
      Map<Integer, Set<Integer>> mapRelacio = new HashMap<>();
      for (int x : a) {
          mapRelacio.put(x, new HashSet<>()); //relacionar cada elemento con todos los que esta relacionado
      }
      for (int[] parell : rel) {
          mapRelacio.get(parell[0]).add(parell[1]);
      }
  
      for (int i = 0; i < a.length; i++) {
          for (int j = 0; j < a.length; j++) {
              int x = a[i];
              int y = a[j];
              if (x == y) continue;
              
              // relexivitat
              if (!mapRelacio.get(x).contains(x)) {
              return false;
              }

              // antisimetria
              if (mapRelacio.get(x).contains(y) && mapRelacio.get(y).contains(x) && x != y) {
              return false;
              }

              // transitivitat
              for (int k = 0; k < a.length; k++) {
                  int z = a[k];
                  if (mapRelacio.get(x).contains(y) && mapRelacio.get(y).contains(z) && !mapRelacio.get(x).contains(z)) {
                      return false;
                  }
              }
          }
      }
      return true;
  }

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
      //comprovam si les dues relacions son funcions
      if (!esFuncio(a, rel1) || !esFuncio(a, rel2)) {
        return null;
      } else {
        return composicio(rel1, rel2);
      }
    }

    static boolean esFuncio(int[] a, int[][] rel) {
        Map<Integer, Integer> mapRelacio = new HashMap<>();
        for (int[] parell : rel) {
            int x = parell[0];
            int y = parell[1];
            //comprovam si cada valor té la seva clau
            if (mapRelacio.containsKey(x)) {
                if (mapRelacio.get(x) != y) {
                    return false; //si no té retornam fals
                }
            } else {
                mapRelacio.put(x, y);
            }
        }
        for (int x : a) {
            if (!mapRelacio.containsKey(x)) { //si no té retornam fals
                return false;
            }
        }
        return true;
    }

    static int[][] composicio(int[][] rel1, int[][] rel2) {
        Map<Integer, Integer> mapRelacio1 = new HashMap<>();
        Map<Integer, Integer> mapRelacio2 = new HashMap<>();

        for (int[] par : rel1) {
            mapRelacio1.put(par[0], par[1]);
        }
        for (int[] par : rel2) {
            mapRelacio2.put(par[0], par[1]);
        }

        Set<int[]> composicioSet = new HashSet<>();

        for (int x : mapRelacio1.keySet()) { //troba els parells relacionats i el posa a la composició
            int y = mapRelacio1.get(x);
            if (mapRelacio2.containsKey(y)) {
                int z = mapRelacio2.get(y);
                composicioSet.add(new int[]{x, z});
            }
        }

        //converteix la composició en array
        int[][] composicioArray = new int[composicioSet.size()][2];
        int index = 0;
        for (int[] parell : composicioSet) {
            composicioArray[index++] = parell;
        }

        return composicioArray;
    }

    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) { //HECHO 
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

    static boolean esBiyectiva(int[] dom, int[] codom, Function<Integer, Integer> f) {
      Set<Integer> codomImagen = new HashSet<>();
      for (int x : dom) {
        int y = f.apply(x); 
        if (!contieneElemento(codom, y) || codomImagen.contains(y)) {
            return false; 
        }
        codomImagen.add(y);
      }
      return codomImagen.size() == codom.length;
    }

    static int encontrarPreimagen(int y, int[] dom, Function<Integer, Integer> f) {
        for (int x : dom) {
            if (f.apply(x) == y) {
                return x;
            }
        }
        return -1; 
    }

    static boolean contieneElemento(int[] arr, int elemento) {
      for (int x : arr) {
          if (x == elemento) {
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
      //moviments de los caballos
      int[] movX = {2, 2, -2, -2, 1, 1, -1, -1};
      int[] movY = {1, -1, 1, -1, 2, -2, 2, -2};
      //si inici = final
      if (i == j) return 0;

      boolean[][] visited = new boolean[w][h]; //array visitades
      Queue<int[]> coa = new LinkedList<>(); //coa
      
      //coordenades en (x,y)
      int startX = i % w;
      int startY = i / w;
      int endX = j % w;
      int endY = j / w;
      
      //afegim posInicial a la coa i la marcam com visitada
      coa.add(new int[]{startX, startY, 0});
      visited[startX][startY] = true;
      
      while (!coa.isEmpty()) {
          int[] pos = coa.poll();
          int x = pos[0];
          int y = pos[1];
          int numMoves = pos[2];
          
          //per tots els movimentos posibles comprovam si algun arriba al final
          for (int k = 0; k < 8; k++) {
              int newPosX = x + movX[k];
              int newPosY = y + movY[k];
              
              //si arriba al final retornam el numMoviment
              if (newPosX == endX && newPosY == endY) {
                  return numMoves + 1;
              }
              
              //comprobam si la posició és vàlida i la marcam com a visitada
              if (isValid(newPosX, newPosY, w, h) && !visited[newPosX][newPosY]) {
                  coa.add(new int[]{newPosX, newPosY, numMoves + 1});
                  visited[newPosX][newPosY] = true;
              }
          }
      }
      
      return -1; // No es posible llegar
  }

  //comprova que la posició no es troba fora del limits del tauler
  static boolean isValid(int x, int y, int w, int h) {
      return x >= 0 && x < w && y >= 0 && y < h;
  }
    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
      // Array per emmagatzemar l'ordre de visita en preordre per cada vèrtex
        int[] preorder = new int[g.length];
        Arrays.fill(preorder, -1); // Inicialitzem amb -1 per indicar que encara no ha estat visitat
       
        // Índex per assignar l'ordre de visita
        int[] index = {0}; // Utilitzem un array per poder modificar el valor dins del mètode recursiu
       
        // Realitzem el recorregut en preordre començant per l'arrel r
        preOrderTraversal(g, r, preorder, index);
       
        // Comparar les etiquetes de preordre de u i v
        return preorder[u] <= preorder[v];
    }
   
    private static void preOrderTraversal(int[][] g, int node, int[] preorder, int[] index) {
        // Assignem l'ordre de visita al vèrtex actual
        preorder[node] = index[0]++;
       
        // Recorrem els fills del vèrtex actual
        for (int vecino : g[node]) {
            if (preorder[vecino] == -1) { // Si el veí encara no ha estat visitat
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
      Arbre arbre = new Arbre();
      Node arrel = arbre.crearArbre(preord, d);
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

      // Mètode recursiu que construeix un arbre
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

      // Mètode que calcula l'altura d'un arbre a partir d'un node determinat
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
        if (producte < 0) {
            producte = -producte;
        }
        return producte / gcd(a, b);
    }

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
        int[] sol1 = euclidesExtendido(a, m);
        int[] sol2 = euclidesExtendido(b, n);

        int x1 = (c / gcd(a, m)) * sol1[1];
        int x2 = (d / gcd(b, n)) * sol2[1];

        // Verifica la compatibilidad: x1 ≡ x2 (mod gcd(m, n))
        return (x1 % gcd(m, n)) == (x2 % gcd(m, n));
    }

    private static int[] euclidesExtendido(int a, int b) {
      if (b == 0) {
          return new int[]{a, 1, 0};
      }
      int[] resultado = euclidesExtendido(b, a % b);
      int gcd = resultado[0];
      int x = resultado[2];
      int y = resultado[1] - (a / b) * resultado[2];
      return new int[]{gcd, x, y};
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
    int result = 1; // Inicialitzem el resultat
    n = n % p; // Reduïm n amb p per assegurar que és dins el rang [0, p-1]
    
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
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
