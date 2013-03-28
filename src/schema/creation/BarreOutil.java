package schema.creation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
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
        
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = constraints.gridy = 0;
        constraints.ipady = 3;
        
        Picture nullP = new Picture(gf.getPicture(GestionaireFichier.EMPTY_PICTURE), GestionaireFichier.EMPTY_PICTURE);
        pictures.add(nullP);
        add(nullP, constraints);
        
        for (Map.Entry<Integer, Image> entry : gf.getPictures().entrySet()) {
            Integer code = entry.getKey();
            if (code % 10 == 1) { // TODO changer car maintenant 4 chiffres
                constraints.gridy++;
                Picture p = new Picture(entry.getValue(), code );
                pictures.add(p);
                add(p, constraints);
            }
        }
    }
    
    /**
     * 
     * @param code
     * @return 
     */
    public Picture getNewPicture(Integer code){
        return new Picture(gf.getPicture(code), code);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        for (Picture picture : pictures) {
            picture.addMouseListener(l);
        }
    }
}
