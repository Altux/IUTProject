package menu;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import schema.ActionCommande;

/**
 *
 * @author groupe interface
 */
public class MenuBarre extends JMenuBar implements ActionCommande {
    
    JMenuItem save = new JMenuItem("Enregistrer");
    JMenuItem saveAs = new JMenuItem("Enregistrer sous");
    ArrayList<JMenuItem> menuItems = new ArrayList<>();
    
    public MenuBarre(boolean creation) {
        JMenu fichier = new JMenu("Fichier");
        fichier.setMnemonic(KeyEvent.VK_F);
        add(fichier);
        
        JMenuItem newS = new JMenuItem("Nouveau");
        newS.setMnemonic(KeyEvent.VK_N);
        newS.setActionCommand(AC_NEW);
        newS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        fichier.add(newS);
        menuItems.add(newS);
        
        JMenuItem open = new JMenuItem("Ouvrire");
        open.setMnemonic(KeyEvent.VK_O);
        open.setActionCommand(AC_OPEN);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        fichier.add(open);
        menuItems.add(open);
        
        save.setMnemonic(KeyEvent.VK_E);
        save.setActionCommand(AC_SAVE);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        save.setEnabled(creation);
        fichier.add(save);
        menuItems.add(save);
        
        saveAs.setMnemonic(KeyEvent.VK_S);
        saveAs.setActionCommand(AC_SAVE);
        saveAs.setEnabled(creation);
        fichier.add(saveAs);
        menuItems.add(saveAs);
        
        JMenuItem exit = new JMenuItem("Quitter");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setActionCommand(AC_EXIT);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        fichier.add(exit);
        menuItems.add(exit);

        /*
         * *******************************************************************
         */
        JMenu edit = new JMenu("Edition");
        edit.setMnemonic(KeyEvent.VK_E);
        add(edit);
        
        JMenuItem preference = new JMenuItem("Préférence");
        preference.setMnemonic(KeyEvent.VK_P);
        preference.setActionCommand(AC_PREFERENCE);
        preference.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        edit.add(preference);
        menuItems.add(preference);

        /*
         * *******************************************************************
         */
        JMenu help = new JMenu("Aide");
        help.setMnemonic(KeyEvent.VK_A);
        add(help);
        
        JMenuItem about = new JMenuItem("A propos");
        about.setMnemonic(KeyEvent.VK_A);
        about.setActionCommand(AC_ABOUT);
        help.add(about);
        menuItems.add(about);
    }

    /**
     *
     */
    public MenuBarre() {
        this(false);
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
    void setCreationAction(boolean b) {
        save.setEnabled(b);
        saveAs.setEnabled(b);
    }
}
