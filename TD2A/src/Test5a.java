import tc.TC;
public class Test5a {
  public static void main(String[] args){
    int i = 2;
    int n = 16;
    TC.println("L'adversaire de la joueuse " + i+ " est la joueuse " + Tournoi.adversaire(i, n) + " quand il y a "+ n +" adversaires.");
    i = 15;
    TC.println("L'adversaire de la joueuse " + i+ " est la joueuse " + Tournoi.adversaire(i, n) + " quand il y a "+ n +" adversaires.");
    n = 32;
    TC.println("L'adversaire de la joueuse " + i+ " est la joueuse " + Tournoi.adversaire(i, n) + " quand il y a "+ n +" adversaires.");
    int[] t={1,2};
    Tournoi.afficheTour(t);
    int[] t1=Tournoi.tourPrecedent(t);
    Tournoi.afficheTour(t1);
    int[] t2=Tournoi.tourPrecedent(t1);
    Tournoi.afficheTour(t2);
    int[] t3=Tournoi.tourPrecedent(t2);
    Tournoi.afficheTour(t3);
  }
}