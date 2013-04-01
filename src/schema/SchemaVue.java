package schema;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author groupe interface
 */
public class SchemaVue extends JPanel implements ComponentListener {

    public final static String REP_SAVE = "./save/";
    protected int nbLigne = 0;
    protected int nbCol = 0;
    protected GestionaireFichier gf;
    protected ArrayList<Picture> pictures = new ArrayList<>();
    protected MouseListener mouseListener = null;
    protected boolean resizeAuto;
    protected boolean haveChange;


    /**
     *
     * @param gf
     * @param resizeAuto
     */
    public SchemaVue(GestionaireFichier gf, boolean resizeAuto) {
        this.gf = gf;
        this.resizeAuto = resizeAuto;
        this.haveChange = false;
        setLayout(new GridBagLayout());
        addComponentListener(this);
    }

    /**
     *
     * @param gf
     * @deprecated
     */
    public SchemaVue(GestionaireFichier gf) {
        this(gf, true);
    }

    /**
     *
     * @param gf
     * @param arrayList
     * @param resizeAuto
     * @deprecated
     */
    public SchemaVue(GestionaireFichier gf, ArrayList<Integer[]> arrayList, boolean resizeAuto) {
        this(gf, resizeAuto);
        newSchema(arrayList);
    }

    /**
     *
     * @param gf
     * @param arrayList
     * @deprecated
     */
    public SchemaVue(GestionaireFichier gf, ArrayList<Integer[]> arrayList) {
        this(gf, arrayList, true);
    }

    public void newSchema(ArrayList<Integer[]> arrayList) {
        // on vide le panel et le tableau de picture
        removeAll();
        pictures.clear();

        // récupére la taille de la grille
        nbLigne = arrayList.size();
        nbCol = arrayList.get(0).length;

        // mise en place du layout
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = constraints.gridy = 0;
        for (Integer[] integers : arrayList) {
            constraints.gridx = 0;
            for (Integer integer : integers) {
                addPicture(new Picture(gf.getPicture(integer), integer, constraints.gridy, constraints.gridx), constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
        }

        if (mouseListener != null) {
            addMouseListener(mouseListener);
        }

        calculateOptimalSize(getSize());
    }

    public void newSchema(Integer schema[][]) {
        // on vide le panel et le tableau de picture
        removeAll();
        pictures.clear();

        // récupére la taille de la grille
        nbLigne = schema.length;
        nbCol = schema[0].length;

        // mise en place du layout
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = constraints.gridy = 0;
        for (Integer[] integers : schema) {
            constraints.gridx = 0;
            for (Integer integer : integers) {
                addPicture(new Picture(gf.getPicture(integer), integer, constraints.gridy, constraints.gridx), constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
        }

        if (mouseListener != null) {
            addMouseListener(mouseListener);
        }

        calculateOptimalSize(getSize());
    }

    /**
     *
     * @param p Instance de la classe Picture
     * @param constraints
     */
    protected void addPicture(Picture p, GridBagConstraints constraints) {
        p.setMaximumSize(p.getPreferredSize());
        pictures.add(p);
        add(p, constraints);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        mouseListener = l;
        for (Picture picture : pictures) {
            picture.addMouseListener(l);
        }
    }

    @Override
    public synchronized void removeMouseListener(MouseListener l) {
        mouseListener = null;
        for (Picture picture : pictures) {
            picture.removeMouseListener(l);
        }
    }

    protected void calculateOptimalSize(Dimension dim) {
        if (resizeAuto && dim.height > 1 && dim.width > 1) {
            int maxSize = pictures.get(0).getMaximumSize().height;

            int newSize;

            if (dim.height / nbLigne < dim.width / nbCol) {
                newSize = dim.height / nbLigne;
            } else {
                newSize = dim.width / nbCol;
            }

            if (newSize > maxSize) {
                newSize = maxSize;
            }

            Dimension d = new Dimension(newSize, newSize);
            for (Picture picture : pictures) {
                picture.setPreferredSize(d);
            }
            revalidate();
        }
    }

    public int getNbLigne() {
        return nbLigne;
    }

    public int getNbCol() {
        return nbCol;
    }

    public Integer[][] getSchemaCode() {
        if (nbLigne > 0 && nbCol > 0) {
            Integer tab[][] = new Integer[nbLigne][nbCol];

            for (Picture picture : pictures) {
                tab[picture.getLig()][picture.getCol()] = picture.getCode();
            }

            return tab;
        }
        return null;
    }

    public boolean getResizeAuto() {
        return resizeAuto;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (pictures != null && !pictures.isEmpty()) {
            calculateOptimalSize(getSize());
        }
    }

    public void setHaveChange(boolean haveChange) {
        this.haveChange = haveChange;
    }

    public boolean getHaveChange() {
        return haveChange;
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
