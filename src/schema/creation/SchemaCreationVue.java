package schema.creation;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import schema.GestionaireFichier;
import schema.Picture;
import schema.SchemaVue;

/**
 *
 */
public class SchemaCreationVue extends SchemaVue {

    /**
     * ActionCommande a lier au bouton d'ajout d'une ligne en dessous.
     * 
     * @see #addLigne(java.lang.String) 
     * @see #addSouth(java.awt.GridBagConstraints, java.awt.Image) 
     */
    public static final String AC_ADD_SOUTH = "addSouth";
    /**
     * ActionCommande a lier au bouton d'ajout d'une ligne a droite.
     * @see #addLigne(java.lang.String) 
     * @see #addEast(java.awt.GridBagConstraints, java.awt.Image) 
     */
    public static final String AC_ADD_EAST = "addEast";
    /**
     * ActionCommande a lier au bouton d'ajout d'une ligne au dessus
     * 
     * @see #addLigne(java.lang.String) 
     * @see #addNorth(java.awt.GridBagConstraints, java.awt.Image) 
     */
    public static final String AC_ADD_NORTH = "addNorth";
    /**
     * ActionCommande a lier au bouton d'ajout d'une ligne a gauche.
     * 
     * @see #addLigne(java.lang.String) 
     * @see #addWest(java.awt.GridBagConstraints, java.awt.Image) 
     */
    public static final String AC_ADD_WEST = "addWest";
    /**
     * L'élément aillant le focus.
     */
    //protected PictureCreation old_focused = null;
    
    /**
     * Début de contrainte pour le GridConstraintLayout, cela permet d'ajouter 
     * des lignes au dessus et a gauche même une fois le GridLayout mis en place.
     * Dans la limite X défini par cette valeur.
     */
    protected final static int START_CONSTRAINTS = 200;
    /**
     * Début réel du GridBagLayout sur l'axe X.
     */
    protected int startX = START_CONSTRAINTS;
    /**
     * Début réel du GridBagLayout sur l'axe Y.
     */
    protected int startY = START_CONSTRAINTS;

    public SchemaCreationVue(GestionaireFichier gf) {
        super(gf);
    }

    public SchemaCreationVue(GestionaireFichier gf, ArrayList<Integer[]> arrayList) {
        super(gf, arrayList);
    }

