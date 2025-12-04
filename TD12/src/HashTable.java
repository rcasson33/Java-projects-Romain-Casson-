import java.util.LinkedList;
import java.util.ArrayList;

public class HashTable {
    static final int M = 50000;
    ArrayList<LinkedList<Quadruple>> buckets;
    HashTable(){
        buckets = new ArrayList<>(M);
        for (int i = 0; i < M; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    static int hashCode(Row r1, Row r2, int height){
        return ( ( (r1.hashCode() * 31) + (r2.hashCode()*7) + (height * 3) ) );
    }

    static int bucket(Row r1, Row r2, int height){
        int h = hashCode(r1, r2, height) % M;
        if (h<0){
            h += M;
        }
        return h;
    }

    void add(Row r1, Row r2, int height, Long result){
        int h = bucket(r1, r2, height);
        this.buckets.get(h).add(new Quadruple(r1, r2, height, result));
    }

    Long find(Row r1, Row r2, int height){
        int h = bucket(r1, r2, height);
        LinkedList<Quadruple> seau = this.buckets.get(h);
        for (Quadruple q : seau){
            if (q.r1.equals(r1) && q.r2.equals(r2) && q.height==height){
                return q.result;
            }
        }
        return null;
    }
}
