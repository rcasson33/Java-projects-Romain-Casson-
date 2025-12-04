import java.util.Scanner;

import tc.TC;

public class TestExercice7a {

  public static void test(String nom) {
    TC.lectureDansFichier(nom + ".txt");
    ABR I = new ABR();
    I.indexerTexte();
    I.dessiner();
    TC.println("hauteur de " + nom + " : " + I.hauteur());
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
