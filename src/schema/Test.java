package schema;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JFrame;
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
        HashMap<Integer, Byte[]> hashBytes = new HashMap();
        ParametreurModele.loadPicotFile(hashBytes);
        HashMap<Integer, String> sentence = new HashMap();
        LecteurTexteThread lecteurTexte = new LecteurTexteThread();
        lecteurTexte.start();
        ParametreurModele.loadDescription(sentence, LecteurTexteThread.PATH_PHONETIQUE);

        GestionaireFichier gf = new GestionaireFichier(GestionaireFichier.REP_IMAGES);
        SchemaMenu schemaMenu = new SchemaMenu();

        SchemaVue schema = new SchemaVue(gf, GestionaireFichier.loadFileShemat("./text.txt"));
        
        SchemaControleur controleur = new SchemaControleur(null/*vtp*/, hashBytes, schema, lecteurTexte, sentence);
        MenuControleur mc = new MenuControleur(schemaMenu, schema);
        schema.addMouseListener(controleur);
        //schema.addKeyListener(controleur);
        schemaMenu.addActionListener(mc);


        JFrame frame = new JFrame("Test");
        frame.setJMenuBar(schemaMenu);
        frame.addKeyListener(controleur);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(schema);
        frame.setVisible(true);
    }
}
