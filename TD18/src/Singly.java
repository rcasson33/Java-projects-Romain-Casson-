class Singly<E> {
	E element;
	Singly<E> next;

	// On choisit de représenter la liste vide par null, les deux constructeurs qui suivent ne
	// peuvent donc pas construire de liste vide.

	// Cree une liste a un element.
	
	public Singly(E element, Singly<E> next) {
		this.element = element;
		this.next = next;
	}

	// Crée une liste à partir d'un tableau non vide.
	
	public Singly(E[] data) {
		assert (data.length > 0) : "\nLe constructeur Singly(E[] data) ne peut pas être utilisé avec un tableau vide"
				+ "\ncar on ne peut pas construire une liste non vide sans données.";
		this.element = data[0];
		this.next = null;
		Singly<E> cursor = this;
		for (int i = 1; i < data.length; i++) {
			cursor.next = new Singly<E>(data[i], null);
			cursor = cursor.next;
		}
		;
	}

	static<E> int length(Singly<E> l){
		int compteur = 0;
		while (l != null){
			compteur ++;
			l = l.next;
		}
		return compteur;
	}

	static<E> Singly<E> split(Singly<E> l){
		if (l == null) return null;
		int n = (length(l) + 1) / 2;
		Singly<E> courant = l;
		for (int i = 1; i < n; i++){
			courant = courant.next;
		}
		Singly<E> deuxieme = courant.next;
		courant.next = null;
		return deuxieme;
	}

	// Copie physique d'une liste (pour les tests uniquement)
	
	static <E> Singly<E> copy(Singly<E> l) {
		if (l == null)
			return null;
		Singly<E> res = new Singly<E>(l.element, l.next);
		Singly<E> cursor = res;
		while (l.next != null) {
			l = l.next;
			cursor.next = new Singly<E>(l.element, l.next);
			cursor = cursor.next;
		}
		return res;
	}

	// Test l'égalite de deux chaînes.
	
	static <E> boolean areEqual(Singly<E> chain1, Singly<E> chain2) {
		while (chain1 != null && chain2 != null) {
			if (!chain1.element.equals(chain2.element))
				return false;
			chain1 = chain1.next;
			chain2 = chain2.next;
		}
		return chain1 == chain2;
	}
	
	// Crée une chaîne de caractères à partir d'une liste chaînée (nécessaire à l'affichage).
	
	public String toString() {
		Singly<E> cursor = this;
		String answer = "[ ";
		while (cursor != null) {
			answer = answer + (cursor.element).toString() + " ";
			cursor = cursor.next;
		}
		answer = answer + "]";
		return answer;
	}

}
