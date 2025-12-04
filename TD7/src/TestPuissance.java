import java.math.BigInteger;

import tc.TC;

/**
 * @author P. Chassignet (INF361, 2022)
 *
 *         Cette classe permet de tester differentes implantations du calcul de
 *         puissance de matrices
 */
public class TestPuissance {

	public static String texteDe(Matrice2D m) {
		StringBuffer s = new StringBuffer("[");
		for (int i = 0; i < 4; i++)
			s.append(' ').append(m.getCoefficient(i));
		return s.append(" ]").toString();
	}

	public static void calculPremieresPuissances(Puissance algo, int n) {
		TC.println("Test pour " + algo.name());
		Matrice2D m = new Matrice2D(
				new BigInteger[] { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO });
		for (int i = 0; i < n; i++)
			TC.println("M^" + i + " = " + texteDe(algo.puissance(m, i)) + " (" + algo.operations()
					+ " ops)");
	}

	public static void main(String[] args) {
		int n = 10;
		Puissance algo; // choix de l'implantation
		algo = new PuissanceNaive();
		// algo = new PuissanceRapide();
		calculPremieresPuissances(algo, n);
	}

}
