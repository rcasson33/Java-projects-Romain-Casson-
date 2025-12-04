public class CandidatComparatorNom implements CandidatComparator {

	@Override
	public int compare(Candidat c1, Candidat c2) {
		int n = c1.nom.compareTo(c2.nom);
		if (n == 0){
			int m = c1.prenom.compareTo(c2.prenom);
			return m;
		}
		else {
			return n;
		}
	}

}
