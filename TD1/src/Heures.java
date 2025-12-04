import tc.TC;

public class Heures{
  /**
   * Renvoie une chaine de la forme "H : M : S", pour un affichage digital.
   * 
   * A COMPLETER. Vous devez expliciter le calcul des valeurs des heures,
   * minutes et secondes.
   * 
   * @param secondes
   *          nombre total de secondes indiquant l'heure que l'on doit convertir
   * @return la chaine Java correspondante (type String)
   */
  public static String chaineDe(int secondes) {
    int heures = 0;
    int minutes = 0;
    heures = secondes / 3600;
    minutes = (secondes - heures*3600) / 60;
    secondes = secondes - heures*3600 - minutes*60;
    return heures + " : " + minutes + " : " + secondes; // ceci utilise la surcharge de +
  }

  public static int lireEntier(String invite) {
    int a = 0; 
    TC.print(invite);
    a = TC.lireInt();
    return a;
  }

  public static int lireHMS(String invite) {
    int heures = 0;
    int minutes = 0;
    int secondes = 0;
    TC.print(invite);
    heures = TC.lireInt();
    minutes = TC.lireInt();
    secondes = TC.lireInt();
    return heures*3600 + minutes*60 + secondes;
  }


  public static void main(String[] args) {
    int secondes1 = lireHMS("entrer heures minutes secondes : ");
    int secondes2 = lireHMS("entrer heures minutes secondes : ");
    TC.println(chaineDe(secondes1+secondes2));
  }
}
