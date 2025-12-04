public class SelectionSort implements SortingAlgorithm {

	private Candidat[] table; // tableau a trier
	private CandidatComparator comparator; // definit la relation d'ordre

	public SelectionSort(Candidat[] table, CandidatComparator comparator) {
		this.table = table;
		this.comparator = comparator;
	}

	public void run() {
	    TriSelection.trier(this.table, this.comparator);
	}

}
