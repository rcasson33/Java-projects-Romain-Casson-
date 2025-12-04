public class Median {

    static Pair<Double> median(Singly<Double> data) {
        if (data == null || Singly.length(data) == 0)
            return new Pair<>(Double.NaN, Double.NaN);

        data = MergeSort.sort(data);

        int n = Singly.length(data);

        Singly<Double> courant = data;

        if (n % 2 == 1) {
            
            int mid = n / 2;
            for (int i = 0; i < mid; i++) courant = courant.next;
            return new Pair<>(courant.element, courant.element);
        } else {
            int mid = n / 2 - 1; 
            for (int i = 0; i < mid; i++) courant = courant.next;
            double gauche = courant.element;
            double droite = courant.next.element; 
            return new Pair<>(gauche, droite);
        }
    }
}