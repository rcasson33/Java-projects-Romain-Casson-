import tc.TC;

public class Test02b {
    public static void main(String[] args) {
        ListeCandidats e = new ListeCandidats();
        Candidat c1 = new Candidat("Auvergne-Rhone-Alpes", "BERTHELEIN DONIA 7");
        Candidat c2 = new Candidat("Auvergne-Rhone-Alpes", "BERTHELEIN HUGO 17");
        Candidat c3 = new Candidat("Auvergne-Rhone-Alpes", "CENDRAY LUDOVIC 19");
        Candidat c4 = new Candidat("Bourgogne-Franche-Comte", "ALLUIRE GERALDINE 15");
        e.ajouterEnTete(c1);
        e.ajouterEnQueue(c2);
        if (!e.estCorrecte()) {
            TC.println("ERREUR: ajouterEnQueue 1 candidat rend la liste incorrecte");
            return;
        }
        e.ajouterEnQueue(c3);
        if (!e.estCorrecte()) {
            TC.println("ERREUR: ajouterEnQueue 2 candidats rend la liste incorrecte");
            return;
        }    
        e.ajouterEnTete(c4);
        if (!e.estCorrecte()) {
            TC.println("ERREUR: ajouterEnTete rend la liste incorrecte");
            return;
        }
        e.afficher();
        TC.println("Il y a " + e.nombreCandidats() + " candidats");
    }

}
