import tc.TC;
public class Test01 {
	public static void main(String[] args) {
		
		Monnaie yuan=new Monnaie("Yuan",7.8);
		TC.println("-- test Monnaie() et getTaux():\nYuan 7.8\t<-- attendu");
		TC.println(yuan.nom+" "+yuan.getTaux() + "\t<-- obtenu\n");
				
		TC.println("-- test setTaux(double autreTaux) :\ntrue 7.6\t<-- attendu");
		boolean res = yuan.setTaux(7.6);
		TC.print(res + " " + yuan.getTaux() + "\t<-- obtenu\n");

		TC.println("-- test setTaux(double autreTaux) :\nfalse 7.6\t<-- attendu");
		res = yuan.setTaux(0);
		TC.print(res + " " + yuan.getTaux() + "\t<-- obtenu\n");

		TC.println("-- test setTaux(double autreTaux) :\nfalse 7.6\t<-- attendu");
		res = yuan.setTaux(-7.6);
		TC.print(res + " " + yuan.getTaux() + "\t<-- obtenu\n");

		TC.println();
	}
}
