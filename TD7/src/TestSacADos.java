import tc.TC;

public class TestSacADos {

    public static void test1() {
        Objet[] objets = new Objet[] {
                new Objet("un gros caillou", 4000, 2),
                new Objet("un parchemin de lune", 20, 300),
                new Objet("un bouclier", 8000, 600),
                new Objet("une potion de vitesse", 600, 500),
                new Objet("une pièce d'or", 84, 200),
                new Objet("une grosse pièce d'or", 210, 500),
                new Objet("un objet mystérieux", 9970, 2880),
                new Objet("une Twingo", 1140000, 1000000),
                new Objet("une cape d'invisibilité", 500, 800),
                new Objet("une potion de force", 600, 500),
        };

        int[] tailles = { 1000, 2000, 5000, 10000 };
        for (int i = 0; i < tailles.length; i++) {
            TC.println("=== " + tailles[i] + " g ===");
            //TC.println(ResolutionExacte.remplirSac(objets, tailles[i]));
            TC.println(ResolutionApprochee.remplirSac(objets, tailles[i]));
        }
    }

    public static void test2(int n, long graine) {
        Objet.RANDOM.setSeed(graine);
        TC.println("== Inventaire (graine = " + graine + ") ===");
        Objet[] objets = new Objet[n];
        for (int i = 0; i < n; i++) {
            Objet obj = Objet.random();
            TC.print(String.format("%s (%d g, %d sous)\n",
                    obj, obj.poids(), obj.valeur()));
            objets[i] = obj;
        }
        TC.println("== Résultat ==");
        TC.print(ResolutionExacte.remplirSac(objets, 2000));
        // TC.print(ResolutionApprochee.remplirSac(objets, 2000));
    }

    public static void main(String[] args) {
        test1();
        // test2(10, 1);
    }

}
