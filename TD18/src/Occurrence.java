

public class Occurrence implements Comparable<Occurrence>{
    String word;
    int count;


    public Occurrence(String word, int count){
        this.word = word;
        this.count = count;
    }

    static Singly<Occurrence> count(Singly<String> l){
        l = MergeSortString.sort(l);
        if (l==null) return null;
        Singly<Occurrence> result = null;
        Singly<Occurrence> last = null;
        String actuel = l.element;
        int countactuel = 1;
        l = l.next; 

        while (l != null){
            if (l.element.equals(actuel)) countactuel++;
            else{
                Singly <Occurrence> node = new Singly<>(new Occurrence(actuel, countactuel), null);
                if (result == null){
                    result = node;
                    last = node;
                }
                else{
                    last.next = node;
                    last = last.next;
                }
                actuel = l.element;
                countactuel = 1;
            }
            l = l.next;
            }
        Singly<Occurrence> node = new Singly<>(new Occurrence(actuel, countactuel), null);
        if (result == null) {
            result = node;
        } else {
            last.next = node;
        }

        return result;
    }

    @Override
    public int compareTo(Occurrence that){
        if (this.count != that.count) {
            return that.count - this.count;
        }
        return this.word.compareTo(that.word);
    
    }

    static Singly<Occurrence> sortedCount(Singly<String> l) {
        Singly<Occurrence> comptage = count(l); 
        if (comptage == null) return null;
        comptage = MergeSort.sort(comptage); 
        return comptage;
    }

}
