import java.util.*;



/**
 * la représentation du problème est la suivante :
 * la grille a 6 colonnes, numérotées 0 à 5 de gauche à droite
 * et 6 lignes, numérotées 0 à 5 de haut en bas
 *
 * il y a nbCars voitures, numérotées de 0 à nbCars-1
 * pour chaque voiture i :
 * - color[i] donne sa couleur
 * - horiz[i] indique s'il s'agit d'une voiture horizontale
 * - len[i] donne sa longueur (2 ou 3)
 * - moveOn[i] indique sur quelle ligne elle se déplace pour une voiture horizontale
 *   et sur quelle colonne pour une voiture verticale
 *
 * la voiture d'indice 0 est celle qui doit sortir, on a donc
 * horiz[0]==true, len[0]==2, moveOn[0]==2
 */
class RushHour {
	int nbCars;
	String[] color;
	boolean[] horiz;
	int[] len;
	int[] moveOn;
	static int nbMouvs;

    public RushHour(int nbCars,String[] color,boolean[] horiz,int[] len,int[] moveOn){
        this.nbCars = nbCars;
        this.color = color;
        this.horiz = horiz;
        this.len = len;
        this.moveOn = moveOn;
    }
    
	/** renvoie la liste des déplacements possibles à partir de s */
	LinkedList<State> moves(State s) {
        LinkedList<State> resultat = new LinkedList<State>();
		boolean [][] libre = s.free();
		for (int i=0; i < s.plateau.nbCars; i++){
			int debut = s.pos[i];
			int longueur = s.plateau.len[i];
			int move = s.plateau.moveOn[i];

			if (s.plateau.horiz[i]){
				if (debut>0 && libre[move][debut-1]){
					resultat.add(new State(s, i, -1));
				}
				if (debut + longueur < 6 && libre[move][debut + longueur]){
					resultat.add(new State(s, i, +1));
				}
			}
			else{
				if (debut>0 && libre[debut-1][move]){
					resultat.add(new State(s, i, -1));
				}
				if (debut + longueur < 6 && libre[debut + longueur][move]){
					resultat.add(new State(s, i, +1));
				}
			}
		}
		return resultat;
	}


	State solveDFS(State s){
		HashSet<State> visited = new HashSet<State>();
		Stack<State> stack = new Stack<State>();
		stack.push(s);
		while (!stack.isEmpty()){
			s = stack.pop();
			if (s.success()) return s;
			if(visited.contains(s)) continue;
			visited.add(s);
			for (State t : moves(s)) stack.push(t);
		}
		return null;
	}

	/** cherche une solution à partir de l'état s */
	State solveBFS(State s) {
		HashSet<State> visited = new HashSet<State>();
		Queue<State> q = new LinkedList<>();
		q.add(s);
		while (!q.isEmpty()){
			State a = q.poll();
			if (a.success()) return a;
			for (State t : moves(a)){
				if (!visited.contains(t)){
					q.add(t);
					visited.add(t);
				}
			}
		}
		return null;
	}

	/** affiche une solution */
	void printSolution(State s) {
		nbMouvs = 0;
		printMouvsRec(s);
		System.out.println(nbMouvs + " déplacements");
    }

	private void printMouvsRec(State s){
		if(s.prev==null) return;
		printMouvsRec(s.prev);
		nbMouvs++;
		String direction;
    	if (s.plateau.horiz[s.c]) {
        	direction = (s.d == 1) ? "vers la droite" : "vers la gauche";
    	} 
		else {
        	direction = (s.d == 1) ? "vers le bas" : "vers le haut";
    	}

		System.out.println("on déplace le véhicule " + s.plateau.color[s.c] + " " + direction);
	}

	
	
}