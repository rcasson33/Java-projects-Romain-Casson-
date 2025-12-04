import tc.TC;

public class Noeud {

    private final Entree contenu;
    public Noeud gauche;
    public Noeud droit;

    public Noeud(Entree e) {
        this.gauche = null;
        this.droit = null;
        this.contenu = e;
    }

    public Noeud(Noeud g, Noeud d, Entree e) {
        this.gauche = g;
        this.droit = d;
        this.contenu = e;
    }

    public Entree contenu() {
        return this.contenu;
    }

    public String toString( ) {
        String str = "";
        if(this.gauche != null)
            str += "(" + this.gauche + ")";
        else
            str += "*";
        str += " <- " + this.contenu + " -> ";
        if(this.droit != null)
            str += "(" + this.droit + ")";
        else
            str += "*";
        return str;
    }

    // Les methodes ci-dessus sont données, vous ne devez pas les modifier.
    //BEGIN_ALWAYS

    public int hauteur(){
        int hauteurG = 0;
        int hauteurD = 0;
        if (this.gauche != null){
            hauteurG = this.gauche.hauteur();
        }
        if (this.droit != null){
            hauteurD = this.droit.hauteur();
        }
        return (1 + Math.max(hauteurG, hauteurD));
    }

    public ListeEntiers chercher(String w){
        int comparaison = this.contenu().comparer(w);
        if (comparaison == 0){
            return this.contenu().occurrences;
        }
        else if (comparaison > 0 && this.gauche != null){
            return this.gauche.chercher(w);
        }
        else if (comparaison < 0 && this.droit != null){
            return this.droit.chercher(w);
        }
        return new ListeEntiers();
    }

    public static boolean estValide(Noeud courant, String min, String max){
        if (courant == null){
            return true;
        }
        if (min != null){
            if (courant.contenu().comparer(min) <= 0){
                return false;
            }
        }
        if (max != null){
            if (courant.contenu().comparer(max) >= 0){
                return false;
            }
        }
        return (estValide(courant.gauche, min, courant.contenu().mot) && estValide(courant.droit, courant.contenu().mot, max));
    }

    public void ajouterOccurrence(String w, int n){
        int comparaison = this.contenu().comparer(w);
        if (comparaison == 0){
            this.contenu().ajouter(n);
        }
        else if (comparaison > 0){
            if (this.gauche == null){
                this.gauche = new Noeud(new Entree(w, n));
            }
            else{
                this.gauche.ajouterOccurrence(w, n);
            }
        }
        else{
            if (this.droit == null){
                this.droit = new Noeud(new Entree(w, n));
            }
            else{
                this.droit.ajouterOccurrence(w, n);
            }
        }
    }

    public void imprimer(){
            if (this.gauche != null){
                this.gauche.imprimer();
            }
            TC.println(this.contenu().toString());
            if (this.droit != null){
                    this.droit.imprimer();
            }
            
    }

    public ListeEntrees liste(){
        ListeEntrees liste = new ListeEntrees();
        return listeAux(liste);
    }

    public ListeEntrees listeAux(ListeEntrees l){
        if (this.gauche != null){
            this.gauche.listeAux(l);
        }
        l.ajouterEnQueue(this.contenu());
        if (this.droit != null){
            this.droit.listeAux(l);
        }
            
        return l;
        
    }
}
