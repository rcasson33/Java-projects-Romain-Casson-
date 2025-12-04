import tc.TC;

public class TestExercice5 {
    public static void main(String[] args) {
	System.setProperty("line.separator", "\n");

	Candidat[] candidats = CandidatUtil.readCandidatsFromFile("candidats.txt");

	new InsertionSort(candidats, new CandidatComparatorNote()).run();

	TC.println("-- redirection de sortie vers fichiers SortieExercice5a.txt et SortieExercice5b.txt");
	TC.ecritureDansNouveauFichier("SortieExercice5a.txt");
	CandidatUtil.printCandidatsTable(candidats);

	new InsertionSort(candidats, new CandidatComparatorNom()).run();
	TC.ecritureDansNouveauFichier("SortieExercice5b.txt");
	CandidatUtil.printCandidatsTable(candidats);

    }
}
