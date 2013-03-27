package schema;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vocal.LecteurTexteThread;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerInterface;

/**
 *
 * @author GODEAU Quentin
 */
public class SchemaControleurInterface extends MouseAdapter implements KeyListener {
    protected VTPlayerInterface vtp;
    protected HashMap<Integer, Byte[]> bytes;
    protected LecteurTexteThread stm;
    protected HashMap<Integer, String> sentence;

    protected SchemaControleurInterface(VTPlayerInterface vtp, HashMap<Integer, Byte[]> bytes, LecteurTexteThread stm, HashMap<Integer, String> sentence) {
        this.vtp = vtp;
        this.bytes = bytes;
        this.stm = stm;
        this.sentence = sentence;
    }
    
    protected final void vtpSet(int code) {
        // si VTPlayer est brancher et fonctionnel
        if (vtp != null && vtp.isOpen()) {

            Byte b[] = bytes.get(code);
            if (b == null) {
                b = VTPlayerInterface.NULLBYTES;
            }

            try {
                vtp.set(b);
            } catch (VTPlayerException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur VTPlayer", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(SchemaControleur.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        
        if(stm != null && sentence != null){
            stm.setTexte(sentence.get(code));
            System.out.println("we try to read the text " + sentence.get(code));
        }
        
        System.out.println(code);
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        Object o = e.getSource();
        if (o instanceof Picture) {
            vtpSet(((Picture) o).getCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
