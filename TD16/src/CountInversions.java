import java.util.Arrays;;

public class CountInversions {

    static int countInversionsNaive(int[] a) {
        int compteur = 0;
        int n = a.length;
        for (int i = 0; i < n; i++){
            for(int j = i; j < n; j++){
                if( a[i] > a[j]){
                    compteur += 1;
                }
            }
        }
        return compteur;
    }

    static int countInversionsFen(int[] a) {
        int n = a.length;
        int compteur = 0;
        if (n==0) return 0;
        
        int min = a[0], max = a[0];
        for (int i = 1; i < n; i ++){
            int tmp = a[i];
            if (tmp > max) max = tmp;
            if (tmp < min) min = tmp;
        }

        Fenwick fen = new Fenwick(min, max+1);
        for (int i=n-1; i >= 0; i --){
            compteur += fen.cumulative(a[i]);
            fen.inc(a[i]);
        }

        return compteur;
    }

    static int countInversionsBest(int[] a){
        int n = a.length;
        int[] aCopy = Arrays.copyOf(a, n);
        Arrays.sort(aCopy);
        for (int i=0; i < n; i++){
            a[i] = Arrays.binarySearch(aCopy, a[i]);
        }
        return countInversionsFen(a);

    }
}
