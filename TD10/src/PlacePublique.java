
public class PlacePublique extends Client {

	public PlacePublique(String pseudo) {
		super(pseudo);
		this.afficher(this.getPseudo() + ":");
	}

	public PlacePublique(String pseudo, Dialogue d) {
		super(pseudo, d);
		this.afficher(this.getPseudo() + ":");
	}

	@Override
	public void recevoirDuCanal(String paquet) {
		Paquet p = new Paquet(paquet);
		if (p.getType() != TypePaquet.__WRONG__){
			String destinataire = p.getDestinataire();
        	String pseudoperso = this.getPseudo();
			if (destinataire.equals("__ALL__") || destinataire.equals(pseudoperso)) {
            	this.afficher(paquet);
			}
		}
		// on affiche simplement ce qui arrive du canal
	}

	@Override
	public void recevoirDuClavier(String ligne) {
		String s = "";
	    if (ligne.equals("Hello")){
			s = this.getPseudo()+ ";__ALL__;__HELLO__";
		}
		else{
			s = this.getPseudo()+ ";__ALL__;__MESSAGE__;0;0;"+ligne;
		}
		this.envoyer(s);
	}

	public static void main(String[] args) {
		new Canal_361(new PlacePublique("Barnab")).lancer();
		// new Canal_361(new SimpleClient("Timon", new Dialogue(400, 0))).lancer();
	}
	
}
