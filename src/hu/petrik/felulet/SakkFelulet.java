package hu.petrik.felulet;

import hu.petrik.logika.SakkTabla;

import javax.swing.*;
import java.awt.*;

public class SakkFelulet extends JFrame {

    private Container foAblak;
    private JPanel panelSakkTabla;

    private SakkTabla tabla;

    public SakkFelulet(){
        this.tabla = new SakkTabla();
        initComponets();
        sakkTablaMegjelenit();
    }

    private void initComponets(){
        this.setTitle("Sakk 1.0");
//        this.setSize(1024,768);

        int szelesseg = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int magassag = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setSize(szelesseg,magassag-50);

        this.setLocationRelativeTo(null);

        this.foAblak = getContentPane();
        this.foAblak.setLayout(new BorderLayout(10,10));

        this.panelSakkTabla = new JPanel();
        this.panelSakkTabla.setLayout(new GridLayout(9,9));

        this.foAblak.add(this.panelSakkTabla, BorderLayout.CENTER);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void sakkTablaMegjelenit(){
        this.panelSakkTabla.removeAll();

        this.panelSakkTabla.add(new JLabel(""));
        for (int i=0; i<8; i++){
            JLabel hivatkozasABC = new JLabel();
            hivatkozasABC.setText(String.format("%s.",(char)(i+65)));
            hivatkozasABC.setHorizontalAlignment(SwingConstants.CENTER);
            hivatkozasABC.setVerticalAlignment(SwingConstants.CENTER);
            this.panelSakkTabla.add(hivatkozasABC);
        }

        for (int i=0; i<8; i++){
            JLabel hivatkozas123 = new JLabel();
            hivatkozas123.setText(String.format("%d.",8-i));
            hivatkozas123.setHorizontalAlignment(SwingConstants.CENTER);
            hivatkozas123.setVerticalAlignment(SwingConstants.CENTER);
            this.panelSakkTabla.add(hivatkozas123);

            for (int j=0; j<8; j++){
                SakkTablaElem elem = new SakkTablaElem(i,j,this.tabla.getErtek(i,j));
                panelSakkTabla.add(elem);
            }
        }

        this.revalidate();
        this.repaint();
    }


}
