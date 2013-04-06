package parametres;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author godeau
 */
public class Test {
    public static void main(String arg[]) throws FileNotFoundException, IOException {
        JFrame frame = new  JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        ParametresModele parametresModele = new ParametresModele();
        ParametresVue parametresVue = new  ParametresVue(frame, parametresModele);
        parametresVue.setVisible(true);
        System.out.println("hello world");
    }

}
