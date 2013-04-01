package schema.creation;

import java.awt.GridBagConstraints;
import java.awt.Image;
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
     */
    public static final String AC_ADD_SOUTH = "addSouth";
    /**
     * ActionCommande a lier au bouton d'ajout d'une ligne a droite.
     */
    public static final String AC_ADD_EAST = "addEast";
    /**
     * ActionCommande a lier au bouton d'ajout d'une ligne au dessus
     */
    public static final String AC_ADD_NORTH = "addNorth";
    /**
     * ActionCommande a lier au bouton d'ajout d'une ligne a gauche.
     */
    public static final String AC_ADD_WEST = "addWest";
    /**
     * Début de contrainte pour le GridConstraintLayout, cela permet d'ajouter
     * des lignes au dessus et a gauche même une fois le GridLayout mis en
     * place. Dans la limite X défini par cette valeur.
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
        this(gf, true);
    }

    public SchemaCreationVue(GestionaireFichier gf, boolean resizeAuto) {
        super(gf, resizeAuto);
    }

    public SchemaCreationVue(GestionaireFichier gf, ArrayList<Integer[]> arrayList, boolean resizeAuto) {
        super(gf, arrayList, resizeAuto);
    }

    public SchemaCreationVue(GestionaireFichier gf, ArrayList<Integer[]> arrayList) {
        this(gf, arrayList, true);
    }

    public void clear() {
        removeAll();
        pictures.clear();
        nbCol = nbLigne = 0;
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

    @Override
    public void newSchema(Integer schema[][]) {
        startX = START_CONSTRAINTS;
        startY = START_CONSTRAINTS;

        // on vide le panel et le tableau de picture
        removeAll();
        pictures.clear();

        // récupère la taille de la grille
        nbLigne = schema.length;
        nbCol = schema[0].length;

        // mise en place du layout
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = constraints.gridy = START_CONSTRAINTS;
        for (Integer[] integers : schema) {
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
     * @param direction direction choisie, ({@link  #AC_ADD_NORTH},
     * {@link #AC_ADD_SOUTH}, {@link #AC_ADD_WEST} ou {@link #AC_ADD_EAST}).
     */
    public void addLigne(String direction) {
        if (nbCol == 0 || nbLigne == 0) {
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
     */
    protected void addWest(GridBagConstraints constraints, Image image) {
        startX--;
        constraints.gridx = startX;

        for (constraints.gridy = startY; constraints.gridy < nbLigne + startY; constraints.gridy++) {
            Picture picture = new PictureCreation(image, GestionaireFichier.EMPTY_PICTURE, constraints.gridy, constraints.gridx);
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

    @Override
    public Integer[][] getSchemaCode() {
        if (nbLigne > 0 && nbCol > 0) {
            Integer tab[][] = new Integer[nbLigne][nbCol];

            for (Picture pc : pictures) {
                tab[pc.getLig() - startY][pc.getCol() - startX] = pc.getCode();
            }
            return tab;
        }
        return null;
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
