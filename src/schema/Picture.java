package schema;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;

/**
 *
 * @author groupe interface
 */
public class Picture extends JComponent {

    /**
     * code de l'image.
     */
    protected int code;
    /**
     * image représentative de l'élément.
     */
    protected Image image;
    /**
     * position de l'image.
     */
    protected int lig;
    /**
     * position de l'image.
     */
    protected int col;

    /**
     *
     *
     * @param image image représentent l'élément
     * @param code code associé a l'élément
     */
    public Picture(Image image, int code) {
        this.code = code;
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }

    /**
     * @param image
     * @param code
     */
    public Picture(Image image, int code, int lig, int col) {
        this(image, code);
        this.lig = lig;
        this.col = col;
    }

    /**
     * @param g : un graphic on peut redimensionner la taille de l'image
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension dimImage = getPreferredSize();
        g.drawImage(image, 0, 0, dimImage.width, dimImage.height, null);
    }

    /**
     *
     * @return code de image
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @return une image
     */
    public Image getImage() {
        return image;
    }

    /**
     *
     * @return ligne de la position de l'image
     */
    public int getLig() {
        return lig;
    }

    /**
     *
     * @return colonne de la position de l'image
     */
    public int getCol() {
        return col;
    }
}
