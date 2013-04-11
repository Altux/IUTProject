package schema.creation;

import parametres.Config;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import schema.GestionaireFichier;
import schema.Picture;
import schema.SchemaControleur;
import vocal.SpeakToMe;
import vtplayer.VTPlayerInterface;

/**
 *
 */
public class SchemaCreationControleur extends SchemaControleur {

    /**
     * Image en cours de placement.
     */
    protected Picture newPicture = null;
    /**
     *
     */
    protected CreationVue creationVue;
    /**
     * L'élément aillant le focus.
     */
    protected PictureCreation old_focused = null;
    /**
     * modèle.
     */
    protected GestionaireFichier gf;
    protected Toolkit tk = Toolkit.getDefaultToolkit();
    protected int escape;
    protected int delete;
    protected int rotation;
    protected int changebit;

    public SchemaCreationControleur(VTPlayerInterface vtp, HashMap<Integer, Byte[]> bytes, CreationVue cv, /*LecteurTexteThread*/ SpeakToMe lecteurTexte, HashMap<Integer, String> sentence, Config configuration) {
        super(vtp, bytes, cv.getSchemaCreationVue(), lecteurTexte, sentence, configuration);
        creationVue = cv;
        gf = cv.getGestionaireFichier();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();

        if (o instanceof Picture) {
            Integer i = clickedOn((Picture) o);
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
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Integer i = null;
        switch (e.getActionCommand()) {
            case CreationAction.AC_DELETE:
                i = delete();
                break;

            case CreationAction.AC_ROTATE:
                i = rotation();
                break;

            case CreationAction.AC_ESCAPE:
                escape();
                break;

            case CreationAction.AC_CHANGEBIT:
                i = changeBit();
                break;
        }
        if (i != null) {
            vtpSet(i);
        }
    }

    /**
     * Permet de définir quel élément a le focus.
     *
     * @param pc élément auquel le focus doit être attribuer.
     */
    protected void setFocusOn(PictureCreation pc) {
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
     * Si {@link #newPicture} n'est pas nul l'image aura le focus afin de
     * permettre a l'utilisateur de mieux visualiser ou il click.
     *
     * @param pc l'image ce trouvent sous le pointeur de la souris
     */
    protected void enteredOn(PictureCreation pc) {
        if (newPicture != null) {
            setFocusOn(pc);
        }
    }

    protected Integer delete() {
        if (old_focused != null) {
            old_focused.setCode(GestionaireFichier.EMPTY_PICTURE);
            old_focused.setImage(gf.getPicture(GestionaireFichier.EMPTY_PICTURE));
            if (newPicture == null) {
                setFocusOn(null);
            }
            sv.setHaveChange(true);
            return GestionaireFichier.EMPTY_PICTURE;
        }
        return null;
    }

    protected void escape() {
        newPicture = null;
        setFocusOn(null);
        creationVue.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Fonction permettant charger l'image après une rotation
     *
     * @return integer(i) : Code de l'image
     */
    protected Integer rotation() {
        Integer i = null;
        // Si l'image est differente de null
        if (newPicture != null) {

            // on récupère le code de l'image
            i = newPicture.getCode();

            // Si le code de l'image est différent de 0
            if (i != GestionaireFichier.EMPTY_PICTURE) {
                // Déclaration d'une variable de type image
                Image image;

//                for (i = (((((int) (i / 10 + 1)) * 10) + i % 10)); i < 10; i = (((((int) (i / 10 + 1)) * 10)))) {
//                    
//                }

                //On incrémente de 1 le 3e chiffre du code qui est celui de la rotation.
                i = (((((int) (i / 10 + 1)) * 10) + i % 10));

                // Si il n'y a pas d'image, on récupère l'image de base en changeant le chiffre de la rotation à 1
                if ((image = gf.getPicture(i)) == null) {
                    i = (((int) (i / 100)) * 10 + 1) * 10 + i % 10;
                    image = gf.getPicture(i);
                }

                newPicture = new Picture(image, i);

                // On change l'image du curseur
                Cursor cursor = tk.createCustomCursor(newPicture.getImage(), new Point(1, 1), "Pointeur");
                creationVue.setCursor(cursor);
            }
        } else {
            if (old_focused != null) {
                i = old_focused.getCode();

                // Si le code de l'image est différent de 0
                if (i != GestionaireFichier.EMPTY_PICTURE) {
                    // Déclaration d'une variable de type image
                    Image image;
                    //On incrémente de 1 le 3e chiffre du code qui est celui de la rotation
                    i = ((((int) i / 10 + 1) * 10) + i % 10);

                    // Si il n'y a pas d'image, on récupère l'image de base en changeant le chiffre de la rotation à 1
                    if ((image = gf.getPicture(i)) == null) {
                        i = (((int) (i / 100)) * 10 + 1) * 10 + i % 10;
                        image = gf.getPicture(i);
                    }

                    old_focused.setCode(i);
                    old_focused.setImage(image);
                    sv.setHaveChange(true);
                }
            }
        }
        return i;
    }

    /**
     * Fonction qui permet de modifier le bit du code de l'image
     *
     * @return integer(i): Code de l'image
     */
    protected Integer changeBit() {
        Integer i = null;
        // Si l'image est differente de null
        if (newPicture != null) {

            // on récupère le code de l'image
            i = newPicture.getCode();

            // Si le code de l'image est différent de 0
            if (i != GestionaireFichier.EMPTY_PICTURE && (i % 10) != GestionaireFichier.CODE_PORTE) {

                //On change le dernier chiffre du code en 1 ou 0
                i = ((int) (i / 10)) * 10 + ((i % 10 == 1) ? 0 : 1);

                //Recupère l'image associée au code après avoir effectuée une rotation
                Image image = gf.getPicture(i);

                newPicture = new Picture(image, i);

                // On change l'image du curseur
                Cursor cursor = tk.createCustomCursor(newPicture.getImage(), new Point(1, 1), "Pointeur");
                creationVue.setCursor(cursor);
            }
        } else {
            if (old_focused != null) {
                i = old_focused.getCode();

                // Si le code de l'image est différent de 0
                if (i != GestionaireFichier.EMPTY_PICTURE && (i % 10) != GestionaireFichier.CODE_PORTE) {

                    //On change le dernier chiffre du code en 1 ou 0
                    i = ((int) (i / 10)) * 10 + ((i % 10 == 1) ? 0 : 1);

                    //Recupère l'image associée au code après avoir effectuée une rotation
                    Image image = gf.getPicture(i);

                    // On place le focus sur l'image
                    old_focused.setCode(i);
                    old_focused.setImage(image);
                    sv.setHaveChange(true);
                }
            }
        }
        return i;
    }

    protected Integer clickedOn(Picture picture) {
        Integer i = null;
        // Si c'est une instance de PictureCreation on est sur d'être dans 
        // SchemaCreationVue
        if (picture instanceof PictureCreation) {
            PictureCreation pc = (PictureCreation) picture;
            // si l'on est en mode édition
            if (newPicture != null) {
                i = newPicture.getCode();
                // on remplace l'image est le code de celle-ci par celle séléctionnée
                pc.setCode(i);
                pc.setImage(newPicture.getImage());
            } else {
                // sinon on met le focus dessus
                setFocusOn(pc);
            }
        } else {
            // on enleve le focus sur tous les éléments de SchemaCreationVue
            setFocusOn(null);

            newPicture = creationVue.getBarreOutil().getNewPicture(picture.getCode());
            // on remplace le curseur de la souris par celui de l'image
            Cursor curseur = tk.createCustomCursor(newPicture.getImage(), new Point(1, 1), "Pointeur");
            creationVue.setCursor(curseur);
        }
        return i;
    }
}
