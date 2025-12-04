import tc.TC;

public class Test02a {
    public static void main(String[] args) {
        Candidat c1 = new Candidat("Auvergne-Rhone-Alpes", "ALLUIRE GERALDINE 15");
        Candidat c2 = new Candidat("Auvergne-Rhone-Alpes", "BERTHELEIN DONIA 7");
        Candidat c3 = new Candidat("Auvergne-Rhone-Alpes", "BERTHELEIN HUGO 17");
        Candidat c4 = new Candidat("Auvergne-Rhone-Alpes", "CENDRAY LUDOVIC 19");
        Maillon m1 = new Maillon(c1);
        Maillon m3 = m1.ajouterApres(c3);
        m3.ajouterApres(c4);
        m1.ajouterApres(c2);
        Maillon.enleverSuivant(m1);
        Maillon.afficher(m1);
        TC.println("Il y a " + Maillon.longueur(m1) + " candidats");
        Maillon d = Maillon.dernier(m1);
        TC.println("Le dernier maillon est:");
        Maillon.afficher(d);
    }

}
