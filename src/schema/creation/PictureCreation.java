package schema.creation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import schema.Picture;

/**
 * 
 */
public class PictureCreation extends Picture {
    /**
     * Permet d'indiquer si l'image a le focus ou non.
     */
    protected boolean focus = false;
    /**
     * Bordure afficher si l'élément a le focus.
     */
    protected static Border border = BorderFactory.createLineBorder(Color.BLACK);
    
    public PictureCreation(Image image, Integer code, int lig, int col) {
        super(image, code, lig, col);
    }

    /**
     * Remplace le code associé a l'image par celui mis en paramètre.
     * 
     * @param code nouveau code.
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Remplace l'image par celle mise en paramètre.
     * 
     * @param image nouvelle image a afficher.
     */
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(focus){
            border.paintBorder(this, g, 0, 0, this.getWidth(), this.getHeight());
        }
    }
    
    /**
     * Donne ou enlève le focus a l'image.
     * 
     * @param focus vrai si l'on veut donner le focus, faux sinon.
     */
    public void setFocus(boolean focus){
        this.focus = focus;
        repaint();
    }
    
    /**
     * Permet de savoir si l'image a le focus ou non.
     * 
     * @return vrai si l'image a le focus faux sinon.
     */
    @Override
    public boolean hasFocus(){
        return focus;
    }
}
