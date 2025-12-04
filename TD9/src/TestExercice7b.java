import java.util.Scanner;

import tc.TC;

public class TestExercice7b {
    public static void test(String nom) {
        TC.lectureDansFichier(nom + ".txt");
        ListeEntrees l = new ListeEntrees();
        int nligne = 1;
        while (!TC.finEntree()) {
            for (String mot : TC.lireLigne().split("[ .,:;!?()\\[\\]\"]+"))
                l.ajouterEnQueue(new Entree(mot, nligne));
            nligne++;
        }
        ABR balancedIndex = new ABR(l);
        balancedIndex.indexerTexte();
        balancedIndex.dessiner();
        TC.println("hauteur : "
                + balancedIndex.hauteur());
    }

    public static void main7() {
        test("english_short");

        TC.println();
        TC.println("Tapez enter/retour pour continuer...");
        new Scanner(System.in).nextLine();

        test("english");

        TC.println();
        TC.print("Tapez enter/retour pour finir...");
        TC.lectureEntreeStandard();
        TC.lireLigne();
    }

    public static void main(String[] args) {
        main7();
        System.exit(0);
    }
}
