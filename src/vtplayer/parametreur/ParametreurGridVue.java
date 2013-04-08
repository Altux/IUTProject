package vtplayer.parametreur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import vtplayer.ByteUtilitaire;

/**
 *
 * @author 
 */
class ParametreurGridVue extends JPanel {

    /**
     * 
     */
    protected class Picot extends JComponent {

        private boolean bit = false;

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Dimension d = getSize();
            drawCircle(g, d.width / 2, d.height / 2, ((d.width < d.height) ? d.width / 2 : d.height / 2) - 3);
        }

        public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
            if (bit) {
                cg.fillOval(xCenter - r, yCenter - r, 2 * r, 2 * r);
            } else {
                cg.drawOval(xCenter - r, yCenter - r, 2 * r, 2 * r);
            }

        }

        public void setBit(boolean bit) {
            this.bit = bit;
        }

        public void setBit() {
            bit = !bit;
        }

        public boolean getBit() {
            return bit;
        }
    }
    private static int SQUARE = 4;
    private Picot picots[][] = new Picot[SQUARE][SQUARE];

    public ParametreurGridVue() {

        setLayout(new GridLayout(SQUARE, SQUARE));
        for (int cptL = 0; cptL < SQUARE; cptL++) {
            for (int cptC = 0; cptC < SQUARE; cptC++) {
                picots[cptL][cptC] = new Picot();
                add(picots[cptL][cptC]);
            }
        }
        setBackground(Color.WHITE);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        for (int cptL = 0; cptL < SQUARE; cptL++) {
            for (int cptC = 0; cptC < SQUARE; cptC++) {
                picots[cptL][cptC].addMouseListener(l);
            }
        }
    }

    public void setBytes(Byte b1, Byte b2) {
        String str = ByteUtilitaire.byte_hexa_to_bin_string(b1);

        int cpt = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                picots[i][j].setBit(str.charAt(cpt) == '0');
                cpt++;
            }
        }

        for (int i = SQUARE - 1; i > 1; i--) {
            for (int j = 0; j < 2; j++) {
                picots[i][j].setBit(str.charAt(cpt) == '0');
                cpt++;
            }
        }

        str = ByteUtilitaire.byte_hexa_to_bin_string(b2);
        cpt = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 2; j < SQUARE; j++) {
                picots[i][j].setBit(str.charAt(cpt) == '0');
                cpt++;
            }
        }

        for (int i = SQUARE - 1; i > 1; i--) {
            for (int j = 2; j < SQUARE; j++) {
                picots[i][j].setBit(str.charAt(cpt) == '0');
                cpt++;
            }
        }
    }

    public Byte[] getBytes() {
        Byte[] bytes = new Byte[2];
        String str;

        str = "";
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                str += (picots[i][j].getBit() ? '0' : '1');
            }
        }

        for (int i = SQUARE - 1; i > 1; i--) {
            for (int j = 0; j < 2; j++) {
                str += (picots[i][j].getBit() ? '0' : '1');
            }
        }

        bytes[0] = (byte) (Byte.parseByte(str.substring(0, 4), 2) * 16 + Byte.parseByte(str.substring(4, 8), 2));


        str = "";
        for (int i = 0; i < 2; i++) {
            for (int j = 2; j < SQUARE; j++) {
                str += (picots[i][j].getBit() ? '0' : '1');
            }
        }

        for (int i = SQUARE - 1; i > 1; i--) {
            for (int j = 2; j < SQUARE; j++) {
                str += (picots[i][j].getBit() ? '0' : '1');
            }
        }

        bytes[1] = (byte) (Byte.parseByte(str.substring(0, 4), 2) * 16 + Byte.parseByte(str.substring(4, 8), 2));

        return bytes;
    }
}
