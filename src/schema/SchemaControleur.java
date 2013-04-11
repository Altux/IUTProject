package schema;

import parametres.Config;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import vocal.SpeakToMe;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerInterface;
import vtplayer.ByteUtilitaire;
import vtplayer.parametreur.ParametreurModele;

/**
 *
 * @author Charles,Thang-tung, Qianlin
 */
public class SchemaControleur extends MouseAdapter implements Observer, ActionListener {

    protected VTPlayerInterface vtp;
    protected HashMap<Integer, Byte[]> bytes;
    protected SpeakToMe stm;
    protected HashMap<Integer, String> sentence;
    protected Picture lastEntered = null;
    protected SchemaVue sv;
    protected boolean son;
    protected int frequence;

    public SchemaControleur(VTPlayerInterface vtp, HashMap<Integer, Byte[]> bytes, SchemaVue shV, SpeakToMe stm, HashMap<Integer, String> sentence, Config configuration) {
        this.vtp = vtp;
        this.bytes = bytes;
        this.stm = stm;
        this.sentence = sentence;
        this.sv = shV;
        // TODO get configuration
        son = configuration.getSon();
        frequence = configuration.getFrequencePicots();
    }

    protected final void vtpSet(final int code) {
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

        if (son && stm != null && sentence != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    stm.setTexte(sentence.get(code / 100));
                    stm.playAll();
                    //System.out.println("we try to read the text " + sentence.get(code));
                }
            });
        }

        System.out.println(code);
    }

    protected Point getZone() {
        // on récupére le nombres totale de lignes et colonnes de l'image
        int nbcoltotal = sv.getNbCol();
        int nbligtotal = sv.getNbLigne();

        // si le nombre totale de colonne n'est pas un multiple de 4
        // on incrémente jusqu'à  atteindre un multiple de 4
        while (nbcoltotal % 4 != 0) {
            nbcoltotal++;
        }

        // si le nombre totale de ligne n'est pas un multiple de 4
        // on incrémente jusqu'à  atteindre un multiple de 4
        while (nbligtotal % 4 != 0) {
            nbligtotal++;
        }

        return new Point(lastEntered.getLig() / Math.round(nbligtotal / 4), lastEntered.getCol() / Math.round(nbcoltotal / 4));
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
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof Picture) {
            lastEntered = null;
            vtpSet(GestionaireFichier.EMPTY_PICTURE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SchemaAction.AC_SPATIALIZATION) && lastEntered != null) {
            //System.out.println(" ligne :" + lastEntered.getLig() + " colonne : " + lastEntered.getCol());
            //int[] position_zone = getZone();
            final Point position_zone = getZone();

            //pour avoir la colonne tableau [0] pour la ligne tableau [1]
            //position de la zone avec le synthése vocal :
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("read position");
                    if (stm != null) {
                        stm.positionnement(position_zone);
                        stm.playAll();
                    }
                }
            });

            //System.out.println("l = " + position_zone[0] + " c = " + position_zone[1]);
            if (vtp != null && vtp.isOpen()) {
                try {
                    //position de la zone avec la souri : 
                    vtp.setRight(ByteUtilitaire.position_to_byte(position_zone), frequence);
                } catch (VTPlayerException ex) {
                    // TODO informer l'utilisateur de l'erreur
                    Logger.getLogger(SchemaControleur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Config) {
            Config config = (Config) o;
            son = config.getSon();
            frequence = config.getFrequencePicots();
        } else if (o instanceof ParametreurModele && arg instanceof HashMap) {
            bytes = (HashMap<Integer, Byte[]>) arg;
        }
    }
}
