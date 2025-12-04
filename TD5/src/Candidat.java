import tc.TC;

public class Candidat {

	public String nom;
	public String prenom;
	public int note;
	public DossierId dossierId;

	public Candidat(String nom, String prenom, int note, DossierId dossierId) {
		this.nom = nom;
		this.prenom = prenom;
		this.note = note;
		this.dossierId = dossierId;
	}

	public Candidat(String ligne) {
		String [] decoupage = TC.motsDeChaine(ligne);

		this.nom = decoupage[0];
		this.prenom = decoupage[1];
		this.note = Integer.parseInt(decoupage[2]);
		this.dossierId = new DossierId(decoupage[3]);
	}

	

	public String toString() {
		return ("" + this.nom + " " + this.prenom + " " + this.note + " " + this.dossierId.toString());
	}

	public boolean equals(Candidat candidat) {
		return this.nom.equals(candidat.nom) && this.prenom.equals(candidat.prenom);
	}

}
