import tc.TC;

public class ListeCandidats {

    private Maillon tete;
    private Maillon queue;

    public ListeCandidats() {
        this.tete = null;
        this.queue = null;
    }

    public void afficher() {
        if (this.tete == null)
            TC.println("<liste vide>");
        else
            Maillon.afficher(this.tete);
    }

    public boolean estVide() {
        return this.tete == null && this.queue == null;
    }

    public void ajouterEnTete(Candidat c) {
        Maillon m = new Maillon(c, this.tete);
        this.tete = m;
        if (this.queue == null)
            /*
             * on a ajoute un candidat a la liste vide: il est
             * a la fois le premier et le dernier.
             */
            this.queue = m;
    }

    public Maillon tete() { return this.tete; }

    // Les methodes ci-dessous sont a completer au cours des exercices du TP

    public int nombreCandidats() {
        if (this.estVide()){
            return 0;
        }
        else{
            return(Maillon.longueur(tete));
        }
    }

    public boolean estCorrecte() {
        if (this.estVide()){
            return true;
        }
        else{
            if (this.tete != null && this.queue != null && Maillon.dernier(this.tete) == this.queue){
                return true;
            }
            return false;
        }
        
    }

    public void ajouterEnQueue(Candidat c){
        if(this.estVide()){
            ajouterEnTete(c);
        }
        else{
            this.queue.ajouterApres(c);
            this.queue = this.queue.suivant();
        }
    }

    public void ajouterFichierEnQueue(String nomFichier){
        int n = this.nombreCandidats();
        TC.lectureDansFichier(nomFichier);
        String region = TC.lireLigne();
        while (!TC.finEntree()){
            Candidat c = new Candidat(region, TC.lireLigne());
            ajouterEnQueue(c);
            n++;
        }
        TC.println("Candidats de la region " + region + " ajoutes. Il y a maintenant " + n + " candidats");
    }

    public void filtrer(int seuil){
        while (this.tete != null && this.tete.contenu.note < seuil){
            this.tete = this.tete.suivant();
        }
        if (this.tete == null ){
            this.queue = null;
            return;
        }

        Maillon courant = this.tete;
        Maillon suiv;
        while(courant.suivant() != null){
            suiv = courant.suivant();
            if (suiv.contenu.note < seuil){
                Maillon.enleverSuivant(courant);
            }
            else{
                courant = suiv;
            }
        }
        this.queue = courant;
        }
    
    public ListeCandidats selection(){
        ListeCandidats liste = new ListeCandidats();

        if (this.estVide()) return liste;

        Maillon tmp = this.tete;
        Candidat best = tmp.contenu;
        liste.ajouterEnQueue(best);
        tmp = tmp.suivant();

        while(tmp != null){
            if (tmp.contenu.ordreNote(best) > 0){
                liste = new ListeCandidats();
                liste.ajouterEnQueue(tmp.contenu);
                best = tmp.contenu;
            }
            else if (tmp.contenu.ordreNote(best) == 0){
                liste.ajouterEnQueue(tmp.contenu);
            }

            tmp = tmp.suivant();
        }
        return liste;
    }
    
public void ajouterFichierTrie(String nomFichier){
    TC.lectureDansFichier(nomFichier);
    String region = TC.lireLigne();

    Maillon insertion = this.tete;

    if (this.estVide()){
        ajouterFichierEnQueue(nomFichier);
        return;
    }

    while (!TC.finEntree()){
        Candidat c = new Candidat(region, TC.lireLigne());


        if (this.tete.contenu.ordreAlphabetique(c) > 0){
            ajouterEnTete(c);
            insertion = this.tete;
        } 
        else {
            Maillon suiv = insertion.suivant();
            while (suiv != null && suiv.contenu.ordreAlphabetique(c) <= 0){
                insertion = suiv;
                suiv = insertion.suivant();
            }

           
            insertion =  insertion.ajouterApres(c);

            if (suiv == null){
                this.queue = insertion;
            }
        }
    }

    int n = this.nombreCandidats();
    TC.println("Candidats de la region " + region + " ajoutes. Il y a maintenant " + n + " candidats");
}
}
