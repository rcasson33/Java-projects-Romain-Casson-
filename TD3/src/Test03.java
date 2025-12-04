import tc.TC;
public class Test03 {

	public static void main(String[] args) {
		
		Monnaie dollar=new Monnaie("Dollar",0.93);

        	Argent a1 = new Argent(68);
		Argent a2 = new Argent(17401, dollar);

		
		TC.println("-- test toString :\n0.68 Euro\t<-- attendu");
		TC.println(a1.toString() + "\t<-- obtenu\n");

		TC.println("-- test toString :\n174.01 Dollars\t<-- attendu");
		TC.println(a2 + "\t<-- obtenu\n"); // appel implicite de a2.toString()
	}

}
