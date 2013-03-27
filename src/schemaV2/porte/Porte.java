package schemaV2.porte;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 *
 */
abstract public class Porte extends JComponent {

    //protected static final int MAX_PREVIOUS = 2;
    protected Integer code;
    protected Image image;
    protected boolean focus = false;
    //protected Picture next = null;
    //protected Picture previous[] = new Picture[MAX_PREVIOUS];
    //protected int cptPrev = 0;
    protected static Border border = BorderFactory.createLineBorder(Color.BLACK);
    protected int maxRotation;
    protected int numRotation;
    protected int gridLayoutX;
    protected int gridLayoutY;
    protected final static String PATH = "./porte/";

    protected Porte(String nom, int code, int maxRotation) {
        //PATH + nom;
    }

    /*
     * public Porte(Image image, Integer code, int maxRotation) { setCode(code);
     * setImage(image); setMaxRotation(maxRotation);
     *
     * setPreferredSize(new Dimension(image.getWidth(null),
     * image.getHeight(null)));
    }
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension dimImage = getPreferredSize();
        g.drawImage(image, 0, 0, dimImage.width, dimImage.height, null);
        if (focus) {
            border.paintBorder(this, g, 0, 0, dimImage.width, dimImage.height);
        }
    }

    public void rotate() {
        int tmp = getGridLayoutY();
        setGridLayoutY(getGridLayoutX());
        setGridLayoutX(tmp);
// TODO

    }

    public Integer getCode() {
        return code + numRotation;
    }

    public Image getImage() {
        return image;
    }

    /**
     * Permet de savoir si l'image a le focus ou non.
     *
     * @return vrai si l'image a le focus faux sinon.
     */
    @Override
    public boolean hasFocus() {
        return focus;
    }

    //abstract public Porte[] getNext();
    abstract public Porte[] getPrevious();

    abstract public boolean getResult();

    public int getGridLayoutX() {
        return gridLayoutX;
    }

    public int getGridLayoutY() {
        return gridLayoutY;

    }

    public int getMaxRotation() {
        return maxRotation;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setImage(Image image) {
        gridLayoutY = image.getHeight(null) / 10;
        gridLayoutX = image.getWidth(null) / 10;

        this.image = image;
    }

    /**
     * Donne ou enlève le focus a l'image.
     *
     * @param focus vrai si l'on veut donner le focus, faux sinon.
     */
    public void setFocus(boolean focus) {
        this.focus = focus;
        repaint();
    }

    abstract public void addNext(Porte p);

    abstract public void addPrevious(Porte p, int index) throws IndexOutOfBoundsException;/*
     * {
     * if (cptPrev >= MAX_PREVIOUS) { throw new IndexOutOfBoundsException(); }
     *
     * previous[cptPrev] = picture;
     *
     * cptPrev++;
    }
     */


    public void setMaxRotation(int maxRotation) {
        if (this.maxRotation < maxRotation) {
            numRotation = 0;
        }
        this.maxRotation = maxRotation;
    }

    public void setRotate(int rotate) {
        for (int cpt = 1; cpt < rotate; cpt++) {
            rotate();
        }
    }

    protected void setGridLayoutX(int x) {
        gridLayoutX = x;
    }

    protected void setGridLayoutY(int y) {
        gridLayoutY = y;
    }
}
