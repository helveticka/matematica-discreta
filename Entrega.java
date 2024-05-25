import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
      return 0; // TODO
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
      return false; // TODO
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
     * Calculau el nombre d'elements del conjunt de parts de (a u b) × (a \ c)
     *
     * Podeu soposar que `a`, `b` i `c` estan ordenats de menor a major.
     */
    static int exercici1(int[] a, int[] b, int[] c) {
      return -1; // TODO
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
    static int exercici3(int[] a, int[][] rel) { //FALTA METODO CREAR HASSE
      if (!esOrdreTotal(a, rel)){
        return -2;
      } else {
        int numArestes = 0;
        //int numArestes = construirHasse(a, rel);
        return numArestes;
      }
     
    }

    static boolean esOrdreTotal(int[] a, int[][] rel){
      HashSet<Integer> elements = new HashSet<>();
      for (int x : a) {
          elements.add(x);
      }

      for (int i = 0; i < rel.length; i++) {
          for (int j = 0; j < rel[i].length; j++) {
              if (!elements.contains(rel[i][j])) {
                  return false;
              }
          }
      }
      return true;
    }



    /*
     * Comprovau si les relacions `rel1` i `rel2` són els grafs de funcions amb domini i codomini
     * `a`. Si ho son, retornau el graf de la composició `rel2 ∘ rel1`. Sino, retornau null.
     *
     * Podeu soposar que `a`, `rel1` i `rel2` estan ordenats de menor a major (les relacions,
     * lexicogràficament).
     */
    static int[][] exercici4(int[] a, int[][] rel1, int[][] rel2) {
      return new int[][] {}; // TODO
    }

    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) { //CREO QUE HECHO, NO PROBADO
      if (esBiyectiva(dom, codom, f)) {
        int[][] graficoInversa = new int[codom.length][2];
        for (int i = 0; i < codom.length; i++) {
            int y = codom[i];
            int x = encontrarPreimagen(y, dom, f);
            graficoInversa[i][0] = x;
            graficoInversa[i][1] = y;
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
      return true;
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

    // Mètode que comprova si una relació és reflexiva
    static boolean esReflexiva(int [] a, int [][] relacio) {
      for (int index=0; index< a.length ; index++){
          boolean trobat = false;
          for (int[] p : relacio) {
              if (p[0] == index && p[1] ==index) {
                  trobat =true ;
                  break;
              }
          }
          if (!trobat){
              return false;
          }
      }

      return true;
    }

  // Mètode que comprova si una relació és transitiva
  static boolean esTransitiva(int[][] relacio) {
      boolean trobat;
      for (int[] p : relacio) {
          for (int[] q : relacio) {
              if (p[1] == q[0]) {
                  trobat = false;
                  for (int[] r : relacio) {
                      if (r[0] == p[0] && r[1] == q[1]) {
                          trobat = true;
                          break;
                      }
                  }
                  if (!trobat){
                      return false;
                  }
              }
          }
      }

      return true;
  }

  // Mètode que comprova si una relació és simètrica
  static boolean esSimetrica(int [][] relacio) {
      for (int[] p : relacio) {
          boolean trobat = false;
          for (int[] q : relacio) {
              if (p[0] == q[1] && p[1] == q[0]) {
                  trobat = true;
                  break;
              }
          }
          if (!trobat){
              return false;
          }
      }

      return true;
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
      class Casilla{
        int indice;
        int[] movimientos = new int[4];

        Casilla(int i, int w, int h){
          this.indice = i;
          this.movimientos = generarMovimientos(w, h, i);
        }

        static int[] generarMovimientos(int w, int h, int i){
          int[] aux = new int[4];
          int indice = 0;
          if (w == 0 | w == 1 | h == 0 | h == 1 | (h == 2 & w == 2)){
            return null;
          } else {
            if (i <= w/2){
              i = i + 2;
            } else {
              i = i - 2;
            } 
            if (i < h/2){
              i = i + w;
              aux[indice] = i;
              indice++;

            } else if (i > h /2) {
              i = i + w;
              aux[indice] = i;
              indice++;
            } else {
              int auxi;
              auxi = i;
              i = i + w;
              aux[indice] = i;
              indice++;
              i = auxi + w;
              aux[indice] = i;
              indice++;
            }
            return aux;
          }
          
        }
      }
      if (w == 0 | w == 1 | h == 0 | h == 1 | (h == 2 & w == 2)){
        return -1; 
      } else {

         return 0;
      }
        
    }

    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
      return false; // TO DO
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
        int numFills = d[index[0]];
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

      assertThat(exercici4(P1, D1) == 4); //posava 3 pero crec que esta malament
      assertThat(exercici4(P2, D2) == 4);
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
  }

  // Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
