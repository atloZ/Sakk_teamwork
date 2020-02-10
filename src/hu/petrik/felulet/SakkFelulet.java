package hu.petrik.felulet;

import hu.petrik.logika.SakkTabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class SakkFelulet extends JFrame {

    private Container foAblak;
    private JPanel panelSakkTabla;
    private JPanel panelOldalAllapot;
    private JPanel panelAlso;

    private JLabel labelStopper;
    private JLabel labelLepesek;
    private JLabel labelAktualisJatekos;
    private JLabel labelJatekos;
    private JLabel labelFeketeBabukSzama;
    private JLabel labelFeherBabukSzama;

    private JList listLepesek;
    private DefaultListModel listModel;

    private long stopperInditas;

    private Timer stopper;

    private SakkTabla tabla;

    private SakkTablaElem forrasElem, celElem;

    public SakkFelulet(){
        this.tabla = new SakkTabla();
        initComponets();
        sakkTablaMegjelenit();
    }

    public void TimerTick(){
        this.stopper = new Timer();
        this.stopper.schedule(new TimerTask() {

            @Override
            public void run() {
                int ms = 0;
                while (true){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    labelStopper.setText(timerFormater(ms));
                    ms++;
                }
            }
        },0,100);
    }

    private String timerFormater(int l) {
        final long hr = TimeUnit.MILLISECONDS.toHours(l);
        final long min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
    }

    private void initComponets(){
        this.setTitle("Sakk 1.0");

        int szelesseg = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int magassag = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setSize(szelesseg,magassag-50);

        this.setLocationRelativeTo(null);

        this.foAblak = getContentPane();
        this.foAblak.setLayout(new BorderLayout(19,15));

        this.panelSakkTabla = new JPanel();
        this.panelSakkTabla.setLayout(new GridLayout(9,9));

        this.labelStopper = new JLabel();
        this.labelStopper.setText("00:00.000");
        this.labelStopper.setSize(400,40);
        this.labelStopper.setLayout(new BorderLayout());

        this.panelOldalAllapot = new JPanel();//Oldal panel létrehozása
        this.panelOldalAllapot.setLayout(new BorderLayout());//Oldal panel mérete
        this.panelOldalAllapot.add(this.labelStopper);
        //this.panelOldalAllapot.add(this.labelAktualisJatekos);
        //this.panelOldalAllapot.add(this.labelLepesek);
        this.foAblak.add(this.panelOldalAllapot,BorderLayout.EAST);

        this.labelFeherBabukSzama = new JLabel();
        this.labelFeherBabukSzama.setText("Fehér - " + this.tabla.getVilagosFigurakSzama());

        this.labelFeketeBabukSzama = new JLabel();
        this.labelFeketeBabukSzama.setText("Fekete - " + this.tabla.getSotetFigurakSzama());

        this.panelAlso = new JPanel();
        this.panelAlso.setLayout(new BorderLayout());
        this.panelAlso.add(this.labelFeherBabukSzama);
        this.panelAlso.add(this.labelFeketeBabukSzama);
        this.foAblak.add(this.panelAlso, BorderLayout.SOUTH);

        this.foAblak.add(this.panelSakkTabla, BorderLayout.CENTER);

        this.stopperInditas = (new Date().getTime());
        this.TimerTick();
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
            aktualisElem.setBackground(Color.decode("#ABB2B9"));
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

            if ((aktualisElem.getPozicioY() + aktualisElem.getPozicioX()) % 2 == 0){
                this.setBackground(Color.decode("#FFFFFF"));
            }else{
                this.setBackground(Color.decode("#8B4513"));
            }

            forrasElem = null;
            celElem = null;
        }
    }
}
