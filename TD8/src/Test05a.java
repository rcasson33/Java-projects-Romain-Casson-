import tc.TC;

public class Test05a {
    public static void test(Candidat c1, Candidat c2, int expected) {
        TC.println("Test de comparaison: ");
        TC.println("Candidat 1: " + c1 + " (" + c1.getRegion() + ")");
        TC.println("Candidat 2: " + c2 + " (" + c2.getRegion() + ")");
        int res = c1.ordreNote(c2);
        boolean ok = Integer.signum(res) == expected;
        if (ok) TC.println("OK");
        else TC.println("ERREUR");
    }
    
    public static void main(String[] args) {
            Candidat c1 = new Candidat("Corse", "RAULETTE CAMILLE 13");
            Candidat c2 = new Candidat("Corse", "ELISABETHE LUDOVIC 13");
            Candidat c3 = new Candidat("Corse", "ALENS CLARA 17");
            Candidat c4 = new Candidat(new String("Ile-de-France"), "TIETSCHY SALMA 13");
            test(c1,c1,0);
            test(c1,c2,0);
            test(c1,c3,-1);
            test(c1,c4,1);
            test(c2,c4,1);
            test(c4,c2,-1);
        }
}
