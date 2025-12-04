import java.util.Arrays;

public class CountingSort implements SortingAlgorithm {

	private Candidat[] a;
	private Candidat[] b;
	private int[] count;
	private CountingSortAdapter adapter;

	public CountingSort(Candidat[] data, CountingSortAdapter adapter) {
		this.a = data;
		this.adapter = adapter;
		this.b = new Candidat[data.length];
		this.count = new int[adapter.getMaxValue() + 1];
	}

	@Override
	public void run() {
	    TriComptage.trier(this.a, this.adapter);
	}

}
