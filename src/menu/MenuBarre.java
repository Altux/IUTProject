package menu;

import parametres.Config;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import schema.SchemaAction;
import schema.creation.CreationAction;

/**
 *
 * @author groupe integration
 */
public class MenuBarre extends JMenuBar implements MenuAction, CreationAction, SchemaAction, Observer {

    JMenuItem spatialization;
    JMenuItem rotate;
    JMenuItem del;
    JMenuItem changeBit;
    JMenuItem escape;
    JMenuItem switchCreationSchema;
    final String up = "Éditer";
    final String down = "Éditer fin";
    ArrayList<JMenuItem> menuItems = new ArrayList<>();

    public MenuBarre(Config config, boolean c) {
        JMenu fichier = new JMenu("Fichier");
        fichier.setMnemonic(KeyEvent.VK_F);
        add(fichier);

        JMenuItem newS = new JMenuItem("Nouveau");
        newS.setMnemonic(KeyEvent.VK_N);
        newS.setActionCommand(AC_NEW);
        newS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        fichier.add(newS);
        menuItems.add(newS);

        JMenuItem open = new JMenuItem("Ouvrir");
        open.setMnemonic(KeyEvent.VK_O);
        open.setActionCommand(AC_OPEN);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        fichier.add(open);
        menuItems.add(open);

        JMenuItem save = new JMenuItem("Enregistrer");
        save.setMnemonic(KeyEvent.VK_E);
        save.setActionCommand(AC_SAVE);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        fichier.add(save);
        menuItems.add(save);

        JMenuItem saveAs = new JMenuItem("Enregistrer sous");
        saveAs.setMnemonic(KeyEvent.VK_S);
        saveAs.setActionCommand(AC_SAVEAS);
        fichier.add(saveAs);
        menuItems.add(saveAs);

        JMenuItem exit = new JMenuItem("Quitter");
        exit.setMnemonic(KeyEvent.VK_Q);
        exit.setActionCommand(AC_EXIT);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        fichier.add(exit);
        menuItems.add(exit);

        /*
         * *******************************************************************
         */
        JMenu edit = new JMenu("Edition");
        edit.setMnemonic(KeyEvent.VK_E);
        add(edit);

        spatialization = new JMenuItem("Spatialisation");
        spatialization.setMnemonic(KeyEvent.VK_S);
        spatialization.setActionCommand(AC_SPATIALIZATION);
        spatialization.setAccelerator(KeyStroke.getKeyStroke(config.getKeySpatialization(), 0));
        edit.add(spatialization);
        menuItems.add(spatialization);

        JMenuItem vtp_preference = new JMenuItem("VTPlayer préférence");
        vtp_preference.setMnemonic(KeyEvent.VK_V);
        vtp_preference.setActionCommand(AC_VTPPREFERENCE);
        edit.add(vtp_preference);
        menuItems.add(vtp_preference);

        JMenuItem preference = new JMenuItem("Préférence");
        preference.setMnemonic(KeyEvent.VK_P);
        preference.setActionCommand(AC_PREFERENCE);
        preference.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        edit.add(preference);
        menuItems.add(preference);

        /**
         * *******************************************************************
         */
        JMenu creation = new JMenu("Création");
        creation.setMnemonic(KeyEvent.VK_C);
        add(creation);

        rotate = new JMenuItem("Rotation");
        rotate.setMnemonic(KeyEvent.VK_R);
        rotate.setAccelerator(KeyStroke.getKeyStroke(config.getKeyRotation(), 0));
        rotate.setActionCommand(AC_ROTATE);
        rotate.setEnabled(c);
        creation.add(rotate);
        menuItems.add(rotate);

        del = new JMenuItem("Suppression");
        del.setMnemonic(KeyEvent.VK_S);
        del.setAccelerator(KeyStroke.getKeyStroke(config.getKeySuppresion(), 0));
        del.setActionCommand(AC_DELETE);
        del.setEnabled(c);
        creation.add(del);
        menuItems.add(del);

        changeBit = new JMenuItem("Changer bit");
        changeBit.setAccelerator(KeyStroke.getKeyStroke(config.getKeyChangeBit(), 0));
        changeBit.setActionCommand(AC_CHANGEBIT);
        changeBit.setEnabled(c);
        creation.add(changeBit);
        menuItems.add(changeBit);

        escape = new JMenuItem("Échapement");
        escape.setAccelerator(KeyStroke.getKeyStroke(config.getKeyEchapement(), 0));
        escape.setActionCommand(AC_ESCAPE);
        escape.setEnabled(c);
        creation.add(escape);
        menuItems.add(escape);

        switchCreationSchema = new JMenuItem(up);
        //switchCreationSchema.setAccelerator(null);
        //switchCreationSchema.setMnemonic(WIDTH);
        switchCreationSchema.setActionCommand(AC_UPTOCREATION);
        creation.add(switchCreationSchema);
        menuItems.add(switchCreationSchema);

        /*
         * *******************************************************************
         */
        JMenu help = new JMenu("Aide");
        help.setMnemonic(KeyEvent.VK_A);
        add(help);


        JMenuItem manuel = new JMenuItem("Aide");
        manuel.setMnemonic(KeyEvent.VK_A);
        manuel.setActionCommand(AC_HELP);
        help.add(manuel);
        menuItems.add(manuel);

        JMenuItem about = new JMenuItem("A propos");
        about.setMnemonic(KeyEvent.VK_A);
        about.setActionCommand(AC_ABOUT);
        help.add(about);
        menuItems.add(about);
    }

    /**
     *
     */
    public MenuBarre(Config config) {
        this(config, false);
    }

    /**
     * Ajoute un action listener sur les items du menu.
     *
     * @param l action listener a ajouter
     */
    public synchronized void addActionListener(ActionListener l) {
        for (JMenuItem item : menuItems) {
            item.addActionListener(l);
        }
    }

    /**
     * Supprime un action listener des items du menu.
     *
     * @param l action listener a supprimer
     */
    public synchronized void removeActionListener(ActionListener l) {
        for (JMenuItem item : menuItems) {
            item.removeActionListener(l);
        }
    }

    /**
     * Verrouille ou déverrouille les actions du mode de création.
     *
     * @param b vrai pour déverrouiller, faux sinon.
     */
    public void setCreationAction(boolean b) {
        rotate.setEnabled(b);
        del.setEnabled(b);
        changeBit.setEnabled(b);
        escape.setEnabled(b);
        if (b) {
            switchCreationSchema.setActionCommand(AC_DOWNFROMCREATION);
            switchCreationSchema.setText(down);
        } else {
            switchCreationSchema.setActionCommand(AC_UPTOCREATION);
            switchCreationSchema.setText(up);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("barre menu update");
        if (o instanceof Config) {
            Config config = (Config) o;
            rotate.setAccelerator(KeyStroke.getKeyStroke(config.getKeyRotation(), 0));
            del.setAccelerator(KeyStroke.getKeyStroke(config.getKeySuppresion(), 0));
            changeBit.setAccelerator(KeyStroke.getKeyStroke(config.getKeyChangeBit(), 0));
            escape.setAccelerator(KeyStroke.getKeyStroke(config.getKeyEchapement(), 0));
            spatialization.setAccelerator(KeyStroke.getKeyStroke(config.getKeySpatialization(), 0));
        }
    }
}
