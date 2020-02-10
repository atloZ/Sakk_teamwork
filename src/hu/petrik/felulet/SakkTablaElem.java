package hu.petrik.felulet;

import javax.swing.*;
import java.awt.*;

public class SakkTablaElem extends JButton {

    private int x;
    private int y;
    private int ertek;

    public SakkTablaElem(int x, int y, int ertek){
        this.x = x;
        this.y = y;
        this.ertek = ertek;
        frissit();
    }

    private void frissit(){
        if ((this.x + this.y) % 2 == 0){
            this.setBackground(Color.decode("#FFFFFF"));
        }else{
            this.setBackground(Color.decode("#8B4513"));
        }

        String fajlNev = "img/ures.png";
        switch (this.ertek){
            case 11: fajlNev = "img/feherGyalog.png"; break;
            case 12: fajlNev = "img/feherBastya.png"; break;
            case 13: fajlNev = "img/feherHuszar.png"; break;
            case 14: fajlNev = "img/feherFuto.png"; break;
            case 15: fajlNev = "img/feherVezer.png"; break;
            case 16: fajlNev = "img/feherKiraly.png"; break;
        }

        Image img = new ImageIcon(fajlNev).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(img));

//        this.setText(this.ertek+ "");

        this.revalidate();
        this.repaint();
    }


}
