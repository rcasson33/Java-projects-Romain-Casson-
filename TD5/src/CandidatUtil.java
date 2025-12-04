import tc.TC;

public class CandidatUtil {
    public static Candidat[] readCandidatsFromFile(String nomDuFichier) {
		TC.lectureDansFichier(nomDuFichier);

		int n = Integer.parseInt(TC.lireLigne());

		Candidat[] candidats = new Candidat[n];
		for (int i = 0; i < n; i++){
			candidats[i] = new Candidat(TC.lireLigne());
		}
		TC.lectureEntreeStandard();

		return(candidats);
    }
    
    public static void printCandidatsTable(Candidat[] data) {
	TC.println(data.length);
	for (int i = 0; i < data.length; i++) {
	    TC.println(data[i]);
	}
    }

}
