package schema.creation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import schema.GestionaireFichier;

/**
 *
 */
public class CreationVue extends JPanel {

    /**
     * Action dans le cas d'une demande de nouveau schéma.
     * 
     * @see #newSchema() 
     */
    public final static String ACTION_NEW = "#NEW";    
    /**
     * Action dans le cas d'une demande de suppression.
     * 
     * @see #delete() 
     * @see KeyEvent#VK_DELETE
     */
    public final static String ACTION_DELETE = "#DELETE";
    /**
     * Action dans le cas d'une demande de sauvegarde.
     * 
     * @see #saveAs() 
     * @see #save() 
     */
    public final static String ACTION_SAVE = "#SAVE";
    /**
     * Action dans le cas d'une demande de rotation.
     * 
     * @see KeyEvent#VK_R
     * @see #rotation() 
     */
    public final static String ACTION_ROTATE = "#ROTATE";
    /**
     * Action dans le cas d'une demande d'ouverture d'une nouveau projet.
     * 
     * @see #openSchema() 
     */
    public final static String ACTION_OPEN = "#OPEN";

    /**
     * Barre d'outils d'action rapide.
     * 
     */
//    protected class SchemaAction extends JPanel {
//
//        /**
//         * 
//         */
//        private final static String PATH_NEW = "./ico/new.png";
//        /**
//         * 
//         */
//        private final static String PATH_DELETE = "./ico/delete.png";
//        /**
//         * 
//         */
//        private final static String PATH_SAVE = "./ico/save.png";
//        /**
//         * 
//         */
//        private final static String PATH_ROTATE = "./ico/rotate.png";
//        /**
//         * 
//         */
//        private final static String PATH_OPEN = "./ico/open.png";
//        /**
//         * 
//         */
//        protected JButton buttons[];
//
//        /**
//         * 
//         */
//        public SchemaAction() {
//            setLayout(new FlowLayout(FlowLayout.LEFT));
//            buttons = new JButton[5];
//            buttons[0] = new JButton("New", new ImageIcon(PATH_NEW));
//            buttons[0].setActionCommand(ACTION_NEW);
//            buttons[1] = new JButton("Open", new ImageIcon(PATH_OPEN));
//            buttons[1].setActionCommand(ACTION_OPEN);
//            buttons[2] = new JButton("Save", new ImageIcon(PATH_SAVE));
//            buttons[2].setActionCommand(ACTION_SAVE);
//            buttons[3] = new JButton("Rotate", new ImageIcon(PATH_ROTATE));
//            buttons[3].setActionCommand(ACTION_ROTATE);
//            buttons[4] = new JButton("Delete", new ImageIcon(PATH_DELETE));
//            buttons[4].setActionCommand(ACTION_DELETE);
//
//            for (JButton jButton : buttons) {
//                add(jButton);
//                //jButton.setFocusable(false);
//            }
//        }
//
//        /**
//         * 
//         * @param l 
//         */
//        public synchronized void addActionListener(ActionListener l) {
//            for (JButton jButton : buttons) {
//                jButton.addActionListener(l);
//            }
//        }
//    }
    /**
     * Chemin vers l'icône nord.
     */
    private static final String ICON_PATH_NORTH = "./ico/fleche_north.png";
    /**
     * Chemin vers l'icône est.
     */
    private static final String ICON_PATH_EAST = "./ico/fleche_east.png";
    /**
     * Chemin vers l'icône sud.
     */
    private static final String ICON_PATH_SOUTH = "./ico/fleche_south.png";
    /**
     * Chemin vers l'icône ouest.
     */
    private static final String ICON_PATH_WEST = "./ico/fleche_west.png";
    
    /**
     * Vue du schéma en cours de création.
     */
    protected SchemaCreationVue scv;
    /**
     * Barre d'outils ou sont proposer les images pouvant être placer.
     */
    protected BarreOutil bo;
    /**
     * Lien vers le Gestionnaire de fichier.
     */
    protected GestionaireFichier gf;
    /**
     * Image en cours de placement.
     */
    //protected Picture newPicture;
    /**
     * Barre de menu en haut permettant les action rapide comme :
     * @see #ACTION_DELETE
     * @see #ACTION_NEW
     * @see #ACTION_OPEN
     * @see #ACTION_ROTATE
     * @see #ACTION_SAVE
     */
    //protected SchemaAction schemaAction = new SchemaAction();
    /**
     * Accès au toolkit permettant de créer des icons.
     */
   // protected static Toolkit tk = Toolkit.getDefaultToolkit();
    /**
     * Chemin de sauvegarde.
     * 
     * @see #ACTION_SAVE
     */
    //protected String savePath = null;

