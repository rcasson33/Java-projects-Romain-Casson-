public class ResolutionExacte {

	public static SacADos remplirSac(Objet[] objets, int capacite) {
		int n = objets.length;
		return remplirSacAux(objets, capacite, capacite, n);
	}


	public static SacADos remplirSacAux(Objet[] objets, int capacite, int limite, int i){
		if (i == 0){
			return (new SacADos(capacite));
		}
		else{
			SacADos sAvant = remplirSacAux(objets, capacite, limite, i-1);
			Objet o = objets[i-1];

			if (o.poids() <= limite){
				SacADos sApres = remplirSacAux(objets, capacite, limite - o.poids(), i-1);
				sApres.ajouter(o);
			

				if (sApres.valeur() > sAvant.valeur()){
					return sApres;
				}
			}

			return sAvant;
		}
	}
}
