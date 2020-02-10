package hu.petrik;

import hu.petrik.felulet.SakkFelulet;
import hu.petrik.logika.SakkTabla;

public class Main {

    public static void main(String[] args) {

        SakkFelulet felulet = new SakkFelulet();

        SakkTabla t = new SakkTabla();
        System.out.println(t);
        t.lep(6,1,4,1); //világos gyalog kilép
        t.lep(1,1,3,1);
        t.lep(6,2,4,2);
        t.lep(3,1,4,2); //sötét gyalog leüt
        System.out.println(t);

    }
}
