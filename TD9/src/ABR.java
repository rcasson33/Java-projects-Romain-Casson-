import tc.TC;

public class ABR {
    private Noeud racine;
    
    // construit un arbre vide
    public ABR() {
        this.racine = null;
    }

    // construit un arbre avec cette racine (pour les tests seulement)
    public ABR(Noeud _racine) {
        this.racine = _racine;
    }
 
    public String toString( ) {
        return "Index de : " + racine;
    }
    
    public void dessiner( ) {
    	new Fenetre(this.racine);
    }

    public int hauteur(){
        if (this.racine == null){
            return 0;
        }
        else{
        return(this.racine.hauteur());
        }
    }

    public ListeEntiers chercher(String w){
        if (this.racine == null){
            return new ListeEntiers();
        }
        else{
            return(this.racine.chercher(w));
        }
    }

    public boolean estValide(){
        if (this.racine == null){
            return true;
        }
        else{
            return (Noeud.estValide(this.racine,  null, null));
        }
    }

    public void ajouterOccurrence(String w, int n){
        if (this.racine == null){
            this.racine = new Noeud(new Entree(w, n));
        }
        else{
            this.racine.ajouterOccurrence(w, n);
        }
    }

    public void indexerTexte(){
    int nligne = 1;
    while(!TC.finEntree( )) {
        for(String mot: TC.lireLigne( ).split("[ .,:;!?()\\[\\]\"]+"))
        ajouterOccurrence(mot,nligne);
        nligne++;
    }
    }

    public void imprimer(String nom){
        TC.ecritureDansNouveauFichier(nom+".index");
        if (this.racine != null){
            this.racine.imprimer();
        }
        TC.ecritureSortieStandard();
    }

    public ListeEntrees liste(){
        if (this.racine == null){
            return new ListeEntrees();
        }
        else{
            return this.racine.liste();
        }
    }
}

