import tc.TC;

public class Bissextile {
    
    public static boolean estBissextile(int annee) {
        return annee % 4 == 0 && annee % 100 != 0 || annee % 400 == 0;
    }

    public static void affichage(int annee) {
        if ( (boolean) estBissextile(annee) ) {
            TC.println("L'annee "+ annee +" est bissextile.");
        }
        else {
            TC.println("L'annee "+ annee +" n'est pas bissextile.");
        }
        }

    


    public static void main(String[] args) {
        affichage(1900);
        affichage(1901);
        affichage(1904);
        affichage(2000);
        affichage(2024);
    }

}
