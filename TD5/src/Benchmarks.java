import tc.TC;

public class Benchmarks{

    private static boolean debug = false;

    // stolen from __Test_Sort__.java
    public static Candidat[] makeArray(int n) {
	Candidat[] dst = new Candidat[n];
	for (int i = 0; i < n; ++i)
	    dst[i] = new Candidat("X" + i % 17, "Y" + i % 37, i % 21,
				  new DossierId((100 + i % 100) + "Z"));
	return dst;
    }
    // stolen_end

    public static void randomize(Candidat[] tab){
	for(int i = tab.length-1; i > 0; i--){
	    int j= (int) (Math.random() * (i+1));
	    TriSelection.swap(tab, i, j);
	}
    }
    
    public static double bench(String algo, CandidatComparator comparator,
			     int n){
	double tl, tt = 0.0;
	
	Candidat[] t = makeArray(n);
	if(debug)
	    TC.println("On randomise");
	randomize(t);
	if(debug){
	    for(int i = 0; i < n; i++)
		TC.println(t[i]);
	    TC.println("On trie");
	}
	tl = System.nanoTime();
	if(algo.compareTo("selection") == 0)
	    TriSelection.trier(t, comparator);
	else if(algo.compareTo("insertion") == 0)
	    TriInsertion.trier(t, comparator);
	tt = (double)((System.nanoTime() - tl)) / 1000000; // time in ms
	if(debug){
	    for(int i = 0; i < n; i++)
		TC.println(t[i]);
	}
	return tt;
    }

    public static void bench(String algo, CandidatComparator comparator,
			     int nmin, int nmax, int nstep){
	double[] tt = new double[nmax];
	for(int n = nmin; n < nmax; n += nstep){
	    tt[n] = bench(algo, comparator, n);
	    TC.println(n+" "+tt[n]);
	}
    }

    public static void main(String[] args){
	CandidatComparator comparator = new CandidatComparatorId();
	TC.println("selection");
	TC.ecritureDansNouveauFichier("selection.in");
	bench("selection", comparator, 100, 2001, 100);
	TC.ecritureSortieStandard();
	TC.println("insertion");
	TC.ecritureDansNouveauFichier("insertion.in");
	bench("insertion", comparator, 100, 2001, 100);
	TC.ecritureSortieStandard();
    }
    
}
