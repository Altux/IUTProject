package schema;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vocal.LecteurTexteThread;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerInterface;
import vtplayer.ByteUtilitaire;

/**
 *
 * @author Charles,Thang-tung, Qianlin
 */
public class SchemaControleur extends MouseAdapter implements KeyListener {

    protected VTPlayerInterface vtp;
    protected HashMap<Integer, Byte[]> bytes;
    protected LecteurTexteThread stm;
    protected HashMap<Integer, String> sentence;
    protected Picture lastEntered = null;
    protected SchemaVue sv;

    public SchemaControleur(VTPlayerInterface vtp, HashMap<Integer, Byte[]> bytes, SchemaVue shV, LecteurTexteThread stm, HashMap<Integer, String> sentence) {
        this.vtp = vtp;
        this.bytes = bytes;
        this.stm = stm;
        this.sentence = sentence;
        this.sv = shV;
    }

    protected final void vtpSet(int code) {
        // si VTPlayer est brancher et fonctionnel
        if (vtp != null && vtp.isOpen()) {

            Byte b[] = bytes.get(code);
            if (b == null) {
                b = VTPlayerInterface.NULLBYTES;
            }

            try {
                vtp.set(b);
            } catch (VTPlayerException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur VTPlayer", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(SchemaControleur.class.getName()).log(Level.WARNING, null, ex);
            }
        }

        if (stm != null && sentence != null) {
            stm.setTexte(sentence.get(code / 100));
            //System.out.println("we try to read the text " + sentence.get(code));
        }

        System.out.println(code);
    }

    protected Point getZone() {
        // on rÈcupÈre le nombres totale de lignes et colonnes de l'image
        int nbcoltotal = sv.getNbCol();
        int nbligtotal = sv.getNbLigne();

//        int[] position_zone = new int[2];
//
//        //int zone_col;
//        //int zone_lig;
//        // on rÈcupÈre la position de l'image point√© par le curseur 
//        int col_Image = lastEntered.getCol() - 1;
//        int lig_Image = lastEntered.getLig() - 1;

        // si le nombre totale de colonne n'est pas un multiple de 4
        // on incrÈmente jusqu'‡† atteindre un multiple de 4
        while (nbcoltotal % 4 != 0) {
            nbcoltotal++;
        }

        // si le nombre totale de ligne n'est pas un multiple de 4
        // on incrÈmente jusqu'‡† atteindre un multiple de 4
        while (nbligtotal % 4 != 0) {
            nbligtotal++;
        }

        return new Point((lastEntered.getLig() - 1) / Math.round(nbligtotal / 4), (lastEntered.getCol() - 1) / Math.round(nbcoltotal / 4));

//        //on cherche combien d'image (porte) peut comptenir une zone 
//        int nbcol_zone = Math.round(nbcoltotal / 4);
//        int nblign_zone = Math.round(nbligtotal / 4);
//
//
//        //on v√©rifie dans quelle zone ce trouve notre image
//        zone_col = col_Image / nbcol_zone;
//        zone_lig = lig_Image / nblign_zone;
//
//        position_zone[0] = zone_lig;
//        position_zone[1] = zone_col;
//
//        //System.out.println("coordonn√© de la zone --> ligne :" + position_zone[1] + " colonne : " + position_zone[0]);
//
//        return (position_zone);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof Picture) {
            lastEntered = (Picture) o;
            vtpSet(lastEntered.getCode());
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C && lastEntered != null) {
            //System.out.println(" ligne :" + lastEntered.getLig() + " colonne : " + lastEntered.getCol());
            //int[] position_zone = getZone();
            Point position_zone = getZone();

            //pour avoir la colonne tableau [0] pour la ligne tableau [1]
            //position de la zone avec le synth√®se vocal :
            stm.positionnement(position_zone/*[0], position_zone[1]*/);
            //System.out.println("l = " + position_zone[0] + " c = " + position_zone[1]);
            try {
                //position de la zone avec la souri : 
                vtp.setRight(ByteUtilitaire.position_to_byte(position_zone), 1000);
            } catch (VTPlayerException ex) {
                // TODO informer l'utilisateur de l'erreur
                Logger.getLogger(SchemaControleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}