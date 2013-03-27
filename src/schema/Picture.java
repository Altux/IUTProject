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
      * code : code des images
      * image : une image
      * lig : position de l'image (ligne)
      * col : position de l'image (colonne)
      */
    protected Integer code;
    protected Image image;
    protected int lig;
    protected int col;

    public Picture(Image image, Integer code) {
        this.code = code;
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
    }
    
    /**
     * @param image
     * @param code
     * Constructeur d'image avec un code et une image 
     */
    public Picture(Image image, Integer code, int lig, int col) {
        this(image, code);
        this.lig = lig;
        this.col = col;
    }
    /** 
     * @param g : un graphic
     * on peut redimensionner la taille de l'image
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
    public Integer getCode() {
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
    public int getLig(){
        return lig;
    }
    /**
     * 
     * @return colonne de la position de l'image
     */
    public int getCol(){
        return col;
    }
}
