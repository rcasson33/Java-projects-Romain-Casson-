import tc.TC;

public class Test06b {
    public static void main(String[] args) {
        ListeCandidats e = new ListeCandidats();
        e.ajouterFichierTrie("candidatsAuvergneRhoneAlpes.txt");
        e.ajouterFichierTrie("candidatsBourgogneFrancheComte.txt");
        e.ajouterFichierTrie("candidatsBretagne.txt");
        e.ajouterFichierTrie("candidatsCentreValDeLoire.txt");
        e.ajouterFichierTrie("candidatsCorse.txt");
        e.ajouterFichierTrie("candidatsGrandEst.txt");
        e.ajouterFichierTrie("candidatsHautsDeFrance.txt");
        e.ajouterFichierTrie("candidatsIleDeFrance.txt");
        e.ajouterFichierTrie("candidatsNormandie.txt");
        e.ajouterFichierTrie("candidatsNouvelleAquitaine.txt");
        e.ajouterFichierTrie("candidatsOccitanie.txt");
        e.ajouterFichierTrie("candidatsPaysDeLaLoire.txt");
        e.ajouterFichierTrie("candidatsProvenceAlpesCoteDAzur.txt");
        if (!e.estCorrecte()) TC.println("ERREUR: liste incorrecte apres ajout de tous les fichiers");
        e.filtrer(12);
        if (!e.estCorrecte()) TC.println("ERREUR: liste incorrecte apres desistements");
        ListeCandidats r = e.selection();
        if (!r.estCorrecte()) TC.println("ERREUR: liste incorrecte apres selection");
        TC.println("Il reste " + r.nombreCandidats() + " candidat(s)");
        r.afficher();
    }

}
