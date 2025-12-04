import tc.TC;

public class BoucleBissextile {

    public static boolean estBissextile(int annee) {
        return annee % 4 == 0 && annee % 100 != 0 || annee % 400 == 0;
    }

    public static void affichage(int annee) {
        Bissextile.affichage(annee);
    }


    public static int lireannee(String invite) {
        int annee = 0;
        TC.print(invite);
        annee = TC.lireInt();
        return annee;
    }
    
    public static void main(String[] args) {
        int annee = lireannee("Entrer une valeur : ");
        while ( annee >= 0) {
            affichage(annee);
            annee = lireannee("Entrer une valeur : ");
        }
        TC.println("Au revoir.");
    }

}
