import tc.TC;

public class Maillon {
    public final Candidat contenu;
    private Maillon suivant;

    public Maillon(Candidat c) {
        this.contenu = c;
        this.suivant = null;
    }

    public Maillon(Candidat c, Maillon reste) {
        this.contenu = c;
        this.suivant = reste;
    }

    public static void afficher(Maillon m) {
        Maillon courant = m;
        while (courant != null) {
            TC.println(courant.contenu);
            courant = courant.suivant;
        }
    }

    public Maillon suivant() {
        return this.suivant;
    }

    public void setSuivant(Maillon s) {
        this.suivant = s;
    }

    public static Maillon dernier(Maillon m){
        if (m==null){
            return null;
        }
        if (m.suivant == null) {
            return m;
        }
        else{
            return(dernier(m.suivant));
        }
    }

    public static int longueur(Maillon m){
        if (m == null){
            return 0;
        }
        else{
            return(1 + longueur(m.suivant));
        }
    }

    public Maillon ajouterApres(Candidat c){
        Maillon m = new Maillon(c, this.suivant);
        this.suivant = m;
        return(m);
    }

    public static void enleverSuivant(Maillon m){
        if (m==null){
            return;
        }
        if (m.suivant== null){
            return;
        }
        else{
            m.suivant = m.suivant.suivant;
        }
    }

}
//END_ALWAYS
