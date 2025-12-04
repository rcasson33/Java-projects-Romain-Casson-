public class TriInsertion{

    public static void trier(Candidat[] t, CandidatComparator comparator) {
        int n = t.length, j;
        Candidat tmp;

        for (int i = 1; i < n; i++){
            j = i;
            while ((j > 0) && (comparator.compare(t[j-1],t[i]) > 0)){
                j--;
            }
            tmp = t[i];
            for(int k = i; k > j; k--){
                t[k] = t[k-1];

            }
            t[j] = tmp;
        }

    }

}
