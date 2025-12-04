import tc.TC;

public class Test1b {

    private static void print(boolean[] t){
	for(int i = 0; i < 32; i++)
	    TC.print((t[i] ? "T" : "F"));
    }

    private static boolean estEgal(boolean[] t1, boolean[] t2){
	//print(t1); TC.println();
	//print(t2); TC.println();
	if(t1.length != t2.length)
	    return false;
	for(int i = 0; i < 32; i++)
	    if(t1[i] != t2[i]){
		TC.println("PB pour i="+i+" "+t1[i]+" "+t2[i]);
		return false;
	    }
	return true;
    }
    
    public static void main(String[] args) {
	boolean[] b = new boolean[32];
	AdresseIP a = new AdresseIP(b);
	boolean[] tmp = a.enTableauBinaire();

	TC.println(estEgal(b, tmp));
	for(int i = 0; i < 32; i++){
	    b[i] = true;
	    a = new AdresseIP(b);
	    tmp = a.enTableauBinaire();
	    TC.println(i+" "+estEgal(b, tmp));
	}
    }

}
