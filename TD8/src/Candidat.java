import tc.TC;

public class Candidat {

    private static final String idf = "Ile-de-France";

    private final String nom;
    private final String prenom;
    private final String region;
    public int note;

    public Candidat(String nom, String prenom, String region, int note) {
        this.nom = nom;
        this.prenom = prenom;
	this.region = region;
        this.note = note;
    }

    /**
     * Crée un candidat à partir d'une chaîne de la forme "nom prénom note"
     * et évalué dans la region donnée.
         * @param region la région du candidat.
     * @param ligne
     *            la chaîne à analyser. On suppose qu'elle est de la forme
     *            indiquée ci-dessus
     */
    public Candidat(String region, String ligne) {
        String[] mots = TC.motsDeChaine(ligne);
        this.nom = mots[0];
        this.prenom = mots[1];
        this.region = region;
        this.note = Integer.parseInt(mots[2]);
    }

    public String toString() {
        return (this.nom + " " + this.prenom + " " + this.note);
    }

    public int ordreAlphabetique(Candidat c) {
      int cmp = this.getNom().compareTo(c.getNom());
      if (cmp == 0) {
        cmp = this.getPrenom().compareTo(c.getPrenom());
      }
      return cmp;
    }

    public int ordreNote(Candidat c){
      if (this.note < c.note){
        return -1;
      }
      if (this.note > c.note){
        return 1;
      }
      else{
        if (this.region.equals(idf) && !c.region.equals(idf)){
          return -1;
        }
        if(!this.region.equals(idf) && c.region.equals(idf)){
          return 1;
        }
        else{
          return 0;
        }
      }
    }
    // END_EXO exo5a

    public String getNom() { return this.nom; }
    public String getPrenom() { return this.prenom; }
    public String getRegion() { return this.region; }
    public int getNote() { return this.note; }
    public void setNote(int n) {
      if (n < 0 || n > 20) return;
      this.note = n;
    }
}
