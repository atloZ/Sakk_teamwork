package hu.petrik.felulet;

import hu.petrik.logika.SakkTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SakkFelulet extends JFrame {

    private Container foAblak;
    private JPanel panelSakkTabla;

    private SakkTabla tabla;

    private SakkTablaElem forrasElem, celElem;

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

                elem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        elemKattintas(e);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });

                panelSakkTabla.add(elem);
            }
        }

        this.revalidate();
        this.repaint();
    }

    public void elemKattintas(MouseEvent me){
        SakkTablaElem aktualisElem = (SakkTablaElem) me.getSource();

        if (forrasElem == null && celElem == null && aktualisElem.getErtek() != 0){
            forrasElem = aktualisElem;
            System.out.println("Forras:" + forrasElem);
        }else if (forrasElem != null && celElem == null && aktualisElem != forrasElem){
            celElem = aktualisElem;
            System.out.println("Cel" + celElem);
        }

        if (forrasElem != null && celElem != null){
            int sx = forrasElem.getPozicioX();
            int sy = forrasElem.getPozicioY();

            int dx = celElem.getPozicioX();
            int dy = celElem.getPozicioY();

            tabla.lep(sx,sy,dx,dy);

            forrasElem.setErtek(tabla.getErtek(sx,sy));
            celElem.setErtek(tabla.getErtek(dx,dy));

            forrasElem = null;
            celElem = null;
        }


    }


}
