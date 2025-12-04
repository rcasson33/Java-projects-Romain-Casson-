
// arc d'un graphe
public class Edge {
	int origin;
	int destination;
	
	public Edge(int origin, int destination) {
		this.origin = origin;
		this.destination = destination;
	}

	public boolean equals(Object o) {
		Edge that = (Edge)o;
		return this.origin == that.origin && this.destination == that.destination;
	}
	
	public int hashCode() {
		return (Graph.cHash * this.origin) + this.destination;
	}
}
