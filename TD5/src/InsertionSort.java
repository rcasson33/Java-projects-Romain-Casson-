public class InsertionSort implements SortingAlgorithm {

	private Candidat[] table; // tableau a trier
	private CandidatComparator comparator; // definit la relation d'ordre

	public InsertionSort(Candidat[] table, CandidatComparator comparator) {
		this.table = table;
		this.comparator = comparator;
	}

	public void run() {
            TriInsertion.trier(this.table, this.comparator);
	}

}
