
public class RadixSort implements SortingAlgorithm {

	private RadixSortAdapter adapter;
	private Candidat[] data;

	public RadixSort(Candidat[] data, RadixSortAdapter adapter) {
		this.adapter = adapter;
		this.data = data;
	}

	@Override
	public void run() {
	    TriRadix.trier(this.data, this.adapter);
	}

}
