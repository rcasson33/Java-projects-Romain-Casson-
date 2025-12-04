public class TriSelection{

    public static void trier(Candidat[] t, CandidatComparator comparator) {
        int n  = t.length, min; 
        

        for (int i = 0; i < n; i++){
            min = indm(comparator, t, i);
            swap(t, i, min);
        }
    }

    public static int indm(CandidatComparator comparator, Candidat[] t, int i){
        int min = i;

        for (int j = i+1; j < t.length; j++){
            if (comparator.compare(t[j],t[min]) < 0){
                min = j;
            }
        }
        return min;

    }

    public static void swap(Candidat[] t, int i, int j){
        Candidat tmp = t[i];
        t[i] = t[j];
        t[j] = tmp;
    }
	
}
