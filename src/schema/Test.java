package schema;

import menu.MenuBarreControleur;
import menu.MenuBarre;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import vocal.LecteurTexteThread;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerInterface;
import vtplayer.VTPlayerManager;
import vtplayer.parametreur.ParametreurModele;

/**
 *
 * @author groupe interface
 */
public class Test {

    public static void main(String args[]) throws InterruptedException, FileNotFoundException, IOException, VTPlayerException {
        //VTPlayerInterface vtp = VTPlayerManager.getInstance();
        //VTPlayerManager.open(vtp);
        HashMap<Integer, Byte[]> hashBytes = ParametreurModele.loadPicotFile();
        HashMap<Integer, String> sentence = ParametreurModele.loadDescription(LecteurTexteThread.PATH_PHONETIQUE);
        LecteurTexteThread lecteurTexte = new LecteurTexteThread();
        lecteurTexte.start();


        GestionaireFichier gf = new GestionaireFichier(GestionaireFichier.REP_IMAGES);
        MenuBarre schemaMenu = new MenuBarre();

        SchemaVue schema = new SchemaVue(gf, GestionaireFichier.loadFileShemat("./cercle.txt"));

        SchemaControleur controleur = new SchemaControleur(null/*vtp*/, hashBytes, schema, lecteurTexte, sentence);
        MenuBarreControleur mc = new MenuBarreControleur(schemaMenu, schema);
        schema.addMouseListener(controleur);
        //schema.addKeyListener(controleur);
        schemaMenu.addActionListener(mc);


        JFrame frame = new JFrame("Test");
        frame.setJMenuBar(schemaMenu);
        frame.addKeyListener(controleur);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(schema);
        //frame.add(new JScrollPane(schema));
        frame.setVisible(true);
    }
}
