package schema.creation;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import schema.GestionaireFichier;
import schema.Picture;
import schema.SchemaControleur;
import vocal.LecteurTexteThread;
import vtplayer.VTPlayerInterface;

/**
 *
 */
public class SchemaCreationControleur extends SchemaControleur implements ActionListener {

    /**
     * Image en cours de placement.
     */
    protected Picture newPicture = null;
    protected CreationVue creationVue;
    protected PictureCreation old_focused = null;
    protected GestionaireFichier gf;
    protected Toolkit tk = Toolkit.getDefaultToolkit();

    public SchemaCreationControleur(VTPlayerInterface vtp, HashMap<Integer, Byte[]> bytes, CreationVue cv, LecteurTexteThread lecteurTexte, HashMap<Integer, String> sentence) {
        super(vtp, bytes, cv.getSchemaCreationVue(), lecteurTexte, sentence);
        creationVue = cv;
        gf = cv.getGestionaireFichier();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();

        if (o instanceof Picture) {
            Picture picture = (Picture) o;
            Integer i = null;
            // Si c'est une instance de PictureCreation on est sur d'être dans 
            // SchemaCreationVue
            if (picture instanceof PictureCreation) {
                PictureCreation pc = (PictureCreation) picture;
                // si l'on est en mode édition
                if (newPicture != null) {
                    i = newPicture.getCode();
                    // on remplace l'image est le code de celle-ci par celle séléctionner
                    pc.setCode(i);
                    pc.setImage(newPicture.getImage());
                } else {
                    // sinon on met le focus dessus
                    setFocusOn(pc);
                }
            } else {
                // on enleve le fucus sur tout les éléments de SchemaCreationVue
                setFocusOn(null);

                newPicture = creationVue.getBarreOutil().getNewPicture(picture.getCode());
                // on remplace le curseur de la souris par celui de l'image
                Cursor curseur = tk.createCustomCursor(newPicture.getImage(), new Point(1, 1), "Pointeur");
                sv.setCursor(curseur);
            }
            if (i != null) {
                vtpSet(i);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        Object o = e.getSource();

        if (o instanceof PictureCreation) {
            enteredOn((PictureCreation) o);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        Integer i = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                escape();
                break;

            case KeyEvent.VK_DELETE:
                i = delete();
                break;

            case KeyEvent.VK_R:
                i = rotation();
                break;
        }
        if (i != null) {
            vtpSet(i);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*Integer i = null;
        switch (e.getActionCommand()) {
            case CreationVue.ACTION_DELETE:
                i = creationVue.delete();
                break;

            case CreationVue.ACTION_NEW:


            case CreationVue.ACTION_OPEN:

            case CreationVue.ACTION_ROTATE:
                i = creationVue.rotation();
                break;

            case CreationVue.ACTION_SAVE:
                try {
                    creationVue.save();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SchemaCreationControleur.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SchemaCreationControleur.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        if (i != null) {
            vtpSet(i);
        }*/
        //creationVue.requestFocus();
    }

    /**
     * Permet de définir quel élément a le focus.
     *
     * @param pc élément auquel le focus doit être attribuer.
     */
    public void setFocusOn(PictureCreation pc) {
        // si une image avez déjà le focus, on lui enléve
        if (old_focused != null) {
            old_focused.setFocus(false);
        }

        // si l'utilisateur a de nouveau cliqué sur l'image qui a le focus
        // on enléve le focus de celle-ci
        if (old_focused == pc && old_focused != null) {
            pc.setFocus(false);
            old_focused = null;
            return;
        }

        // on met le focus sur la nouvelle image
        if (pc != null) {
            pc.setFocus(true);
        }

        // on garde en mémoire qu'elle image a le focus
        old_focused = pc;
    }
    
    /**
     * Si {@see #newPicture} n'est pas nul l'image aura le focus afin de permettre 
     * a l'utilisateur de mieux visualiser ou il click.
     * 
     * @param pc l'image ce trouvent sous le pointeur de la souris
     */
    public void enteredOn(PictureCreation pc) {
        if (newPicture != null) {
           setFocusOn(pc);
        }
    }
    
    public Integer delete() {
        if (old_focused != null) {
            old_focused.setCode(GestionaireFichier.EMPTY_PICTURE);
            old_focused.setImage(gf.getPicture(GestionaireFichier.EMPTY_PICTURE));
            if (newPicture == null) {
                setFocusOn(null);
            }
            return GestionaireFichier.EMPTY_PICTURE;
        }
        return null;
    }
    
    public void escape() {
        newPicture = null;
        setFocusOn(null);
        ((SchemaCreationVue) sv).setCursor(Cursor.getDefaultCursor());
    }
    
    public Integer rotation() {
        Integer i = null;
        if (newPicture != null) {
            i = newPicture.getCode();

            if (i != GestionaireFichier.EMPTY_PICTURE) {
                if (i % 10 == GestionaireFichier.ROTATION) {
                    i -= GestionaireFichier.ROTATION;
                }
                i++;
                newPicture = new Picture(gf.getPicture(i), i);

                Cursor cursor = tk.createCustomCursor(newPicture.getImage(), new Point(1, 1), "Pointeur");
                ((SchemaCreationVue) sv).setCursor(cursor);
            }
        } else {
            if (old_focused != null) {
                i = old_focused.getCode();

                if (i != GestionaireFichier.EMPTY_PICTURE) {
                    if (i % 10 == GestionaireFichier.ROTATION) {
                        i -= GestionaireFichier.ROTATION;
                    }

                    i++;

                    old_focused.setCode(i);
                    old_focused.setImage(gf.getPicture(i));
                }
            }
        }
        return i;
    }
}
