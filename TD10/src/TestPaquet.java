import tc.TC;

public class TestPaquet {

	public static void main(String[] args) {
		TC.println(new Paquet("glop"));
		TC.println(new Paquet("a;b;HELLO"));
		TC.println(new Paquet("a;b;__HELLO__"));
		TC.println(new Paquet("a;b;__MESSAGE__;0;1;blah"));
	}

}
