import tc.TC;

public class Argent {

	// représentation interne de l'argent:
	// Champs d'objet
	private final int valeur;
	private final Monnaie monnaie;

	public Argent(int v) {
		this.valeur = v;
		this.monnaie = new Monnaie("Euro", 1.0);
	}

	public Argent(int v, Monnaie monnaie) {
		if (monnaie == null)
			throw new IllegalArgumentException("le parametre monnaie est null");
		this.valeur = v;
		this.monnaie = monnaie;
	}

	public Monnaie getMonnaie() {
		return this.monnaie;
	}

	public int getValeur() {
		return this.valeur;
	}

	public int valeurEntiere() {
		return this.valeur / 100;
	}

	public int valeurDecimale() {
		return this.valeur % 100;
	}

	public boolean estEgalA(Argent a) {
		// on fait appel à la méthode de la classe monnaie
		if (a != null &&  this.valeur == a.valeur && this.monnaie.estEgalA(a.monnaie)){
			return true;
		}
		else{
			return false;
		}
	}

	public String toString(){
		String n = this.monnaie.nom;
		String vD = ""+ this.valeurDecimale();
		if (this.valeurDecimale() < 10){ // on teste si le nombre de centimes possède un ou deux chiffres de façon à rajouter un string 0 s'il n'y en a qu'un pour éviter toute confusion
			vD = "0" + vD;
		}
		if (this.valeur >= 200){
			n = this.monnaie.pluriel();
		}
		return this.valeurEntiere() + "." + vD + " " + n;
		}

	public Argent convertir(Monnaie autreMonnaie){
		if (autreMonnaie.estEgalA(this.monnaie)){
			return this;
		}
		else{
			Argent autre = new Argent ( (int) ((autreMonnaie.getTaux()/this.monnaie.getTaux())*this.valeur), autreMonnaie); // on calcule le nouveau taux
			return autre;
		}
		
	}
	public Argent(String str, Monnaie[] tab){
		String[] separation = TC.motsDeChaine(str);
		String[] espace = TC.decoupage(separation[0],'.');
		String colle = ""+espace[0] + espace[1]; //on obtient la valeur String en centime en collant partie entière et decimale
		
		this.valeur = Integer.parseInt(colle);
		this.monnaie = Monnaie.trouverMonnaie(separation[1], tab);
	}
	
	public Argent plus(Argent x){
		Argent xPrime = x.convertir(this.monnaie);
		Argent nouv = new Argent(xPrime.valeur + this.valeur, this.monnaie);
		return nouv;
	}

	public Argent moins(Argent x){
		Argent xPrime = x.convertir(this.monnaie);
		if (this.valeur - xPrime.valeur >= 0){ //teste la positivité de la nouvelle valeur
			Argent nouv = new Argent(this.valeur - xPrime.valeur, this.monnaie);
			return nouv;}
		else{
			return null;
		}
	}
}


