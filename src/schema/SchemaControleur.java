package schema;

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
public class SchemaControleur extends SchemaControleurInterface implements KeyListener {

    protected Picture lastEntered = null;
    protected SchemaVue sv;

    public SchemaControleur(VTPlayerInterface vtp, HashMap<Integer, Byte[]> bytes, SchemaVue shV, LecteurTexteThread stm, HashMap<Integer, String> sentence) {
        super(vtp, bytes, stm, sentence);
        this.sv = shV;
    }

    protected int[] getZone() {
        // on récupère le nombres totale de lignes et colonnes de l'image
        int nbcoltotal = sv.getNbCol();
        int nbligtotal = sv.getNbLigne();

        int[] position_zone = new int[2];

        int zone_col;
        int zone_lig;
        //on récupère la position de l'image pointé par le curseur 
        int col_Image = lastEntered.getCol() - 1;
        int lig_Image = lastEntered.getLig() - 1;

        //si le nombre totale de colonne n'est pas un multiple de 4
        // on incrémente jusqu'à atteindre un multiple de 4
        while (nbcoltotal % 4 != 0) {

            nbcoltotal++;
        }

        //si le nombre totale de ligne n'est pas un multiple de 4
        // on incrémente jusqu'à atteindre un multiple de 4
        while (nbligtotal % 4 != 0) {

            nbligtotal++;
        }

        //on cherche combien d'image (porte) peut comptenir une zone 
        int nbcol_zone = Math.round(nbcoltotal / 4);
        int nblign_zone = Math.round(nbligtotal / 4);


        //on vérifie dans quelle zone ce trouve notre image
        zone_col = col_Image / nbcol_zone;
        zone_lig = lig_Image / nblign_zone;

        position_zone[0] = zone_lig;
        position_zone[1] = zone_col;

        //System.out.println("coordonné de la zone --> ligne :" + position_zone[1] + " colonne : " + position_zone[0]);

        return (position_zone);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        Object o = e.getSource();
        if (o instanceof Picture) {
            lastEntered = (Picture) o;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C && lastEntered != null) {
            //System.out.println(" ligne :" + lastEntered.getLig() + " colonne : " + lastEntered.getCol());
            int[] position_zone = getZone();

            //pour avoir la colonne tableau [0] pour la ligne tableau [1]
            //position de la zone avec le synthèse vocal :
            stm.positionnement(position_zone[0], position_zone[1]);
            //System.out.println("l = " + position_zone[0] + " c = " + position_zone[1]);
            try {
                //position de la zone avec la souri : 
                vtp.setRight(ByteUtilitaire.position_to_byte(position_zone), 1000);
            } catch (VTPlayerException ex) {
                Logger.getLogger(SchemaControleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}