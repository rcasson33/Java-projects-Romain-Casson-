public class Fenwick {
    final Fenwick left;
    final Fenwick right;
    final int lo;
    final int hi;
    int acc;

    Fenwick(int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        if (hi - lo == 1) {
            this.acc = 0;
            this.left = null;
            this.right = null;
        } else {
            int mid = (lo + hi) / 2;
            this.left = new Fenwick(lo, mid);
            this.right = new Fenwick(mid, hi);
            this.acc = this.left.acc + this.right.acc;
        }
    }

    Fenwick get(int i, int lo, int hi) {
        if (i < lo || i >= hi) return null;
        if (hi - lo == 1) return this;
        else{
            int pivot = (lo + hi) / 2;
            if (i < pivot){
                return this.left.get(i, lo, pivot);
            }
            else{
                return this.right.get(i, pivot, hi);
            }
        }
    }

    Fenwick get(int i){
        return get(i, lo, hi);
    }

    void inc(int i) {
        if (i < lo || i >= hi) return;
        if (hi - lo == 1) this.acc = this.acc + 1;
        else{
            int pivot = (lo + hi)/2;
            if (i < pivot){
                this.left.inc(i);
            }
            else{
                this.right.inc(i);
            }
            this.acc = this.left.acc + this.right.acc;
        }
    }

    

    int cumulative(int i) {
        if (i <= lo) return 0;
        if (i >= hi) return this.acc;
        if (hi-lo == 1) return this. acc;
        return this.left.cumulative(i)+this.right.cumulative(i);
    }
}
