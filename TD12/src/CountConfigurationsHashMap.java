import java.util.HashMap;
import java.util.LinkedList;

public class CountConfigurationsHashMap {

    static long count(Row r1, Row r2, LinkedList<Row> rows, int height, HashMap<Triple, Long> table){
        
        if (height <= 1){
            return (long) 0;
        }
        if (height == 2){
            return (long) 1;
        }

        Triple courant = new Triple(r1, r2, height);

        if (table.containsKey(courant)) { return table.get(courant);}


        long compteur = 0;

        for (Row r3 : rows){
            if (r3.areStackable(r1, r2)){

                compteur += count(r2, r3, rows, height -1, table);
            }
        }
        table.put(courant, compteur);

        return compteur;
    }

        static long count(int n){
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 2;
        }
        long compteur = 0;
        LinkedList<Row> rows = Row.allStableRows(n);
        HashMap<Triple, Long> table = new HashMap<>();
        
        for(Row r1 :rows){
            for (Row r2 : rows){
                compteur += count(r1, r2, rows, n, table);
            }
        }
        return compteur;
    }

}
