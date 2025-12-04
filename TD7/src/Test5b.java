import java.util.Arrays;

import tc.TC;

public class Test5b {

	public static void main(String[] args) {

		// Test invPermuation
		int[] perm = { 2, 3, 4, 1, 5 };
		int[] invperm = Permutation.invPermutation(perm);
		Permutation.afficher(invperm);
		Permutation.afficher(Permutation.invPermutation(invperm));

		int[] check = { 4, 1, 2, 3, 5 };
		if (Arrays.equals(check, invperm)) {
			TC.println("invPermutation ok");
		} else {
			TC.println("invPermutation KO :(");
		}

		// Test decodeString
		String decode = Permutation.decodeString("JTSUBUUTORTTNAEOOOES", perm);
		TC.println(decode);
		if (decode.equals("BONJOURATOUTESETTOUS")) {
			TC.println("decodeString ok");
		} else {
			TC.println("decodeString KO :(");
		}
	}

}
