public class Test4b {
    public static void main(String[] args){
      int[] tab={8,1,15,10,2,14,5,4,3,7,11,12,13,16,9,6};
      char[][] res={{'P','G','P','P','G','P','P','P'},{'P','P','G','P'},{'G','P'},{'G'}};
      System.out.println(Tournoi.vainqueur(tab,res));
    }
  }