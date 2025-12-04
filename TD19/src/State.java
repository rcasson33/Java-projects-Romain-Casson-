import java.util.*;

/**
 * l'état donne la position de chaque voiture, avec la convention suivante :
 * pour une voiture horizontale c'est la colonne de sa case la plus à gauche
 * pour une voiture verticale c'est la colonne de sa case la plus haute
 * (rappel : la colonne la plus à gauche est 0, la ligne la plus haute est 0)
 */
class State {
	RushHour plateau;
	int[] pos;

	/** on se rappelle quel déplacement a conduit à cet état, pour l'affichage de la solution */
	State prev;
	int c;
	int d;

	/** construit un état initial (c, d et prev ne sont pas significatifs) */
	public State(RushHour plateau, int[] pos) {
		this.plateau=plateau;
		this.pos=pos;
	}

	/** construit un état obtenu à partir de s en déplaçant la voiture c de d (-1 ou +1) */
	public State(State s, int c, int d) {
		this.plateau=s.plateau;
		this.pos=new int[s.plateau.nbCars];
		for (int i=0; i < s.plateau.nbCars; i++) this.pos[i]=s.pos[i];
		this.prev=s;
		this.pos[c] += d;
		this.c=c;
		this.d=d;

	}

	/** a-t-on gagné ? */
	public boolean success() {
		return this.pos[0]==4;
    }
	
	/** quelles sont les places libres */
	public boolean[][] free() {
		boolean [][] tab = new boolean[6][6];
		for (int i=0; i < 6; i++){
			for (int j = 0; j < 6; j++){
				tab[i][j] = true;
			}
		}
		for (int i = 0; i < this.plateau.nbCars; i++){
			int longueur = plateau.len[i];
        	int move = plateau.moveOn[i];
        	int debut = pos[i];

			if (this.plateau.horiz[i]){
				for (int decalage = 0; decalage <longueur;  decalage++){
					int col = debut + decalage;
					if (col >= 0 && col<6){
						tab[move][col] = false;
					}
				}		
			}
			else{
				for (int decalage = 0; decalage <longueur;  decalage++){
					int ligne = debut + decalage;
					if (ligne >= 0 && ligne<6){
						tab[ligne][move] = false;
					}
				}		
			}
		}
		return tab;
	}

	/* test d'égalité */
	@Override
	public boolean equals(Object o) {
		if (this==o) return true;
		State that = (State) o;
		if (this.pos.length != that.pos.length) return false;
		for (int i = 0; i < this.pos.length; i++) {
        	if (this.pos[i] != that.pos[i]) {
            	return false;
        	}
		}
		return true;
	}

	/** code de hachage */
	public int hashCode() {
		int h = 0;
		for (int i = 0; i < pos.length; i++)
			h = 37 * h + pos[i];
		return h;
	}


}

