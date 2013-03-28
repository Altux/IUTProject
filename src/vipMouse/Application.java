package vipMouse;

import config.Config;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import menu.MenuBarre;
import schema.GestionaireFichier;
import schema.SchemaControleur;
import schema.SchemaVue;
import schema.creation.CreationVue;
import vocal.LecteurTexteThread;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerInterface;
import vtplayer.parametreur.ParametreurModele;

/**
 *
 * @author
 */
public class Application extends JFrame implements ActionListener {

    protected Config config;
    protected GestionaireFichier gestionaireFichier;
    protected MenuBarre menuBarre;
    protected VTPlayerInterface vtp;
    protected SchemaVue schemaVue;
    protected SchemaControleur schemaControleur;
    protected CreationVue creationVue;
    protected LecteurTexteThread lecteurTexte;
    protected HashMap<Integer, Byte[]> bytesConfig;
    protected HashMap<Integer, String> sentence;

    public Application(String path) throws HeadlessException, IOException, VTPlayerException, FileNotFoundException {
        setSize(800, 600);
        setTitle("Titre a choisir");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        bytesConfig = ParametreurModele.loadPicotFile();
        sentence = ParametreurModele.loadDescription(LecteurTexteThread.PATH_PHONETIQUE);

        lecteurTexte = new LecteurTexteThread();
        lecteurTexte.start();

        menuBarre = new MenuBarre();
        setJMenuBar(menuBarre);

        gestionaireFichier = new GestionaireFichier(GestionaireFichier.REP_IMAGES + config.getNorme());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // TODO change body of generated methods.
    }

    public static void main(String args[]) {
        try {
            Application application = new Application((args.length > 1) ? args[1] : null);
        } catch (HeadlessException | VTPlayerException | IOException ex) {
            // TODO informer l'utilisateur des erreurs.
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
