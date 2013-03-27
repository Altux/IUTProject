package schema;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author groupe interface
 */
public class SchemaMenu extends JMenuBar {

    public final static String AC_OPEN = "#OPEN";
    public final static String AC_EXIT = "#EXIT";
    protected ArrayList<JMenuItem> jMenus = new ArrayList();

    /**
     *
     */
    public SchemaMenu() {
        JMenu jMenu = new JMenu("File");
        jMenu.setMnemonic(KeyEvent.VK_F);


        add(jMenu);
        jMenus.add(jMenu);


        JMenuItem open = new JMenuItem("Open file");
        open.setMnemonic(KeyEvent.VK_O);
        open.setActionCommand(AC_OPEN);


        jMenu.add(open);
        jMenus.add(open);


        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setActionCommand(AC_EXIT);


        jMenu.add(exit);
        jMenus.add(exit);


    }

    /**
     *
     * @param l ajoute pour chaque menu un listener afin de savoir ce qui ce
     * passe comme action sur le menu
     */
    public void addActionListener(ActionListener l) {

        for (int cpt = 0; cpt < jMenus.size(); cpt++) {
            jMenus.get(cpt).addActionListener(l);

        }
    }
}
