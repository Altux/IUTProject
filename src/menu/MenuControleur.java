package menu;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Icinhoo
 */
public class MenuControleur extends MouseAdapter implements ActionListener {

    Menu menu;

    public MenuControleur(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("Hello world");
        Object o = e.getSource();
        if (o instanceof JButton) {
            ((JButton) o).setForeground(Color.WHITE);
            ((JButton) o).repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof JButton) {
            ((JButton) o).setForeground(Color.decode("#04FF46"));
            ((JButton) o).repaint();
        }
    }
}
