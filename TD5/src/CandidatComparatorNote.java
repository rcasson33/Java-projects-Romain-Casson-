import tc.TC;

public class CandidatComparatorNote implements CandidatComparator {

	@Override
	public int compare(Candidat c1, Candidat c2) {
		int note1 = c1.note;
		int note2 = c2.note;

		if (note1 < note2){
			return ((int) -1);
		}
		if (note1 == note2){
			return ((int) 0);
		}
		return (int) 1;
		}
}


