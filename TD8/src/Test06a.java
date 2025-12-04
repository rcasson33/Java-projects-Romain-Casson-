import tc.TC;

public class Test06a {
    public static void main(String[] args) {
        ListeCandidats e = new ListeCandidats();
        // CollectionCandidats e = new LinkedListCandidats();
        e.ajouterFichierTrie("candidatsGrandEst.txt");
        if (!e.estCorrecte()) TC.println("ERREUR: liste incorrecte apres ajout fichier 1");
        e.ajouterFichierTrie("candidatsHautsDeFrance.txt");
        if (!e.estCorrecte()) TC.println("ERREUR: liste incorrecte apres ajout fichier 2");
        TC.ecritureDansNouveauFichier("output06a.txt");
        e.afficher();
    }

}
