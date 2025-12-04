public class PuissanceNaive implements Puissance {

	/** Compteur de multiplication de matrices */
	private long mulCount = 0;

	/**
	 * Calcule la puissance d'une matrice de maniere naive:
	 * M^n=M^(n-1)*M
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
			mulCount++;
			return(puissanceRec(matrix, n-1).multiplie(matrix));
			
		}
	}

	/**
	 * Renvoie le type d'algorithme implanté pour calculer M^n
	 *
	 * @return le nom de l'algorithme implanté
	 */
	public String name() {
		return "(Calcul de M^n par Puissance naive)";
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
