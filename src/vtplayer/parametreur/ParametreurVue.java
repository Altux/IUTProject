package vtplayer.parametreur;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Vue du paramètreur.
 * 
 * @author
 */
public class ParametreurVue extends JPanel {
    /**
     * Demande de sauvegarde des paramètres.
     * @see ParametreurModele#save() 
     */
    public static final String ACTION_COMMAND_SAVE = "#SAVE";
    
    /**
     * On change d'index, appeler menu.setTest(menu.getSelectedItem()) pour mettre
     * à jour la vue.
     * @see ParametreurVue#setMenuText(java.lang.String) 
     */
    public static final String ACTION_COMMAND_INDEX_CHANGE = "#CHANGE";

    /**
     * Sous classe gérant simplement le menu.
     * @author godeau
     */
    protected class Menu extends JPanel {
        /**
         * Liste des éléments a paramétré.
         */
        JComboBox<Integer> comboBox = new JComboBox<>();
        
        /**
         * Texte descriptif de l'élément a paramétré.
         */
        JLabel jLabel = new JLabel();

        /**
         * Constructeur du menu, Attention doit être ensuite initialiser pour 
         * être valide ({@see #getSelectedItem()} & {@see #setText(java.lang.String)}).
         * 
         * @param arrayList tableau des index de description
         */
        public Menu(ArrayList<Integer> arrayList) {
            // tri du tableau
            Collections.sort(arrayList);
            
            // mise en place du layout
            setLayout(new BorderLayout());
            
            // ajout des composants
            add(comboBox, BorderLayout.WEST);
            add(jLabel, BorderLayout.CENTER);
            
            // ajout des items a la JComboBox
            for (Integer i : arrayList) {
                comboBox.addItem(i);
            }
            
            // association des actions au constante
            comboBox.setActionCommand(ACTION_COMMAND_INDEX_CHANGE);
        }
         
        /**
         * Met le texte dans le JLabel (ne vérifie pas la cohérence des donner).
         * 
         * @param txt texte a afficher.
         */
        public void setText(String txt){
            jLabel.setText(" " + txt);
        }
        
       public synchronized void  addActionListener(ActionListener l){
           comboBox.addActionListener(l);
       }
       
       /**
        * Permet d'obtenir l'item sélectionner.
        * 
        * @return l'index de la description.
        */
       public Integer getSelectedItem() {
           return (Integer) comboBox.getSelectedItem();
       }
    }
    
    /**
     * Grille représentative du carré de picots gauche.
     */
    ParametreurGridVue gridVue1 = new ParametreurGridVue();
    /**
     * Grille représentative du carré de picots droit.
     */
    ParametreurGridVue gridVue2 = new ParametreurGridVue();
    /**
     * Menu d'action.
     */
    Menu menu;

    /**
     * Constructeur.
     * 
     * @param parametreurModele Prend le modèle pour permétre l'initialisation.
     */
    public ParametreurVue(ParametreurModele parametreurModele) {
        // mise en place du layout
        setLayout(new BorderLayout());
        
        // affichage des grilles
        GridLayout gl = new GridLayout(1, 2);
        gl.setHgap(2);
        JPanel jp = new JPanel(gl);
        jp.add(gridVue1);
        jp.add(gridVue2);
        add(jp, BorderLayout.CENTER);
        
        // affichage du menu
        menu = new Menu(parametreurModele.getKeyDescription());
        menu.setText(parametreurModele.getDescription(menu.getSelectedItem()));
        add(menu, BorderLayout.NORTH);
        
        // mise en place de l'affichage des bytes
        setBytes(parametreurModele.getPicots(menu.getSelectedItem()));
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        gridVue1.addMouseListener(l);
        gridVue2.addMouseListener(l);
    }
    
    /**
     * Ajout du listener au bouton du menu.
     * 
     * @param l listener.
     * @see #ACTION_COMMAND_INDEX_CHANGE
     * @see #ACTION_COMMAND_SAVE
     */
    public synchronized void  addActionListener(ActionListener l){
           menu.addActionListener(l);
       }
    
    /**
     * Affichage des bytes.
     * 
     * @param bytes tableau de quatre bytes a afficher.
     */
    public void setBytes(Byte bytes[]){
        gridVue1.setBytes(bytes[0], bytes[1]);
        gridVue2.setBytes(bytes[2], bytes[3]);
    }
    
    /**
     * Permet de récupéré les bytes telle qu'ils sont sur la vue.
     * 
     * @return tableau de quatre bytes.
     */
    public Byte[] getBytes(){
        // récupération des bytes associé au grilles
        Byte b01[] = gridVue1.getBytes();
        Byte b23[] = gridVue2.getBytes();
        Byte ret[] = new Byte[4];
        
        // copie des bytes
        ret[0] = b01[0];
        ret[1] = b01[1];
        ret[2] = b23[0];
        ret[3] = b23[1];

        return ret;
    }
    
    /**
     * Permet d'obtenir l'index de la description sélectionner.
     * 
     * @return index sélectionner.
     * @see Menu#getSelectedItem() 
     */
    public Integer getMenuSelectedItem(){
        return menu.getSelectedItem();
    }
    
    /**
     * Affiche le texte dans le label du menu.
     * 
     * @param txt texte a afficher.
     * @see Menu#setText(java.lang.String) 
     */
    public void setMenuText(String txt){
        menu.setText(txt);
    }
    
}
