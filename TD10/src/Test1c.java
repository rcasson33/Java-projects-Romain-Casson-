import tc.TC;

public class Test1c {

    public static void main(String[] args) {
	boolean[] b = new boolean[32];
	b[5] = true;
	AdresseIP a = new AdresseIP(b);
	b[5] = false;

	TC.println(a.enChaineBinaire());
	for(int i = 0; i < 32; i++){
	    b[i] = true;
	    a = new AdresseIP(b);
	    TC.println(i+" "+a.enChaineBinaire());
	}
    }

}
