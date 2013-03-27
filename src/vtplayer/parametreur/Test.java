package vtplayer.parametreur;

import vtplayer.VTPlayerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import vtplayer.VTPlayerInterface;
import vtplayer.VTPlayerManager;

/**
 *
 * @author godeau
 */
public class Test {

    public static void main(String args[]) throws FileNotFoundException, IOException, VTPlayerException {
        VTPlayerInterface vtp = VTPlayerManager.getInstance();
        VTPlayerManager.open(vtp);
        
        JFrame frame = new JFrame();

        ParametreurModele modele = new ParametreurModele();
        ParametreurVue vue = new ParametreurVue(modele);
        ParametreurControleur controleur = new ParametreurControleur(modele, vue, vtp);

        vue.addMouseListener(controleur);
        vue.addActionListener(controleur);

        frame.add(vue);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
