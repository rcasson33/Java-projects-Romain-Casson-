import java.math.BigInteger;

public class PuissanceRapide implements Puissance {

	/** Compteur de multiplications de matrices */
	private long mulCount = 0;

	/**
	 * Calcule la puissance M^n avec la strategie dite: "square-and-multiply"
	 *
	 * @param matrix
	 *               matrice 2x2 en entrée
	 * @param n
	 *               exposant
	 * @return M^n
	 */

	/**
	 * Renvoie le type d'algorithme implanté pour calculer M^n
	 *
	 * @return le nom de l'algorithme implanté
	 */

	 public Matrice2D puissance(Matrice2D matrix, int n) {
		mulCount = 0;
		return puissanceRec(matrix, n);
	}

	public Matrice2D puissanceRec(Matrice2D matrix, int n) {
		if (n == 0){
			return Matrice2D.identite();
		}
		if (n == 1){
			return matrix;
		}
		else{
			if (n % 2 == 0){
				mulCount++;
				Matrice2D m = puissanceRec(matrix, n/2);
				return (m.multiplie(m));
			}
			else{
				mulCount+=2;
				Matrice2D m = puissanceRec(matrix, (n-1)/2);
				return (m.multiplie(m).multiplie(matrix));
			}
		}
	}



	public String name() {
		return "(Calcul de M^n par Puissance rapide, 'square-and-multiply')";
	}

	/**
	 * Renvoie le nombre de multiplications de matrices effectuées
	 * par le dernier appel à la méthode puissance.
	 *
	 * @return le nombre de multiplications
	 */
	public long operations() {
		return mulCount * Matrice2D.OP_PAR_MUL;
	}

}
