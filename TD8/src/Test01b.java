import tc.TC;

public class Test01b {
  public static void main(String[] args) {
    ListeCandidats e = new ListeCandidats();
    if (!e.estCorrecte()) TC.println("ERREUR: la liste vide devrait etre correcte");
    TC.println("il y a " + e.nombreCandidats() + " candidat");
    Candidat c1 = new Candidat("Auvergne-Rhone-Alpes", "ALLUIRE GERALDINE 15");
    Candidat c2 = new Candidat("Auvergne-Rhone-Alpes", "BERTHELEIN DONIA 7");
    Candidat c3 = new Candidat("Auvergne-Rhone-Alpes", "BERTHELEIN HUGO 17");
    Candidat c4 = new Candidat("Auvergne-Rhone-Alpes", "CENDRAY LUDOVIC 19");
    e.ajouterEnTete(c4);
    if (!e.estCorrecte()) TC.println("ERREUR: la liste a 1 element devrait etre correcte");
    e.afficher();
    TC.println("il y a " + e.nombreCandidats() + " candidat");
    e.ajouterEnTete(c3);
    e.ajouterEnTete(c2);
    e.ajouterEnTete(c1);
    if (!e.estCorrecte()) TC.println("ERREUR: la liste devrait être correcte");
    e.afficher();
    TC.println("il y a " + e.nombreCandidats() + " candidats");
  }
}
