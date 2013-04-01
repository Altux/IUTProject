package schema.creation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
     * Constructeur.
     *
     * @param gf Gestionnaire de fichier.
     * @deprecated
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
     * @deprecated
     */
    public CreationVue(GestionaireFichier gf, ArrayList<Integer[]> schema) {
        this.gf = gf;
        this.bo = new BarreOutil(gf);
        this.scv = new SchemaCreationVue(gf, schema);
        init();
    }

    public CreationVue(GestionaireFichier gf, SchemaCreationVue creationVue) {
        this.gf = gf;
        this.bo = new BarreOutil(gf);
        this.scv = creationVue;
        init();
    }

    /**
     * initialisation de la classe.
     */
    private void init() {
        // mise en place du layout
        setLayout(new BorderLayout());

        /* GESTION DES BOUTONS D'AJOUT DE LIGNE */
        JPanel jPanel = new JPanel(new BorderLayout());

        // création d'un listerner qui ne gère que les bouton d'ajout de ligne
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scv.addLigne(((JButton) e.getSource()).getActionCommand());
                scv.setHaveChange(true);
                scv.revalidate();
                scv.repaint();
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
            buttons[cpt].setFocusable(false);
            jPanel.add(buttons[cpt], position[cpt]);
        }

        // ajout de la vue du schéma au centre des quatres bouton
        jPanel.add((scv.getResizeAuto() ? scv : new JScrollPane(scv)), BorderLayout.CENTER);
        /* FIN DE GESTION DES BOUTONS D'AJOUT DE LIGNE */

        // ajout du tout a la vue
        add(jPanel, BorderLayout.CENTER);
        add(new JScrollPane(bo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.EAST);
    }

    /**
     * Ajout un écouteur a la vue.
     *
     * @param l Écouteur.
     */
    @Override
    public synchronized void addMouseListener(MouseListener l) {
        scv.addMouseListener(l);
        bo.addMouseListener(l);
    }

    @Override
    public synchronized void removeMouseListener(MouseListener l) {
        scv.removeMouseListener(l);
        bo.removeMouseListener(l);
    }

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
