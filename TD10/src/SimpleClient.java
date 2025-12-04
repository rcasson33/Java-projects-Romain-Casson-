public class SimpleClient extends Client {

	public SimpleClient(String pseudo) {
		super(pseudo);
	}

	public SimpleClient(String pseudo, Dialogue d) {
		super(pseudo, d);
	}

	@Override
	public void recevoirDuCanal(String message) {
		// on affiche simplement ce qui arrive du canal
		this.afficher("-- " + message + " --");
	}

	@Override
	public void recevoirDuClavier(String ligne) {
		// on envoie simplement ce qui arrive du clavier
		this.envoyer("++ " + ligne + " ++");
	}

	public static void main(String[] args) {
		new Canal_361(new SimpleClient("Barnab")).lancer();
		new Canal_361(new SimpleClient("Timon", new Dialogue(400, 0))).lancer();
	}

}
