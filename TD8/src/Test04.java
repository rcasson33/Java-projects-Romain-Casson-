import tc.TC;

public class Test04 {

    public static void filtre(int seuil) {
        ListeCandidats e = new ListeCandidats();
        e.ajouterFichierEnQueue("candidatsCentreValDeLoire.txt");
        if (!e.estCorrecte()) TC.println("ERREUR: liste incorrecte apres ajout de candidats");
        e.filtrer(seuil);
        if (!e.estCorrecte()) TC.println("ERREUR: liste incorrecte apres filtre des candidats");
        TC.println("Test seuil: " + seuil + ". Il reste " + e.nombreCandidats() + " candidats");
        TC.ecritureEnFinDeFichier("output04.txt");
        TC.println("-- Test seuil: " + seuil);
        e.afficher();
        TC.ecritureSortieStandard();
    }

    public static void main(String[] args) {
      TC.ecritureDansNouveauFichier("output04.txt");
      TC.println("Test differents seuils");
      TC.ecritureSortieStandard();
      filtre(10); // filtre tete
      filtre(15); // filtre queue
      filtre(21); // liste vide
    }

}
