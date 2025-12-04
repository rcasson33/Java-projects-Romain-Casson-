public class TestSqDist {

	
	public static void main(String[] args) {

	double [] a = new double []{};
	double [] b = new double []{};

	double [] c = new double []{1};
	double [] d = new double []{-1};

	double [] e = new double [1000];
	double [] f = new double [1000];

	for (int i= 0; i < 1000; i++){
		e[i] = 1;
		f[i] = -1;
	}

	System.out.println(KDTree.sqDist(a, b));
	System.out.println(KDTree.sqDist(c, d));
	System.out.println(KDTree.sqDist(e, f));

}
}
