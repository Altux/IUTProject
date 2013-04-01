package schema.creation;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import menu.MenuAction;

/**
 * Ne pas utiliser encore.
 *
 * @author
 */
public class SchemaCreationBarreAction extends JPanel implements CreationAction, MenuAction {

    private final static String PATH_NEW = "ico/new.png";
    private final static String PATH_DELETE = "ico/delete.png";
    private final static String PATH_SAVE = "ico/save.png";
    private final static String PATH_ROTATE = "ico/rotate.png";
    private final static String PATH_OPEN = "ico/open.png";

    protected JButton nouveau;
    protected JButton ouvrir;
    protected JButton sauvegarder;
    protected JButton rotation;
    protected JButton supprimer;

    /**
     *
     */
    public SchemaCreationBarreAction() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        nouveau = new JButton("Nouveau", new ImageIcon(PATH_NEW));
        nouveau.setActionCommand(AC_NEW);
        add(nouveau);

        ouvrir = new JButton("Ouvrir", new ImageIcon(PATH_OPEN));
        ouvrir.setActionCommand(AC_OPEN);
        add(ouvrir);

        sauvegarder = new JButton("Sauvegarder", new ImageIcon(PATH_SAVE));
        sauvegarder.setActionCommand(AC_SAVE);
        add(sauvegarder);

        rotation = new JButton("Rotation", new ImageIcon(PATH_ROTATE));
        rotation.setActionCommand(AC_ROTATE);
        add(rotation);

        supprimer = new JButton("Supprimer", new ImageIcon(PATH_DELETE));
        supprimer.setActionCommand(AC_DELETE);
        add(supprimer);
    }

    /**
     *
     * @param l
     */
    public synchronized void addActionListener(ActionListener l) {
        nouveau.addActionListener(l);
        ouvrir.addActionListener(l);
        sauvegarder.addActionListener(l);
        rotation.addActionListener(l);
        supprimer.addActionListener(l);
    }
    
    public synchronized void removeActionListener(ActionListener l){
        nouveau.removeActionListener(l);
        ouvrir.removeActionListener(l);
        sauvegarder.removeActionListener(l);
        rotation.removeActionListener(l);
        supprimer.removeActionListener(l);
    }
}
