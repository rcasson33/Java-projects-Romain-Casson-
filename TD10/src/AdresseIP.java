import tc.TC;

public class AdresseIP {
	private final long data;

    public AdresseIP(long d){
        this.data = d;
    }

    public static long deTableau(boolean[] binaires){
        int n = binaires.length;
        long d = 0;

        for (int i = 0; i < n; i++){
            if(binaires[32-i-1]){
                d = d + ( (long) 1 << i);
            }
        }

        return d;
    }

    public AdresseIP(boolean[] binaires){
        this.data = deTableau(binaires);
    }

    public boolean[] enTableauBinaire(){
        long d = this.data;
        boolean [] tab = new boolean[32];

        for (int i = 0; i < 32; i++){
                if (d % 2 == 0){
                    tab[32-i-1] = false;
                }
                else{
                    tab[32-i-1] = true;
                }
                d = d >> ( (long) 1);
            }
        
        return tab;

    }

    public String enChaineBinaire(){
        String s = "";
        boolean [] tab = this.enTableauBinaire();
        for (int i = 0; i < 32; i++){
            if(tab[i]){
                s = s + "1";
            }
            else{
                s = s + "0";
            }
                if ( (i+1)  % 8 == 0 && (i+1) < 31){
                    s = s+ ".";
                }
            }

        return s;
    }

    public String toString(){
        String s = "";
        boolean [] tab = this.enTableauBinaire();
        long tmp = 0;
        for (int i=3; i >= 0; i--){
            for (int j=0; j < 8; j++){
                
                if (tab[i*8 + j]){
                    tmp = tmp + ( (long) 1 << (7-j));
                }

            }
            s = s + tmp;
            if (i != 0) s = s + ".";
            tmp = 0;
            }
        
        return s;
    }

    public AdresseIP(String chaineDecimale){
        String [] decoupage = TC.decoupage(chaineDecimale, '.');
        boolean [] tabfinal = new boolean[32];
        for (int i = 0; i < decoupage.length; i++){
            long d = Integer.parseInt(decoupage[i]);
            for (int j=0; j<8; j++){
                if (d % 2 == 1){
                    tabfinal[(7-j) + i*8] = true;
                }
                else{
                    tabfinal[(7-j) + i*8] = false;
                }
                d = d >> 1; 
            }
        }
        this.data = deTableau(tabfinal);
    }
}



