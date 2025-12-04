import tc.TC;
public class Banque {
    public final static int TAILLE_INITIALE = 10;
    public final String nom;
    private Compte[] comptes;
    private int nombreDeComptes;

    public Banque(String nom){
        this.nom = nom;
        this.comptes = new Compte[TAILLE_INITIALE];
        this.nombreDeComptes = 0;
    }

    public int getNombreDeComptes(){
        return this.nombreDeComptes;
    }

    public String toString(){
        return("BANQUE " + this.nom);
    }


    public void afficher(){
        TC.println(this.nom);
        TC.println(this.nombreDeComptes);
        for (int i=0; i < this.nombreDeComptes;++i){
            TC.println(this.comptes[i]);
        }   
    }

    public Compte trouverCompte(long numero){
        //trouve si le numero correspond  a un compte de la banque
        int n = this.comptes.length;
        for (int i = 0; i < n; i++){
            if (this.comptes[i] != null && this.comptes[i].numero == numero){
                return this.comptes[i];
            }
        }
        return null;
    }

    public boolean ajouterCompteStatique(Compte c){
        // ajoute un compte dans la banque de façon statique
        if (c != null && this.trouverCompte(c.numero) == null && this.nombreDeComptes < this.comptes.length){
            this.comptes[this.nombreDeComptes] = c;
            nombreDeComptes ++;
            return true;
        }
        return false;
    }

    public boolean ajouterCompte(Compte c){
        // ajoute un compte dans la banque de façon dynamique
        if (c == null || this.trouverCompte(c.numero) != null){
            return false;
        }
        else{
            if (this.nombreDeComptes == this.comptes.length){
                Compte[] nouv = new Compte[2*this.nombreDeComptes];
                for (int i = 0; i < this.nombreDeComptes; i++){
                    nouv[i] = this.comptes[i];
                }
                this.comptes = nouv;

            }
            this.comptes[this.nombreDeComptes] = c;
            nombreDeComptes ++;
            return true;
        }

    }

    public boolean deposer(long numero, Argent argent){
        //depose de l'argent dans le compte de numero de la banque
        if (this.trouverCompte(numero) == null){
            return false;
        }
        else{
            this.trouverCompte(numero).deposer(argent);
            return true;
        }
    }

    public boolean prelever(long numero, Argent argent, boolean avecPlafond){
        //preleve le compte numero de la banque
        if (this.trouverCompte(numero) == null){
            return false;
        }
        else{
            return this.trouverCompte(numero).prelever(argent, avecPlafond);
            
        }

    }

    public boolean deposer(long numero, Liquide liq){
        //depose du liquide du compte numero de la banque
        if (this.trouverCompte(numero) == null){
            return false;
        }
        else{
            this.trouverCompte(numero).deposer(liq.montant());
            return true;
        }
    }

    public Liquide retirer(long numero, Argent a){
        //retire du liquide du compte numero
        boolean res = this.prelever(numero, a, true);
        if (res == true){
            Liquide liq = new Liquide(a);
            return liq;
        }
        else{
            return (null);
        }
    }


}
