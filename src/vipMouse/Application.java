package vipMouse;

import credit.Credit;
import parametres.Config;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import menu.Menu;
import menu.MenuAction;
import menu.MenuBarre;
import parametres.ParametresModele;
import parametres.ParametresVue;
import schema.GestionaireFichier;
import schema.SchemaControleur;
import schema.SchemaVue;
import schema.creation.CreationVue;
import schema.creation.SchemaCreationControleur;
import schema.creation.SchemaCreationVue;
import vocal.SpeakToMe;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerInterface;
import vtplayer.VTPlayerManager;
import vtplayer.parametreur.ParametreurControleur;
import vtplayer.parametreur.ParametreurModele;
import vtplayer.parametreur.ParametreurVue;

/**
 * TODO gestion de la barre de menu (contr�leur...) TODO gestion des touche a
 *
 * @author
 */
public class Application extends JFrame implements ActionListener, Observer {

    private final static Dimension MINIMUNSIZE = new Dimension(800, 600);
    protected String savePath;
    protected Config config;
    protected GestionaireFichier gestionaireFichier;
    protected MenuBarre menuBarre;
    protected VTPlayerInterface vtp;
    protected SchemaVue schemaVue;
    protected SchemaControleur schemaControleur;
    protected CreationVue creationVue;
    protected SpeakToMe/*LecteurTexteThread*/ lecteurTexte;
    protected HashMap<Integer, Byte[]> bytesConfig;
    protected HashMap<Integer, String> sentence;
    protected boolean resizeAuto;

