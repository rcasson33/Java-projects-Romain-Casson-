import tc.TC;

public class Permutation {

    public static void afficher(int[] t) {
        for (int i = 0; i < t.length - 1; ++i) {
            TC.print(t[i] + " ");
        }
        if (t.length > 0)
            TC.println(t[t.length - 1]);
    }

    public static void afficherListePermutations(int n) {
        int[][] t = listePermutations(n);
        for (int i = 0; i < t.length; ++i) {
            afficher(t[i]);
        }
    }

    public static int[] inserer(int[] t, int i) {
        int n = t.length;
        int [] tab = new int [n+1];
        for (int k = 0; k < i; k++){
            tab[k] = t[k];
        }
        tab[i] = n+1;
        if(i < n){
            for (int k = i+1; k < n+1; k++){
                tab[k] = t[k-1];
            }
        }
        return tab;
    }

    public static int[][] listePermutations(int n) {
       if (n == 0){
            return new int[][]{ new int[0] };
       }
       else{
            int [][] listeAvant = listePermutations(n-1);
            int [][] listeApres = new int[listeAvant.length * n][];
            int compteur = 0;
            for(int k = 0; k < listeAvant.length; k++){    
                int [] tmp = listeAvant[k];
                for (int i = 0; i < tmp.length + 1; i++){
                    listeApres[compteur] = inserer(tmp, i);
                    compteur ++;
                }
            }
            return listeApres;
       }
    }
    //END_EXO exo5a

    // BEGIN_ALWAYS

    public static int[] invPermutation(int[] perm){
        int [] inv = new int [perm.length];
        for (int i = 0; i < perm.length; i++){
            inv[perm[i]-1] = i + 1;
        }
        return inv;
    }

    static String decodeString(String msg, int[]   perm){
        int [] invPerm = invPermutation(perm);
        int t = msg.length();
        int n = perm.length;
        int m = t / n;
        char [][] tab = new char[m][n];
       
        for (int j = 0; j < n; j++){
            int originalColonne = invPerm[j] - 1;
            for (int i = 0; i < m; i++){
                tab[i][originalColonne] = msg.charAt(j * m + i);
            }
        }

        char[] resultat = new char[t];
        int compteur = 0;

        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                resultat[compteur] = tab[i][j];
                compteur++;
            }
        }

        return new String(resultat); 
    }
}
// END_ALWAYS
