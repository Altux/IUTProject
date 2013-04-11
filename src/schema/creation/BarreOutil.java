package schema.creation;

import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import schema.GestionaireFichier;
import schema.Picture;

/**
 *
 */
public class BarreOutil extends JPanel {

    protected GestionaireFichier gf;
    protected ArrayList<Picture> pictures = new ArrayList<>();

    /**
     *
     * @param gf
     */
    public BarreOutil(GestionaireFichier gf) {
        this.gf = gf;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Picture nullP = new Picture(gf.getPicture(GestionaireFichier.EMPTY_PICTURE), GestionaireFichier.EMPTY_PICTURE);
        add(nullP);
        pictures.add(nullP);
        
        SortedSet<Integer> keys = new TreeSet<>(gf.getPictures().keySet());
        
        for (Integer code : keys) {
            if (((code / 10) % 10 == 1 || (code / 10) % 10 == 0) && code % 10 > 0) {
                Picture p = new Picture(gf.getPicture(code), code);
                pictures.add(p);
                add(p);
                add(Box.createVerticalStrut(3));
            }
        }

        add(Box.createVerticalGlue());
    }

    /**
     *
     * @param code
     * @return
     */
    public Picture getNewPicture(Integer code) {
        return new Picture(gf.getPicture(code), code);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        for (Picture picture : pictures) {
            picture.addMouseListener(l);
        }
    }

    @Override
    public synchronized void removeMouseListener(MouseListener l) {
        for (Picture picture : pictures) {
            picture.removeMouseListener(l);
        }
    }
}
