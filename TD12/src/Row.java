import java.util.LinkedList;

public class Row {
private final int[] fruits;

    Row() {
        this.fruits = new int[0];
    }
    Row(int[] fruits){
        this.fruits = fruits;
    }
    @Override
    public boolean equals(Object o) {
    // equals prend en argument un objet quelconque o
    // ici on suppose que o est de la classe Row et on le convertit
        Row that = (Row) o;
     if (this.fruits.length != that.fruits.length)
          return false;
      for (int i = 0; i < this.fruits.length; ++i) {
         if (this.fruits[i] != that.fruits[i])
             return false;
         }
    // si on arrive ici, les deux lignes sont de même longueur et les
    // fruits à la même position coïncident : on a donc égalité
        return true;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < fruits.length; ++i)
            s.append(fruits[i]);
        return s.toString();
    }

    Row extendedWith(int fruit){
        int n = this.fruits.length;
        int [] liste = new int [n+1];
        for (int i = 0; i < n; i++){
            liste[i] = this.fruits[i];
        }
        liste[n] = fruit;
        return new Row(liste);
    }

    static LinkedList<Row> allStableRows(int width){
       LinkedList<Row> resultat = new LinkedList<>();
       generation(new Row(), width, resultat);
       return resultat;
    }

    private static void generation(Row courant, int width, LinkedList<Row> resultat){
        if (courant.fruits.length == width){
            if (courant.isStable()){
                resultat.add(courant);
            }
            return;
        }
        int nbFruits = 2; 
        for (int fruit = 0; fruit < nbFruits; fruit++){
            Row suivant = courant.extendedWith(fruit);
            generation(suivant, width, resultat);
        }

    }

    boolean isStable(){
        for (int i = 0; i<this.fruits.length - 2; i++){
            if (fruits[i]==fruits[i+1] && fruits[i]==fruits[i+2]){
                return false;
            }
        }
        return true;
    }

    boolean areStackable(Row r1, Row r2){
        if (this.fruits.length != r1.fruits.length  ||  this.fruits.length != r2.fruits.length){
            return false;
        }
        int width = this.fruits.length;
        for (int i = 0 ; i < width; i++){
            if (this.fruits[i]==r1.fruits[i] && r1.fruits[i]==r2.fruits[i]){
                return false;
            }
        }
        return true; 

    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < fruits.length; ++i) {
            hash = 2 * hash + fruits[i];
        }
        return hash;
    }
}

