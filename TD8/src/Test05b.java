import tc.TC;

public class Test05b {
    public static ListeCandidats cloneListe(ListeCandidats l) {
      ListeCandidats r = new ListeCandidats();
      Maillon m = l.tete();
      while(m != null) {
        r.ajouterEnQueue(m.contenu);
        m = m.suivant();
      }
      return r;
    }

   public static boolean compareListe(ListeCandidats l1, ListeCandidats l2) {
     Maillon m1 = l1.tete();
     Maillon m2 = l2.tete();
     while (m1 != null && m2 != null) {
       if (m1.contenu != m2.contenu) return false;
       m1 = m1.suivant();
       m2 = m2.suivant();
     }
     return m1 == null && m2 == null;
   }

    public static void main(String[] args) {
        ListeCandidats e = new ListeCandidats();
        e.ajouterFichierEnQueue("candidatsCorse.txt");
        if (!e.estCorrecte()) TC.println("ERREUR: liste incorrecte apres ajout de candidats");
        ListeCandidats orig = cloneListe(e);
        ListeCandidats r = e.selection();
        if (!r.estCorrecte()) TC.println("ERREUR: liste des candidats selectionnes incorrecte");
        if (!compareListe(orig,e))
          TC.println("ERREUR: liste originale modifiee par selection()");
        TC.println("Meilleurs Candidats: " + r.nombreCandidats());
        TC.ecritureDansNouveauFichier("output05b.txt");
        r.afficher();
    }

}
