public class Triple {
    Row r1;
    Row r2;
    int height;

    Triple(Row r1, Row r2, int height){
        this.r1 = r1;
        this.r2 = r2;
        this.height = height;
    }

    @Override
    public boolean equals(Object o){
        Triple that = (Triple) o;
        if (this.r1.equals(that.r1) && this.r2.equals(that.r2) && this.height == that.height){
            return true;
        }
        return false;
    }

    public int hashCode(){
        return HashTable.hashCode(r1, r2, height);
    }


}
