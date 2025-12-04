import tc.TC;

public class Test1d {

    public static void main(String[] args) {
	boolean[] b = new boolean[32];
	b[0] = true;
	b[7] = true;
	b[16] = true;
	b[24] = true;
	TC.println(new AdresseIP(b));
    }
}
