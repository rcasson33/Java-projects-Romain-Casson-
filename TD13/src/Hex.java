/* Jeu de Hex
   https://fr.wikipedia.org/wiki/Hex

   grille n*n

   cases jouables : (i,j) avec 1 <= i, j <= n

   bords bleus (gauche et droite) : i=0 ou i=n+1, 1 <= j <= n
   bords rouges (haut et bas) : 1 <= i <= n, j=0 ou j=n+1

   note : les quatre coins n'ont pas de couleur

   adjacence :      i,j-1   i+1,j-1

                 i-1,j    i,j   i+1,j

                    i-1,j+1    i,j+1

*/

public class Hex implements Cloneable {

  private final Player[][] grid;
  private final int n;
  int nbcoups;
  private final int[]link; 
  private final int[]rank;
  private int compteuraleatoire = 0;
  private final java.util.Vector<Integer> tableaualeatoire = new java.util.Vector<Integer>();
  
  int find(int i){ 
    int p = this.link[i]; 
    if (p==i) return i; 
    int r = this.find(p); 
    this.link[i] = r;
    return r; 
  }

  void union(int i, int j) { 
    int ri = this.find(i); 
    int rj = this.find(j); 
    if (ri == rj) return;
    if (this.rank[ri] < this.rank[rj]) this.link[ri] = rj; 
    else { 
      this.link[rj] = ri; 
      if (this.rank[ri] == this.rank[rj]) this.rank[ri]++; } 
    }

  enum Player {
    NOONE, BLUE, RED
  }

  // crée un plateau vide de taille n*n
  Hex(int n) {
    this.n = n;
    
    this.grid = new Player[n + 2][n + 2];
    for (int i = 0; i < n + 2; i++) {
      for (int j = 0; j < n + 2; j++) {
        grid[i][j] = Player.NOONE;
      }
    }

    for (int i = 1; i < n + 1; i++) {
      grid[i][0] = Player.RED;
      grid[i][n + 1] = Player.RED;
    }

    for (int j = 1; j < n + 1; j++) {
      grid[0][j] = Player.BLUE;
      grid[n + 1][j] = Player.BLUE;
    }
    
    int size = (n+2)*(n+2);
    this.link = new int[size];
    this.rank = new int[size];
    for (int i=0; i < size; i++){
      link[i] = i;
      rank[i] = i;
    } 
    for (int i1 = 1; i1 <= n; i1++) {
        for (int i2 = i1+1; i2 <= n; i2++) {
            union(i1 , i2);       // haut
            union(i1 + (n + 2) * (n+1), i2 + (n + 2) * (n+1)); // bas
        }
    }

    for (int j1 = 1; j1 <= n; j1++) {
        for (int j2 = j1+1; j2 <= n; j2++) {
            union(0 + (n + 2) * j1, 0 + (n + 2) * j2);         // gauche
            union((n+1) + (n + 2) * j1, (n+1) + (n + 2) * j2); // droite
        }
    }

    for (int k = 0; k < (n * n); k++)
      tableaualeatoire.add(k);
    java.util.Collections.shuffle(tableaualeatoire);

  }

  // renvoie la couleur de la case i,j
  Player get(int i, int j) {
    if (i < 0 || i > n + 1 || j < 0 || j > n + 1)
      throw new IndexOutOfBoundsException();
    return grid[i][j];
  }

  // Met à jour le plateau après que le joueur avec le trait joue la case (i, j).
  // Ne fait rien si le coup est illégal.
  // Renvoie true si et seulement si le coup est légal.
  boolean click(int i, int j) {
    if (i < 1 || i > n || j < 1 || j > n)
      return false;
    if (this.grid[i][j] != Player.NOONE)
      return false;
    else {
      Player joueur = currentPlayer(); 
      this.grid[i][j] = joueur;
      this.nbcoups++;
      int index = label(i,j);
      int [][] connexes = { {i-1, j}, {i-1, j+1}, {i, j+1}, {i+1, j}, {i+1, j-1}, {i, j-1}};
      for (int [] voisin : connexes){
            int ni = voisin[0];
            int nj = voisin[1];
            if (ni >= 0 && nj >= 0 && ni <= n+1 && nj<= n+1){
              if (this.grid[ni][nj] == joueur){
                union(index, label(ni, nj));
              }
            }
      }
      return true;
    }
  }

  // Renvoie le joueur avec le trait ou Player.NOONE si le jeu est terminé par
  // la victoire d'un joueur.
  Player currentPlayer() {
    if (winner() != Player.NOONE)
      return Player.NOONE;
    else {
      if (this.nbcoups % 2 == 0) {
        return Player.BLUE;
      } else {
        return Player.RED;
      }
    }
  }


  // Renvoie le joueur gagnant, ou Player.NOONE si aucun joueur n'est encore
  // gagnant

  Player winner() {
    if (find(0, 1) == find(n + 1, 1)) {
      return Player.BLUE;
    } else if (find(1, 0) == find(1, n + 1)) {
      return Player.RED;
    } else {
      return Player.NOONE;
    }
  }

    int position(int i, int j) {
      return i + (n + 2) * j;
    }

    int find(int i, int j) {
      return find(position(i, j));
    }


  int label(int i, int j) {
      return find(i, j);
  }

  // Joue un coup aléatoire pour le joueur ayant le trait, met à jour l'état
  // du jeu comme un clic sur une case, et renvoie true si un coup a été joué
  // (false si la partie est terminée ou s'il n'existe plus de coup légal).
  boolean randomMove() {
    if (currentPlayer() == Player.NOONE){
      return false;
    }

    if (compteuraleatoire > (n*n) ){
      return false;
    }

    int a = tableaualeatoire.get(compteuraleatoire);
    compteuraleatoire++;
    int i = a / (n+1);
    int j = a % (n+1);

    if (grid[i][j] == Player.NOONE){
      System.out.printf("joue aléatoire (%d, %d)...\n", i, j);
      
      if (click(i, j)){
        System.out.println("OK");
      }
      else{
        System.out.println("Pas possible");
      } 
    }

    return true; 
  }

  // Joue un coup pour le joueur ayant le trait en s'appuyant sur une
  // simulation heuristique (playout) pour choisir ce coup. Met à jour l'état
  // du jeu comme un clic et renvoie true si un coup a été joué (false sinon).
  boolean heuristicMove() {
    return false;
  }

  public static void main(String[] args) {
    HexGUI.createAndShowGUI();
  }
}