    /**
     * Constructeur.
     * 
     * @param gf Gestionnaire de fichier.
     */
    public CreationVue(GestionaireFichier gf) {
        this.gf = gf;
        this.bo = new BarreOutil(gf);
        this.scv = new SchemaCreationVue(gf);
        init();
    }
    
    /**
     * Constructeur.
     * 
     * @param gf Gestionnaire de fichier.
     * @param schema schéma a afficher
     */
    public CreationVue(GestionaireFichier gf, ArrayList<Integer[]> schema) {
        this.gf = gf;
        this.bo = new BarreOutil(gf);
        this.scv = new SchemaCreationVue(gf, schema);
        init();
    }

    /**
     * initialisation de la classe.
     */
    protected void init() {
        // mise en place du layout
        setLayout(new BorderLayout());

        /* GESTION DES BOUTONS D'AJOUT DE LIGNE */
        JPanel jPanel = new JPanel(new BorderLayout());

        // création d'un listerner qui ne gère que les bouton d'ajout de ligne
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scv.addLigne(((JButton) e.getSource()).getActionCommand());
                //requestFocus();
            }
        };

        // les quatre bouton permettent l'ajout de ligne
        JButton buttons[] = new JButton[4];

        // le texte associé au Bouton
        ImageIcon icon[] = {
            new ImageIcon(ICON_PATH_NORTH),
            new ImageIcon(ICON_PATH_EAST),
            new ImageIcon(ICON_PATH_SOUTH),
            new ImageIcon(ICON_PATH_WEST)};

        // les action associé au bouton
        String strAction[] = {
            SchemaCreationVue.AC_ADD_NORTH,
            SchemaCreationVue.AC_ADD_EAST,
            SchemaCreationVue.AC_ADD_SOUTH,
            SchemaCreationVue.AC_ADD_WEST};

        // leurs position dans le layout
        String position[] = {
            BorderLayout.NORTH,
            BorderLayout.EAST,
            BorderLayout.SOUTH,
            BorderLayout.WEST};


        for (int cpt = 0; cpt < buttons.length; cpt++) {
            // création du bouton
            buttons[cpt] = new JButton(icon[cpt]);
            // mise en place de l'action commande
            buttons[cpt].setActionCommand(strAction[cpt]);

            buttons[cpt].addActionListener(listener);
            jPanel.add(buttons[cpt], position[cpt]);
        }

        // ajout de la vue du schéma au centre des quatres bouton
        jPanel.add(scv, BorderLayout.CENTER);
        /* FIN DE GESTION DES BOUTONS D'AJOUT DE LIGNE */

        // ajout du tout a la vue
        add(jPanel, BorderLayout.CENTER);
        add(new JScrollPane(bo), BorderLayout.EAST);
        //add(schemaAction, BorderLayout.NORTH);
        setFocusable(true);
    }

    /**
     * Ajout du listener. Voir ci dessous pour les événements que celui doit 
     * obligatoirement gérer.
     * 
     * @param cl listener a ajouter, doit implémenté {@see CreationListener}.
     * @see #ACTION_DELETE pour {@see ActionListener}
     * @see #ACTION_NEW pour {@see ActionListener}
     * @see #ACTION_OPEN pour {@see ActionListener}
     * @see #ACTION_ROTATE pour {@see ActionListener}
     * @see #ACTION_SAVE pour {@see ActionListener}
     * @see KeyEvent#VK_R pour {@see KeyListener}
     * @see KeyEvent#VK_DELETE pour {@see KeyListener}
     * @see KeyEvent#VK_ESCAPE pour {@see KeyListener}
     */
    public synchronized void addListener(SchemaCreationControleur cl) {
        addMouseListener(cl);
        addKeyListener(cl);
        //schemaAction.addActionListener(cl);
    }

    /**
     * Ajout un écouteur a la vue.
     * 
     * @param l Écouteur.
     * @deprecated {@see #addListener(schema.creation.CreationListener)}.
     */
    @Override
    public synchronized void addMouseListener(MouseListener l) {
        scv.addMouseListener(l);
        bo.addMouseListener(l);
    }

    /**
     * Si {@see #newPicture} n'est pas nul l'image aura le focus afin de permettre 
     * a l'utilisateur de mieux visualiser ou il click.
     * 
     * @param pc l'image ce trouvent sous le pointeur de la souris
     */
    /*public void enteredOn(PictureCreation pc) {
        if (newPicture != null) {
            scv.setFocusOn(pc);
        }
    }*/

    /**
     * Enlève le focus de tout élément.
     */
    /*public void escape() {
        newPicture = null;
        scv.setFocusOn(null);
        setCursor(Cursor.getDefaultCursor());
    }*/

    /**
     * Supprime l'élément sélectionner.<br/>
     * Cette action peut être enclenché par :<br/>
     * - {@see #ACTION_DELETE}<br/>
     * - {@see KeyEvent#VK_DELETE}
     * 
     * @return nul ou {@value GestionaireFichier#EMPTY_PICTURE} 
     * ({@see GestionaireFichier#EMPTY_PICTURE}).
     */
    /*public Integer delete() {
        PictureCreation pc = scv.getFocusElement();
        if (pc != null) {
            pc.setCode(GestionaireFichier.EMPTY_PICTURE);
            pc.setImage(gf.getPicture(GestionaireFichier.EMPTY_PICTURE));
            if (newPicture == null) {
                scv.setFocusOn(null);
            }
            return GestionaireFichier.EMPTY_PICTURE;
        }
        return null;
    }*/

    /**
     * Plusieurs comportement sont défini selon les cas :<br/>
     * - si l'utilisateur est en train de placer des nouvelles portes la porte 
     * sera tourner<br/>
     * - si l'utilisateur est a sélectionner une case celle-ci de tourner<br/>
     * - sinon rien ne sera fait de même si l'image correspond a 
     * {@see GestionaireFichier#EMPTY_PICTURE}<br/>
     * 
     * Cette action peut être enclenché par :<br/>
     * - {@see #ACTION_ROTATE}<br/>
     * - {@see KeyEvent#VK_R}
     * 
     * @return nul ou le code de la nouvelle porte. 
     */
    /*public Integer rotation() {
        Integer i = null;
        if (newPicture != null) {
            i = newPicture.getCode();

            if (i != GestionaireFichier.EMPTY_PICTURE) {
                if (i % 10 == GestionaireFichier.ROTATION) {
                    i -= GestionaireFichier.ROTATION;
                }
                i++;
                newPicture = new Picture(gf.getPicture(i), i);

                Cursor cursor = tk.createCustomCursor(newPicture.getImage(), new Point(1, 1), "Pointeur");
                setCursor(cursor);
            }
        } else {
            PictureCreation pc = scv.getFocusElement();
            if (pc != null) {
                i = pc.getCode();

                if (i != GestionaireFichier.EMPTY_PICTURE) {
                    if (i % 10 == GestionaireFichier.ROTATION) {
                        i -= GestionaireFichier.ROTATION;
                    }

                    i++;

                    pc.setCode(i);
                    pc.setImage(gf.getPicture(i));
                }
            }
        }
        return i;
    }*/

    /**
     * Sauvegarde le fichier dans le répertoire pré-indiquer ({@see #savePath}), 
     * si celui ne l'ai pas l'utilisateur sera invité a le choisir ({@see #saveAs()}).
     * 
     * @throws FileNotFoundException si le fichier n'a pas pu être trouver.
     * @throws IOException si une erreur survient lors de l'écriture.
     * @see #saveAs() 
     */
    /*public void save() throws FileNotFoundException, IOException {
        if (savePath == null) {
            saveAs();
        } else {
            scv.save(savePath);
        }
    }*/

    /**
     * Ouvre une boîte de dialogue ou l'utilisateur peut choisir sous quel nom 
     * enregistrer sont fichier. Ce nom sera enregistrer pour les sauvegardes 
     * ultérieur ({@see #savePath}).
     * 
     * @throws FileNotFoundException si le fichier n'a pas pu être trouver.
     * @throws IOException si une erreur survient lors de l'écriture.
     * @see #save() 
     */
    /*public void saveAs() throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser(SchemaVue.REP_SAVE);
        int returnVal = chooser.showSaveDialog(this);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            savePath = chooser.getSelectedFile().getAbsolutePath();
            scv.save(savePath);
        }
    }*/

    /**
     * Nouveau schéma vierge.
     * 
     * Ouvre une boîte de dialoque dans laquel l'utilisateur saisi le nombre de 
     * ligne et le nombre de colonne qu'il désire.
     */
    /*public void newSchema() {
    }*/

    /**
     * Ouverture d'un schéma spécifique. Le chemin spécifier sera sauvegarder 
     * pour permettre une sauvegarde rapide de l'utilisateur ({@see #savePath})
     * Ouvre une boîte de dialogue dans laquel l'utilisateur est invité a choisir
     * le fichier qu'il désire ouvrir.
     */
    /*public void openSchema() throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser(SchemaVue.REP_SAVE);
        int returnVal = chooser.showOpenDialog(this);
        
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            savePath = chooser.getSelectedFile().getAbsolutePath();
            scv.newSchema(GestionaireFichier.loadFileShemat(savePath));
        }
    }*/

    SchemaCreationVue getSchemaCreationVue() {
        return scv;
    }

    BarreOutil getBarreOutil() {
        return bo;
    }

    GestionaireFichier getGestionaireFichier() {
        return gf;
    }
}
