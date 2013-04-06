package parametres;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;

class KeyInput implements KeyListener {

    int key;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (key != KeyEvent.VK_UNDEFINED) {
           ((JDialog) e.getSource()).dispose();
        }
    }

    public int getKey() {
        return key;
    }
}