package program;

public class adatok {
    private String nev;
    private int memoria;
    private int ar;
    private final Marka marka;

    public adatok() {
        this("John Doe", 12 , 80000 ,Marka.NVIDIA);
    }

    public adatok(String nev, int memoria, int ar) {
        this(nev, memoria, ar , Marka.NVIDIA);
    }

    public adatok(String nev, int memoria, int ar, Marka marka) {
        this.nev = nev;
        this.memoria = memoria;
        this.ar = ar;
        this.marka = marka;
    }




    public String getNev() {
        return nev;
    }

    /*public void setNev(String name) {
        this.nev = nev;
    }*/

    public int getMemoria() {
        return memoria;
    }

    /*public void setMemoria(int memoria) {
        this.memoria = memoria;
    }*/

    public int getAr() {
        return ar;
    }

    /*public void setAr(int ar) {
        this.ar = ar;
    }*/

    public String getMarka(){
        return this.marka.toString();
    }

    @Override
    public String toString() {
        return nev + '(' + memoria + ')';
    }
}
