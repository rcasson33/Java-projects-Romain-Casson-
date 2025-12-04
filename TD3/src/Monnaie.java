import tc.TC;

public class Monnaie {
    public final String nom;
    private double taux;

    public Monnaie (String a, double t){
        this.nom = a;
        this.taux = t;
    }

    public double getTaux(){
        return(this.taux);
    }

    public boolean setTaux(double autreTaux){
        if (autreTaux <= 0){
            return false;
        }
        else {
            this.taux = autreTaux;
            return true;
        }
    }
	
    public String pluriel() { 
		return this.nom+"s"; 
	}

    public boolean estEgalA(Monnaie m){
        if(m != null && this.taux == m.taux && this.nom.equals(m.nom)){
            return true;
        }
        else {
            return false;
        }
    }

    public static Monnaie trouverMonnaie(String s, Monnaie[] tab){
        // méthode qui renvoie la monnaie cherchée si elle apparait dans le tableau    
        int n = tab.length;
        for (int i = 0; i < n; i++){ // on parcourt le tableau
            if (tab[i].nom.equals(s) || tab[i].pluriel().equals(s)){ //on fait attention à un possible s à la fin du nom de la monnaire
                return tab[i];
            }
        }
        return(null); //si on ne trouve pas
    }
            
        
}