    @Override
    public void newSchema(ArrayList<Integer[]> arrayList) {
        startX = START_CONSTRAINTS;
        startY = START_CONSTRAINTS;

        // on vide le panel et le tableau de picture
        removeAll();
        pictures.clear();

        // récupère la taille de la grille
        nbLigne = arrayList.size();
        nbCol = arrayList.get(0).length;

        // mise en place du layout
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = constraints.gridy = START_CONSTRAINTS;
        for (Integer[] integers : arrayList) {
            constraints.gridx = START_CONSTRAINTS;
            for (Integer integer : integers) {
                addPicture(new PictureCreation(gf.getPicture(integer), integer, constraints.gridy, constraints.gridx), constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
        }

        if (mouseListener != null) {
            addMouseListener(mouseListener);
        }

        calculateOptimalSize(getSize());

    }

    /**
     * Permet de créer un nouveau schéma vierge de taille définie.
     * 
     * @param rows nombre de ligne.
     * @param columns nombre de colonne.
     */
    public void newSchema(int rows, int columns) {
        // on réinitialise les valeurs de début
        startX = START_CONSTRAINTS;
        startY = START_CONSTRAINTS;

        // on vide le panel et le tableau de picture
        removeAll();
        pictures.clear();

        // on garde le nombre de ligne et le nombre de colonne
        nbLigne = rows;
        nbCol = columns;

        // mise en place du layout
        GridBagConstraints constraints = new GridBagConstraints();

        // mise en place des images
        constraints.gridx = constraints.gridy = START_CONSTRAINTS;
        Image image = gf.getPicture(GestionaireFichier.EMPTY_PICTURE);

        for (int cptL = 0; cptL < rows; cptL++) {
            constraints.gridx = START_CONSTRAINTS;
            for (int cptC = 0; cptC < columns; cptC++) {
                addPicture(new PictureCreation(image, GestionaireFichier.EMPTY_PICTURE, constraints.gridy, constraints.gridx), constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
        }

        // si un écouteur a été préalabment mis on écoute les images
        if (mouseListener != null) {
            addMouseListener(mouseListener);
        }

        // on calcul la taille optimal des images
        calculateOptimalSize(getSize());
    }

    /**
     * Ajout d'une ligne dans la direction définie.
     * 
     * @param direction direction choisie, ({@see #AC_ADD_NORTH}, 
     * {@see #AC_ADD_SOUTH}, {@see #AC_ADD_WEST} ou {@see #AC_ADD_EAST}).
     * 
     */
    public void addLigne(String direction) {
        if (nbCol == 0 || nbLigne == 0){
            newSchema(1, 1);
            return;
        }
        
        GridBagConstraints constraints = new GridBagConstraints();
        Image image = gf.getPicture(GestionaireFichier.EMPTY_PICTURE);

        switch (direction) {
            case AC_ADD_SOUTH:
                addSouth(constraints, image);
                break;

            case AC_ADD_NORTH:
                addNorth(constraints, image);
                break;

            case AC_ADD_EAST:
                addEast(constraints, image);
                break;

            case AC_ADD_WEST:
                addWest(constraints, image);
                break;
        }
        calculateOptimalSize(this.getSize());
    }

    /**
     * Ajout d'une ligne en dessous des autres.
     * 
     * @param constraints objet de contrainte pour le GridBagLayout.
     * @param image images a ajouter normalement image vide.
     * @see #addLigne(java.lang.String) 
     */
    protected void addSouth(GridBagConstraints constraints, Image image) {
        constraints.gridy = nbLigne + startY;

        for (constraints.gridx = startX; constraints.gridx < nbCol + startX; constraints.gridx++) {
            Picture picture = new PictureCreation(image, GestionaireFichier.EMPTY_PICTURE, constraints.gridy, constraints.gridx);
            addPicture(picture, constraints);
            picture.addMouseListener(mouseListener);
        }

        nbLigne++;
    }

    /**
     * Ajout d'une ligne a droite des autres.
     * 
     * @param constraints objet de contrainte pour le GridBagLayout.
     * @param image images a ajouter normalement image vide.
     * @see #addLigne(java.lang.String) 
     */
    protected void addEast(GridBagConstraints constraints, Image image) {
        constraints.gridx = nbCol + startX;

        for (constraints.gridy = startY; constraints.gridy < nbLigne + startY; constraints.gridy++) {
            Picture picture = new PictureCreation(image, GestionaireFichier.EMPTY_PICTURE, constraints.gridy, constraints.gridx);
            addPicture(picture, constraints);
            picture.addMouseListener(mouseListener);
        }

        nbCol++;
    }

    /**
     * Ajout d'une ligne a gauche des autres.
     * 
     * @param constraints objet de contrainte pour le GridBagLayout.
     * @param image images a ajouter normalement image vide.
     * @see #addLigne(java.lang.String) 
     */
    protected void addWest(GridBagConstraints constraints, Image image) {
        startX--;
        constraints.gridx = startX;

        for (constraints.gridy = startY; constraints.gridy < nbLigne + startY; constraints.gridy++) {
            Picture picture = new PictureCreation(image, GestionaireFichier.EMPTY_PICTURE,constraints.gridy, constraints.gridx);
            addPicture(picture, constraints);
            picture.addMouseListener(mouseListener);
        }

        nbCol++;
    }

    /**
     * Ajout d'une ligne au dessus des autres.
     * 
     * @param constraints objet de contrainte pour le GridBagLayout.
     * @param image images a ajouter normalement image vide.
     * @see #addLigne(java.lang.String) 
     */
    protected void addNorth(GridBagConstraints constraints, Image image) {
        startY--;
        constraints.gridy = startY;

        for (constraints.gridx = startX; constraints.gridx < nbCol + startX; constraints.gridx++) {
            Picture picture = new PictureCreation(image, GestionaireFichier.EMPTY_PICTURE, constraints.gridy, constraints.gridx);
            addPicture(picture, constraints);
            picture.addMouseListener(mouseListener);
        }

        nbLigne++;
    }

    /**
     * Sauvegarde dans un fichier ce qui est afficher dans la vue.
     * 
     * @param path chemin vers le fichier de sauvegarde.
     * @throws FileNotFoundException si le fichier n'a pas pu être trouver.
     * @throws IOException Si une erreur est survenu lors de l'écriture du fichier.
     * @see GestionaireFichier#saveFileSchema(java.util.ArrayList, java.lang.String) 
     */
    public void save(String path) throws FileNotFoundException, IOException {
        if(nbLigne > 0 && nbCol > 0){
            // tableau résultat
            Integer tab[][] =new Integer[nbLigne][nbCol];

            // pour chaque composant de la vue
            for (Component component : this.getComponents()) {
                // si celui-ci est une instance de PictureCreation
                if (component instanceof PictureCreation) {

                    // on cast le composant
                    PictureCreation pc = (PictureCreation) component;

                    tab[pc.getLig()-startY][pc.getCol()-startX] = pc.getCode();
                }
            }
            // si l'ArrayList n'est pas vide on la sauvegarde dans le chemin définie
            GestionaireFichier.saveFileSchema(tab, path);
        }
    }

    @Override
    public int getNbCol() {
        return startY + nbCol;
    }

    @Override
    public int getNbLigne() {
        return startY + nbLigne;
    }
}
