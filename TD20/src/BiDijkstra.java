/* TD10. Plus courts chemins */

//Algorithme de Dijkstra bidirectionnel
class BiDijkstra {
	final Graph g; // le graphe de travail
	final int source; // source du plus court chemin recherche
	final int dest; // destination du plus court chemin recherche
	final Dijkstra forward; // recherche de plus courts chemins depuis la source
	final Dijkstra backward; // recherche de plus courts chemins depuis la destination
	Dijkstra currentDijkstra, otherDijkstra; // sens de la prochaine iteration et celui oppose 
	
	private int last; // sommet traite par la derniere iteration
	
	private Fenetre f; // fenetre pour la visualisation
		
	/* Méthodes à compléter */ 
	
	// Question 3.1
	
	// constructeur
	BiDijkstra(Graph g, int source, int dest) {
		this.g = g;
		this.source = source;
		this.dest = dest;
		this.forward = new Dijkstra(g, source, dest);
		Graph ginv = g.reverse();
		this.backward = new Dijkstra(ginv, dest, source);
		this.currentDijkstra = this.forward;
		this.otherDijkstra = this.backward;
		this.last = source;
	}

	// Question 3.1
	
	// changer la direction de recherche
	void flip() {
		Dijkstra temp = this.currentDijkstra;
		this.currentDijkstra=this.otherDijkstra;
		this.otherDijkstra=temp;
	}

	// Question 3.1
	
	// une iteration de Dijkstra bidirectionnel
	void oneStep() {
		this.last=currentDijkstra.oneStep();
	}
	
	// Question 3.1
	
	// test de terminaison
	boolean isOver() {
		if (forward.settled[last]== true && backward.settled[last]==true) return true;
		return false;
	}
		
	// Questions 3.1 et 3.2
	
	// renvoyer la longueur du plus cours chemin
	int getMinPath() {
		int best = forward.dist[last] + backward.dist[last];
		int bestSommet = last;
		for (int i = 0; i < g.nbVertices; i++){
			for (int succ : g.successors(i)){
				if (forward.dist[i] == Integer.MAX_VALUE ||backward.dist[succ] == Integer.MAX_VALUE) continue;
				int poids = g.weight(i, succ);
				int candidat = forward.dist[i] + backward.dist[succ] + poids;
				if (candidat < best){
					best = candidat;
					bestSommet = succ;
				}
			}
		}
		last = bestSommet;
		return best;
	}
	
	// Question 3.1
	
	// algorithme de Dijkstra bidirectionnel
	int compute() {
		while(!isOver()){
			oneStep();
			flip();
		}
		return (getMinPath());
	}
	
	// Question 4
	
	public int getSteps () {
		return backward.getSteps()+forward.getSteps();
	}

	/* Méthodes à ne pas modifier */ 
	
	int getLast() { return last; }
	
	public void setFenetre (Fenetre f) {
		this.f = f;
		forward.setFenetre(f);
		backward.setFenetre(f);
	}
	
	public void draw () {
	    g.drawSourceDestination(f, source, dest);
	    g.drawPath(f, forward.getPred(), backward.getPred(), last);
	}
}

