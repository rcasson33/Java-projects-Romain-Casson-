import java.util.Vector;

public class KDTree {
	int depth;
	double[] point;
	KDTree left;
	KDTree right;

	KDTree(double[] point, int depth) {
		this.point = point;
		this.depth = depth;
	}

	boolean compare(double[] a) {
		return (difference(a)>=0);
	}

	double difference(double[] a){
		int r = this.depth % this.point.length;
		return a[r]-this.point[r];
	}

	double difference(double[] a, double[] b){
		int r = this.depth % this.point.length;
		return a[r]-b[r];
	}

	static KDTree insert(KDTree tree, double[] a) {
		if (tree == null) return new KDTree(a, 0);
		boolean o = tree.compare(a);
		if (o){
			if (tree.right == null){
				tree.right = new KDTree(a, tree.depth+1);
			}
			else{
				tree.right = insert(tree.right, a);
			}
		}
		else if (!o){
			if (tree.left == null){
				tree.left = new KDTree(a, tree.depth+1);
			}
			else{
				tree.left = insert(tree.left, a);
			}
		}
		return tree;
	}

	static double sqDist(double[] a, double[] b) {
		int n = a.length;
		double distance = 0;
		for (int i = 0; i < n; i ++){
			distance += (a[i] - b[i]) * (a[i] - b[i]);
		}
		return distance;
	}

	static double[] closestNaive(KDTree tree, double[] a, double[] champion) {
		if (tree==null) return champion;
		champion = closestNaive(tree.left, a, champion);
		if (champion == null){
			champion = tree.point;
		}
		else{
		double courant = sqDist(tree.point, a);
		double avant = sqDist(a, champion);
			if (courant < avant){
				champion = tree.point;
			}
		}
		champion = closestNaive(tree.right, a, champion);
		return champion;
	}


	static double[] closestNaive(KDTree tree, double[] a) {
		return closestNaive(tree, a, null);
	}

	static double[] closest(KDTree tree, double[] a, double[] champion) {
		if (tree==null) return champion;

		InteractiveClosest.trace(tree.point, champion);

		if (champion == null){
			champion = tree.point;
		}
		else{
			double courant = sqDist(tree.point, a);
			double avant = sqDist(a, champion);
			if (courant < avant){
				champion = tree.point;
			}
		}
		
		boolean goDroite = tree.compare(a);

		KDTree premier = goDroite ? tree.right : tree.left;
		KDTree deuxieme = goDroite ? tree.left : tree.right;

		champion = closest(premier, a, champion);

		double distanceChampion = sqDist(a, champion);
		double distanceHyperplan = (tree.difference(a)) * (tree.difference(a));
		
		if (distanceHyperplan < distanceChampion){
			champion = closest(deuxieme, a, champion);
		}
		return champion;

	}

	static double[] closest(KDTree tree, double[] a) {
		return closest(tree, a, null);
	}

	static int size(KDTree tree) {
		if (tree==null) return 0;
		return 1+size(tree.left)+size(tree.right);
	}

	static void sum(KDTree tree, double[] acc) {
		
	}

	static double[] average(KDTree tree) {
		throw(new Error("TODO"));
	}


	static Vector<double[]> palette(KDTree tree, int maxpoints) {
		throw(new Error("TODO"));
	}

	public String pointToString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (this.point.length > 0)
			sb.append(this.point[0]);
		for (int i = 1; i < this.point.length; i++)
			sb.append("," + this.point[i]);
		sb.append("]");
		return sb.toString();
	}

}
