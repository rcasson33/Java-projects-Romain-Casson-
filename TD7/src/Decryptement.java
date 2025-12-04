public class Decryptement {

	public static String[] decrypterToutes(String msg, int n) {
		int [][] perms = Permutation.listePermutations(n);
		String [] tabMess = new String [perms.length]; 
		for (int i = 0; i < perms.length; i++){
			tabMess[i] = Permutation.decodeString(msg, perms[i]);
		}
		return tabMess;
	}

}
