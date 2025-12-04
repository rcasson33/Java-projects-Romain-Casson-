
public class Chat extends Client {

	private EtatClient etatCourant = EtatClient.NON_CONNECTE;
	private String correspondant = null;
	private int num_envoi = 0;
	private int num_reception = 0;

	public Chat(String pseudo) {
		super(pseudo);
		afficher("Entrez Hello pour commencer\n" + getPseudo() + ":");
	}

	public Chat(String pseudo, Dialogue d) {
		super(pseudo, d);
		afficher("Entrez Hello pour commencer\n" + getPseudo() + ":");
	}

	@Override
	public void recevoirDuCanal(String paquet) {
		Paquet p = new Paquet(paquet);
		if (p.getType() == TypePaquet.__WRONG__)
			return;
		switch (this.etatCourant) {
		case NON_CONNECTE:
			// ici traitement de ce cas
			break;
		case RECU_CONNECT:
			// ici traitement de ce cas
			break;
		case CONNECT_ENVOYE:
			// ici traitement de ce cas
			break;
		case EST_CONNECTE:
			// ici traitement de ce cas
			break;
		default:
			throw new AssertionError("cas non prévu dans recevoirDuCanal : " + this.etatCourant);
		}
	}

	@Override
	public void recevoirDuClavier(String ligne) {
		switch (this.etatCourant) {
		case NON_CONNECTE:
			// ici traitement de ce cas
			break;
		case RECU_CONNECT:
			// ici traitement de ce cas
			break;
		case CONNECT_ENVOYE:
			// ici traitement de ce cas
			break;
		case EST_CONNECTE:
			// ici traitement de ce cas
			break;
		default:
			throw new AssertionError("cas non prévu dans recevoirDuClavier : " + this.etatCourant);
		}
	}

	public static void main(String[] args) {
		new Canal_361(new Chat("alice", new Dialogue(400, 0))).lancer();
		new Canal_361(new Chat("bob", new Dialogue(400, 0))).lancer();
		new Canal_361(new SimpleClient("Chuck")).lancer();
	}

}