    public Application() throws HeadlessException, IOException, VTPlayerException, FileNotFoundException {
        super("Unik Sound Tactil Display");
        setMinimumSize(MINIMUNSIZE);
        setSize(MINIMUNSIZE);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setFocusable(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (closeSchema()) {
                    System.exit(0);
                }
            }
        });


        config = new ParametresModele();
        resizeAuto = config.getAutoResize();
        config.addObserver(this);

        Menu menu = new Menu();
        menu.addActionListener(this);
        setContentPane(menu);

        // Chargement des images n�cessaire � l'application
        gestionaireFichier = new GestionaireFichier(GestionaireFichier.REP_IMAGES + config.getNorme());

        // Chargement des d�pendances de la souris.
        bytesConfig = ParametreurModele.loadPicotFile();
        vtp = VTPlayerManager.getInstance();
        
        if (config.getVTPlayerMouse()) {
            try {
                VTPlayerManager.open(vtp);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement des ressource pour la souris.", "Erreur VTPlayer", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Chargement des d�pendances du son. 
        sentence = ParametreurModele.loadDescription(SpeakToMe.PATH_PHONETIQUE);
        lecteurTexte = new SpeakToMe();/*LecteurTexteThread();*/
        //lecteurTexte.start();

        // cr�ation de la barre de menu.
        menuBarre = new MenuBarre(config);
        menuBarre.addActionListener(this);
        config.addObserver(menuBarre);
        setJMenuBar(menuBarre);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getActionCommand());

        switch (e.getActionCommand()) {
            case MenuAction.AC_OPEN:
                openSchema();
                break;

            case MenuAction.AC_NEW:
                newSchema();
                break;

            case MenuAction.AC_SAVE:
                saveSchema();
                break;

            case MenuAction.AC_SAVEAS:
                saveSchemaAs();
                break;

            case MenuAction.AC_UPTOCREATION:
                upToCreationMode();
                break;

            case MenuAction.AC_DOWNFROMCREATION:
                closeCreationMode();
                break;

            case MenuAction.AC_VTPPREFERENCE:
                vtplayer_preference();
                break;

            case MenuAction.AC_PREFERENCE:
                preference();
                break;
                
            case MenuAction.AC_ABOUT:
                about();
                break;

            case MenuAction.AC_EXIT:
                this.processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
        }
        revalidate();
        repaint();
    }

    /**
     * Ouvre une fen�tre de dialogue permettant a l'utilisateur de choisir ou
     * sauvegarder son fichier.
     *
     * @return vrai si la sauvegarde a bien �t� effectuer, faux sinon
     */
    public boolean saveSchemaAs() {
        if (schemaVue == null) {
            JOptionPane.showMessageDialog(this, "Rien a sauvegarder", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // On d�finit le r�pertoire par d�faut pour ouvrir notre sl�lection de fichier
            JFileChooser f = new JFileChooser(SchemaVue.REP_SAVE);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Unik Sound Tactil Display", GestionaireFichier.EXTENTION_FILE, "txt");
            f.setFileFilter(filter);
            // Si le jFileChooser � fonctionn� alors on peut continuer	
            if (f.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = f.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(GestionaireFichier.EXTENTION_FILE)) {
                    path += "." + GestionaireFichier.EXTENTION_FILE;
                }

                return save(path);
            }
            return false;
        }
        return false;
    }

    /**
     * Sauvegarde le sch�ma dans le fichier pr�c�demment d�finie, si aucun
     * fichier n'a �t� d�finie alors revient au m�me que {@link #saveSchemaAs()
     * }.
     *
     * @return vrai si la sauvegarde a bien �t� effectuer, faux sinon
     */
    public boolean saveSchema() {
        if (schemaVue == null) {
            JOptionPane.showMessageDialog(this, "rien a sauvegarder", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (savePath == null) {
                return saveSchemaAs();
            } else {
                return save(savePath);
            }
        }
        return false;
    }

    /**
     * Sauvegarde le sch�ma.
     *
     * @param path chemin ou sauvegarder
     * @return vrai si la sauvegarde a bien �t� effectuer, faux sinon
     */
    private boolean save(String path) {
        savePath = path;
        Integer tab[][] = schemaVue.getSchemaCode();
        try {
            if (tab != null) {
                GestionaireFichier.saveFileSchema(tab, path);
            }

            schemaVue.setHaveChange(false);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde du fichier.", "Erreur sauvegarde", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * Fermeture du sch�ma
     *
     * @return retourne vrai si il n'y a pas eu de soucis ou si l'utilisateur
     * n'a pas annuler
     */
    private boolean closeSchema() {
        if (schemaVue != null && schemaVue.getHaveChange()) {
            switch (JOptionPane.showConfirmDialog(this, "Voulez-vous sauvegarder ?", "Sauvegarder", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                case JOptionPane.CANCEL_OPTION:
                case JOptionPane.CLOSED_OPTION:
                    return false;

                case JOptionPane.YES_OPTION:
                    return saveSchema();

                case JOptionPane.NO_OPTION:
                    schemaVue.setHaveChange(false);
            }
        }
        return true;
    }

    /**
     * Cr�er l'interface de cr�ation de sch�ma.
     */
    public void newSchema() {
        if (closeSchema()) {
            if (!(schemaVue instanceof SchemaCreationVue) || schemaVue == null) {
                unlink();

                createObjectCreation();

                // enregistrement du controleur aupr�s des autres objet
                link();

                setContentPane(creationVue);
            }

            ((SchemaCreationVue) schemaVue).clear();
            savePath = null;
        }
    }

    /**
     * permet de rendre les objet �lisible au {@link }
     */
    private void unlink() {
        //System.out.println("unlink");
        if (schemaVue != null) {
            config.deleteObserver(schemaControleur);
            menuBarre.removeActionListener(schemaControleur);

            // Si on est en mode cr�ation
            if (schemaVue instanceof SchemaCreationVue) {
                creationVue.removeMouseListener(schemaControleur);
            } else {
                schemaVue.removeMouseListener(schemaControleur);
            }
        }
    }

    /**
     *
     */
    private void link() {
        //System.out.println("link");
        if (schemaVue != null) {
            config.addObserver(schemaControleur);
            menuBarre.addActionListener(schemaControleur);

            if (schemaVue instanceof SchemaCreationVue) {
                creationVue.addMouseListener(schemaControleur);
            } else {
                schemaVue.addMouseListener(schemaControleur);
            }
        }
    }

    /**
     * Cr�er les objets qu'il faut pour le mode cr�ation.
     */
    private void createObjectCreation() {
        // cr�ation des nouvelle instance
        schemaVue = new SchemaCreationVue(gestionaireFichier, resizeAuto);
        creationVue = new CreationVue(gestionaireFichier, (SchemaCreationVue) schemaVue);
        menuBarre.setCreationAction(true);

        // cr�ation du controleur
        schemaControleur = new SchemaCreationControleur(vtp, bytesConfig, creationVue, lecteurTexte, sentence, config);
    }

    /**
     * Cr�er les objets qu'il faut pour le visionnage du sch�ma.
     */
    private void createObjectSchemaVue() {
        creationVue = null;
        schemaVue = new SchemaVue(gestionaireFichier, resizeAuto);
        schemaControleur = new SchemaControleur(vtp, bytesConfig, schemaVue, lecteurTexte, sentence, config);
        menuBarre.setCreationAction(false);
    }

    /**
     * Permet de passer du mode de cr�ation au mode de vue simple. TODO int�gr�
     * la gestion des changements
     */
    private void closeCreationMode() {
        unlink(); // enleve les listeners

        boolean haveChange = schemaVue.getHaveChange();

        // r�cup�re le schema
        Integer tab[][] = schemaVue.getSchemaCode();

        // cr�ation des obejet d'affichage
        createObjectSchemaVue();

        schemaVue.setHaveChange(haveChange);

        // ajout des listeners
        link();

        // mise en place du schema
        if (tab != null) {
            schemaVue.newSchema(tab);
        }

        // ajout de la nouvelle vue
        setContentPane((resizeAuto ? schemaVue : new JScrollPane(schemaVue)));
    }

    private void upToCreationMode() {
        // si il ni a pas encore de schema on en cr�er un nouveu
        if (schemaVue == null) {
            newSchema();
            return;
        }
        // on r�cup�re le tableau de code image
        Integer tab[][] = schemaVue.getSchemaCode();

        // si il ni avais pas de schema
        if (tab == null) {
            newSchema(); // on en cr�er un nouveau
            return;
        }

        // on est vraiment dans le cas qui nous interesse
        unlink(); // On les rend �lisible au ramasse miette

        boolean haveChange = schemaVue.getHaveChange();

        // sinon on cr�er les objets de cr�ation (�dition)
        createObjectCreation();
        // et on met le schema
        schemaVue.newSchema(tab);

        schemaVue.setHaveChange(haveChange);

        // on met les listeners comme il faut
        link();

        // ajout de la vue
        setContentPane(creationVue);
    }

    /**
     * Permet d'ouvrir une JDialog pour choisir un fichier a charger.
     */
    private void openSchema() {
        if (closeSchema()) {
            // On d�finit le r�pertoire par d�faut pour ouvrir notre sl�lection de fichier
            JFileChooser f = new JFileChooser(SchemaVue.REP_SAVE);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Unik Sound Tactil Display", GestionaireFichier.EXTENTION_FILE, "txt");
            f.setFileFilter(filter);
            // Si le jFileChooser � fonctionn� alors on peut continuer	
            if (f.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                open(f.getSelectedFile().getAbsolutePath());
            }
        }
    }

    /**
     * Charge un fichier et l'affiche.
     *
     * @param path fichier a charger
     */
    public void open(String path) {
        // Si l'on a pas encore de sch�ma afficher on cr�er le panel d'affichage
        if (schemaVue == null) {
            createObjectSchemaVue();

            link();

            // ajout de la nouvelle vue
            setContentPane((resizeAuto ? schemaVue : new JScrollPane(schemaVue)));
        }

        try {
            //on affiche alors le nouveau sch�ma
            schemaVue.newSchema(GestionaireFichier.loadFileShemat(path));
            savePath = path;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenu lors de la lecture du fichier.", "Erreur chargement", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Application.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void vtplayer_preference() {
        try {
            ParametreurModele modele = new ParametreurModele();

            ParametreurVue vue = new ParametreurVue(modele);
            ParametreurControleur controleur = new ParametreurControleur(modele, vue, vtp);

            vue.addMouseListener(controleur);
            vue.addActionListener(controleur);

            vue.setPreferredSize(new Dimension(700, 500));

            JButton apply = new JButton("Appliquer");
            apply.setActionCommand(ParametreurVue.ACTION_COMMAND_SAVE);
            apply.addActionListener(controleur);

            Object[] selValues = {"Valider", apply, "Annuler"};

            modele.addObserver(this);
            if (schemaControleur != null) {
                //System.out.println("add observeur to controleur schema");
                modele.addObserver(schemaControleur);
            }

            int ret = JOptionPane.showOptionDialog(this, vue, "Param�trer la configuration de la souris VTPlayer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, selValues, selValues[1]);
            if (ret == JOptionPane.YES_OPTION) {
                try {
                    modele.save();
                } catch (FileNotFoundException ex) {
                    // TODO informer utilisateur
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    // TODO informer utilisateur
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //modele.deleteObservers();
        } catch (FileNotFoundException ex) {
            // TODO informer utilisateur
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | VTPlayerException ex) {
            // TODO informer utilisateur
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void preference() {
        ParametresVue parametresVue = new ParametresVue(this, (ParametresModele) config);
        parametresVue.setVisible(true);
    }

    private void about() {
        JOptionPane.showMessageDialog(this, new Credit(), "A propos", JOptionPane.PLAIN_MESSAGE);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("application update");
        if (o instanceof Config) {
            try {
                if (((Config) o).getVTPlayerMouse()) {
                    if (!vtp.isOpen()) {
                        VTPlayerManager.open(vtp);
                    }
                } else {
                    if (vtp.isOpen()) {
                        vtp.close();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors du chargement des ressource pour la souris.", "Erreur souris VTPlayer", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(Application.class
                        .getName()).log(Level.WARNING, null, ex);
            }
        } else if (o instanceof ParametreurModele) {
            if (arg != null) {
                bytesConfig = (HashMap<Integer, Byte[]>) arg;
            }
        }
    }

    public static void main(String args[]) {
        try {
            Application application = new Application();
            if (args.length >= 1) {
                application.open(args[0]);
            }
        } catch (HeadlessException | VTPlayerException | IOException ex) {
            // TODO informer l'utilisateur des erreurs.
            Logger.getLogger(Application.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
