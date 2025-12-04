import tc.TC;

public class Essai3{
    public static void main(String[] args){
        CandidatComparatorNote comp = new CandidatComparatorNote();
        Candidat c1 = new Candidat("STIGNY MARIE 16 127S");
        Candidat c2 = new Candidat("PAPIELLER MARIE 11 120P");
        TC.println("c1="+c1);
        TC.println("c2="+c2);
        TC.println(comp.compare(c1, c2));
    }
}
