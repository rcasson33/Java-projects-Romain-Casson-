/* TD10. Plus courts chemins */

import java.lang.reflect.Field;
import java.util.PriorityQueue;

// Algorithme de Dijkstra
class Dijkstra {
	final Graph g; // le graphe de travail
	final int source; // source du plus court chemin recherche
	final int dest; // destination du plus court chemin recherche
	private Fenetre f; // fenetre pour la visualisation
	int[] dist;
	int[] pred;
	boolean[] settled;
	PriorityQueue<Node> unsettled;
	int steps;


	// Questions 1.1, 1.2 et 4
	
	
	/* Méthodes à compléter */
	
	// Questions 1.1 et 1.2
	
	// constructeur
	Dijkstra(Graph g, int source, int dest) {
		steps = 0;
		this.g = g;
		this.source = source;
		this.dest = dest;
		int n = this.g.nbVertices;
		this.dist = new int[n];
		this.pred = new int[n];
		this.settled = new boolean[n];
		for (int i=0; i < n; i++){
			this.dist[i] = Integer.MAX_VALUE;
			this.pred[i] = -1;
			this.settled[i] = false;
		}
		this.dist[source] = 0;
		this.pred[source] = source;

		this.unsettled = new PriorityQueue<Node>();
		this.unsettled.add(new Node(source, 0));	
	}
	
	// Question 2.1 et 2.2

	// mise a jour de la distance, de la priorite, et du predecesseur d'un sommet
	void update(int succ, int current) {
		int d = dist[current] + g.weight(current, succ);
		if (d < dist[succ]){
			dist[succ] = d;
			pred[succ] = current;
			unsettled.add(new Node(succ, d));
		}
	}

	// trouve le prochain sommet de unsettled non traite
	int nextNode() {
		while(!unsettled.isEmpty()){
			Node current = unsettled.poll();
			if (settled[current.id]==false) return current.id;
		}
		return -1;
	}
	
	// Questions 2.1, 2.2 et 4

	// une etape de l'algorithme Dijkstra
	int oneStep() {
		steps +=1;
		int current = nextNode();
		if (current == -1) return -1;
		settled[current] = true;
		for (int succ : g.successors(current)){
			update(succ, current);
		}
		return current;		
	}
	
	// Question 2.1

	// algorithme de Dijkstra complet
int compute() {
    while (true) {
        int current = oneStep();
        if (current == -1) break; 
        if (current == dest) {
            return dist[dest]; 
        }
    }
    return -1; 
}
	
	// Question 4
	
	public int getSteps() {
		return this.steps;
	}
	
	/* Méthodes à ne pas changer */
	
	// ralentisseur visualisation
	void slow(){
		if(f == null) return;
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {}
	}
	
	void setFenetre (Fenetre f) { this.f = f; }

	// Cette fonction vérifie si le vecteur 'int[] name' est 
	// présent dans la classe et le renvoie. Sinon, renvoie null 
	private int[] getIntArray(String name) {
		Field field = null;
		for (Field f : getClass().getDeclaredFields()) {
			if (f.getName().equals(name)) {
				field = f;
				break;
			}
		}
		if (field == null)
			return null;
		
		int[] result = null;
		try {
			result = (int[]) field.get(this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int[] getPred() {
		return getIntArray("pred");
	}
	
	public int[] getDist() {
		return getIntArray("dist");
	}	
		
	public void draw () {
		g.drawSourceDestination(f, source, dest);
		g.drawPath(f, getPred(), dest);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Dijkstra))
			return false;
		
		Dijkstra that = (Dijkstra) obj;
		return g.equals(that.g) && source == that.source && dest == that.dest;
	}
}
