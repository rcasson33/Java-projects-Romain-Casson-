import java.util.Comparator;

public class ObjetComparator implements Comparator<Objet> {

	public int compare(Objet obj1, Objet obj2) {
		int v1 = obj1.valeur();
		int v2 = obj2.valeur();
		int p1 = obj1.poids();
		int p2 = obj2.poids();

		if (p2*v1 > p1*v2){
			return -1;
		}

		if (p2*v1 < p1*v2){
			return 1;
		}
		
		else{
			return 0;
		}
	}

}
