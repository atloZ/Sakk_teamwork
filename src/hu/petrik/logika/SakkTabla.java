package hu.petrik.logika;

public class SakkTabla {

    private int[][] tabla;

    public SakkTabla(){
        this.tabla = new int[][]
            {
                {22,23,24,25,26,24,23,22},
                {21,21,21,21,21,21,21,21},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                {11,11,11,11,11,11,11,11},
                {12,13,14,15,16,14,13,12}
            };
    }

    public int getErtek(int x, int y){
        return this.tabla[x][y];
    }

    public void lep(int sx, int sy, int dx, int dy){
        if (isErvenyesLepes(sx,sy,dx,dy)){
            this.tabla[dx][dy] = this.tabla[sx][sy];
            this.tabla[sx][sy] = 0;
        }
    }

    public boolean isKivalasztottFigura(int x, int y, int figura){
        return this.tabla[x][y] == figura;
    }

    public boolean isUresHely(int x, int y){
        return this.isKivalasztottFigura(x,y,0);
    }

    public boolean isFigura(int x, int y){
        return !isUresHely(x,y);
    }

    public boolean isVilagosFigura(int x, int y){
        return  this.getErtek(x,y) >= 11 && this.getErtek(x,y) <= 16;
    }

    public boolean isSotetFigura(int x, int y){
        return  this.getErtek(x,y) >= 21 && this.getErtek(x,y) <= 26;
    }

    public int getFigurakSzama(int n, int m){
        int db = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (this.getErtek(i,j) >= n && this.getErtek(i,j) <= m){
                    db++;
                }
            }
        }
        return db;
    }

    public int getVilagosFigurakSzama(){
        return getFigurakSzama(11,16);
    }

    public int getSotetFigurakSzama(){
        return getFigurakSzama(21,26);
    }

    public int getFigurakSzama() {
        return this.getVilagosFigurakSzama() + this.getSotetFigurakSzama();
    }

    public boolean isErvenyesLepes(int sx, int sy, int dx, int dy){
        if (isFigura(sx,sy)) {
            if (isKivalasztottFigura(sx, sy, 11) || isKivalasztottFigura(sx, sy, 21)) {
                return isErvenyesGyalogLepes(sx, sy, dx, dy);
            } else if (isKivalasztottFigura(sx, sy, 12) || isKivalasztottFigura(sx, sy, 22)) {
                return isErvenyesBastyaLepes(sx,sy,dx,dy);
            } else if (isKivalasztottFigura(sx, sy, 13) || isKivalasztottFigura(sx, sy, 23)) {
                return isErvenyesHuszarLepes(sx,sy,dx,dy);
            }
        }

        return false;
    }

    public boolean isMatt(){
        return true;
    }

    public boolean isSakk(){
        return true;
    }

    public boolean isErvenyesGyalogLepes(int sx, int sy, int dx, int dy){
        boolean isHelyesVilagosLepes = false;
        boolean isHelyesSotetLepes = false;

        if (isVilagosFigura(sx,sy)) {
            boolean kezdoLepesVilagossal = sy == dy && sx == 6 && sx - dx <= 2 && isUresHely(dx, dy);
            boolean eloreLepesVilagossal = sy == dy && sx - dx == 1 && isUresHely(dx, dy);
            boolean utesVilagossal = isSotetFigura(dx, dy) && sx - dx == 1 && Math.abs(sy - dy) == 1;

            isHelyesVilagosLepes = kezdoLepesVilagossal || eloreLepesVilagossal || utesVilagossal;
        }else if (isSotetFigura(sx,sy)) {
            boolean kezdoLepesSotettel = sy == dy && sx == 1 && dx - sx <= 2 && isUresHely(dx, dy);
            boolean eloreLepesSotettel = sy == dy && dx - sx == 1 && isUresHely(dx, dy);
            boolean utessotettel = isVilagosFigura(dx, dy) && dx - sx == 1 && Math.abs(sy - dy) == 1;

            isHelyesSotetLepes = kezdoLepesSotettel || eloreLepesSotettel || utessotettel;
        }

        return  isHelyesVilagosLepes || isHelyesSotetLepes;
    }

    public boolean isEllenfelFigurajatUtiE(int sx, int sy, int dx, int dy){
        boolean vilagossalSotetet = isVilagosFigura(sx,sy) && isSotetFigura(dx,dy);
        boolean sotettelVilagosat = isSotetFigura(sx,sy) && isVilagosFigura(dx,dy);
        return  vilagossalSotetet || sotettelVilagosat;
    }

    public boolean isErvenyesBastyaLepes(int sx, int sy, int dx, int dy) {

        boolean vizszintesenLepE = sx == dx && Math.abs(sy-dy) > 0;
        boolean fuggolegesenLepE = Math.abs(sx-dx) > 0 && sy == dy;
        boolean bastyaLepes = vizszintesenLepE || fuggolegesenLepE;

        if (bastyaLepes && (isUresHely(dx,dy) || isEllenfelFigurajatUtiE(sx,sy,dx,dy))){
            if (vizszintesenLepE){
                for (int i = Math.min(sy,dy); i< Math.max(sy,dy); i++){
                    if (isFigura(sx,i)){
                        return false;
                    }
                }
            }else if (fuggolegesenLepE){
                for (int i = Math.min(sx,dx); i< Math.max(sx,dx); i++){
                    if (isFigura(i,sy)){
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }

    public boolean isErvenyesHuszarLepes(int sx, int sy, int dx, int dy){
        boolean huszarLepes = (Math.abs(sx-dx) == 1 && Math.abs(sy-dy) == 2) ||
                (Math.abs(sx-dx) == 2 && Math.abs(sy-dy) == 1);

        return huszarLepes && (isUresHely(dx,dy) || isEllenfelFigurajatUtiE(sx,sy,dx,dy));
    }

    @Override
    public String toString() {
        String s = "";

        for (int i=0; i< 8; i++){
            for (int j=0; j< 8; j++){
                s += String.format("%2d ", this.getErtek(i,j));
            }
            s += "\n";
        }

        return s;
    }
}
