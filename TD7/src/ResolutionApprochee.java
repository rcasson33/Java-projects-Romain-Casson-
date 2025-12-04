import java.util.Arrays;

public class ResolutionApprochee {

	public static SacADos remplirSac(Objet[] obj, int capacite) {
		// On recopie le tableau d'objets avant de le trier pour ne
		// pas modifier le tableau reçu en paramètre.
		Objet[] objets = obj.clone();
		Arrays.sort(objets, new ObjetComparator());
		int n = objets.length;

		SacADos s1 = new SacADos(capacite);
		SacADos s2 = new SacADos(capacite);
		
		for (int i = 0; i < n; i++){
			if (objets[i].poids() <= s1.capaciteDisponible()){
				s1.ajouter(objets[i]);
			}

			else if (objets[i].poids() <= s2.capaciteDisponible()){
				s2.ajouter(objets[i]);
			}
		}

		if (s1.valeur() >= s2.valeur()){
			return s1;
		}
		else{
			return s2;
		}
	}

}
