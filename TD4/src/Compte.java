import tc.TC;
public class Compte {
	public final long numero;
	public final String nom;
	private Argent solde;
	private int plafond;
		
	public Compte(long numero, String nom, Argent a, int plafond){
		this.numero = numero;
		this.nom = nom;
		this.solde = a;
		this.plafond=plafond;
	}
			
	public Argent getSolde(){
		return this.solde;
	}
	
	public Monnaie getMonnaie(){
		return this.solde.getMonnaie();
	}
	
	public int getPlafond(){
		return this.plafond;
	}
		
	public void setPlafond(int p){
		this.plafond=p;
	}

	public String toString(){
		return("" + this.numero + " " + this.nom + " " + this.solde.toString() + " " + this.plafond);
	}
	
	public Compte(String str, Monnaie[] tab){
		//cree un compte a partir d'une chaine de caractèreo et du tableau des monnaies
		String [] decoupe = TC.motsDeChaine(str);

		this.numero = Long.parseLong(decoupe[0]);
		this.nom = decoupe[1];
		this.solde = new Argent(decoupe[2]+ " " +decoupe[3], tab);
		this.plafond= Integer.parseInt(decoupe[4]);
		
	}

	public void deposer(Argent argent){
		this.solde = this.solde.plus(argent);
	}

	public boolean prelever(Argent argent, boolean avecPlafond){
		// preleve l'argent du compte
		Monnaie m = this.solde.getMonnaie();
		Argent nouv = argent.convertir(m);
		int x = nouv.getValeur();

		if (this.solde.getValeur() >= x){
			if (avecPlafond == true){
				if (x <= this.plafond*100){
					this.solde = this.solde.moins(nouv);
					return true;
				}
			}
			if (avecPlafond != true){
				this.solde = this.solde.moins(argent);
    			return true;
			}

		}
		return false;

	}
}
