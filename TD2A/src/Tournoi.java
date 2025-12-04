import tc.TC;

public class Tournoi {
  // utile pour compter le nombre d'échanges en phase de test
  // ne pas supprimer svp
  
  public static void afficheTour(int[] tab){
    // affiche de manière adéquate les tours à partir d'un tableau initial
    int n = tab.length;
    String resultat = "";
    if (n>=2)
        resultat = resultat + "["  + tab[0] + " - " + tab[1] + "]";
    for (int i = 2; i < n; i= i+2)
        resultat = resultat + " ["  + tab[i] + " - " + tab[i+1] + "]";
    TC.println(resultat);
}

public static int[] tableauOrdonne(int n){
    // envoie un tableau ordonnee contenant les n premiers entiers (1 à n)
    int[] tab = new int[n];
    for(int i = 0; i < n; i++)
        tab[i] = i + 1;
    return tab;
}

  public static int echangeCounter = 0;
  public static void echange(int[] tab, int i, int j){ 
    // échange la position de deux éléments du tableau
    // nécessite le tableau en argument sinon travail inutile sur des variables locales
    int tmp = tab[i];
    tab[i] = tab[j];
    tab [j] = tmp; 
    ++Tournoi.echangeCounter;
  }

  public static void melangeTableau(int[] tab){
    // melange aléatoirement un tableau
    for(int i = tab.length-1; i > 0; i--){
      int j = (int)(Math.random()*(i+1));
      Tournoi.echange(tab, j, i);
    }
  }


        public static boolean estUnePermutation(int[] tab) {
            // teste si le tableau est une permutation
            // limite la complexité : en grand O de n
            int n = tab.length;
            int[] compteur = new int[n];
            for (int i=0; i < n; i++){
                if (tab[i] > n || tab[i] <= 0)
                    return false;
            compteur[tab[i]-1] += 1;} // les éléments de tab sont dans (1,n)
            for(int i = 0; i<n; i++)
                if(compteur[i] != 1) 
                    return false;
            return true;
        }

    public static int[] tourSuivant(int[] tab, char[] result) {
        // renvoie le tableau du tour suivant
        int n = result.length;
        int[] nouvtab = new int[n];
            for (int i = 0; i<n; i++){
            if (result[i] == 'G'){
                nouvtab[i]=tab[2*i];
            }
            else {
                nouvtab[i]=tab[2*i+1];
            }
        }
        return nouvtab;    
    }
    
    public static int vainqueur(int[] tab, char[][] result) {
        // calcule et renvoie tous les tours du tournoi jusqu'à la gagnante
        int n = result.length;
        for (int i = 0; i<n; i++){
            afficheTour(tab);
            tab = tourSuivant(tab, result[i]);
        }
        return(tab[0]);

      }
    
    public static int adversaire(int j, int n) {
        // détermine l'adversaire déterministe du joueur i
        int i = n + 1 - j;
        return i;
    }
    
    public static int[] tourPrecedent(int[] tab) {
        // renvoie un tableau déterministe de taille 2n à partir d'un tableau deterministe de taille n
        int n = tab.length;
        int [] suite = new int [2*n];
        for (int i=0; i<n; i++){
            suite[2*i] = tab[i];
            suite[2*i +1] = adversaire(suite[2*i], 2*n);
        }
        return suite;
        

    }
    
    public static int[] tableauDeterministe(int n) {
        // renvoie un tableau déterministe de taille n
        int compteur = 1;
        int [] tab = new int [1];
        tab [0] = 1;
        while (compteur < n){
            tab = tourPrecedent(tab);
            compteur = 2*compteur;
        }
        return tab;
    }
    public static char resultatMatch(int i, int j) {
        // determine en fonction du classement si la joueuse i gagne ou perd
        double aleatoire = Math.random();
        if (aleatoire < (double) j / (i + j)) {
            return 'G'; 
        } else {
            return 'P'; 
        }
    }
}

    

