public class MergeSort {

    static<E extends Comparable<E>> Singly<E> merge(Singly<E> l1, Singly<E> l2){
        if (l1==null) return l2;
        if (l2==null) return l1;
        
        Singly<E> result;
        if (l1.element.compareTo(l2.element) <= 0){ 
            result =l1;
            l1 = l1.next;
        }
        else {
            result = l2;
            l2 = l2.next;
        }

        Singly<E> last = result;
        while (l1 != null  &&  l2 != null){
            int c = l1.element.compareTo(l2.element);
            if (c <= 0){
                last.next = l1;
                l1 = l1.next;
            }
            else{
                last.next = l2;
                l2 = l2.next;
            }
            last = last.next;
        }
        if (l1==null) last.next = l2;
        else{ 
            last.next = l1;
            }
            return result;
        }
        
    static<E extends Comparable<E>> Singly<E> sort(Singly<E> l){
        if (Singly.length(l) <= 1)  return l;
        Singly<E> l2 = Singly.split(l);
        l = sort(l);
        l2 = sort(l2);
        return merge(l, l2);
    }
}


