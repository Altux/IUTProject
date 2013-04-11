package schema;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author groupe interface
 */
public class GestionaireFichier {

    /**
     * Permet de savoir quel est le cractére qui sépare deux valeurs dans le
     * fichier texte.
     */
    public final static String DELIMITTEUR = " ";
    /**
     * Indique le répertoire où ce trouve les images.
     */
    public final static String REP_IMAGES = "porte/";
    /**
     * Répertoire de sauvegarde par défaut.
     */
    public final static String REP_SAVE = "save/";
    /**
     * Correspond au code d'une image vide.
     */
    public final static int EMPTY_PICTURE = 0;
    public final static int CODE_PORTE = 2;
    /**
     * Extension par défaut des fichier gérer.
     */
    public final static String EXTENTION_FILE = "ustd";
    /**
     * Nous permet de ranger nos images, l'index d'une HashMap va être égale au
     * code de notre image.
     */
    protected HashMap<Integer, Image> pictures = new HashMap<>();

    /**
     * Charges les images en mémoire.
     *
     * @param rep_images répertoire ou ce trouve les images; l'extension de
     * celle-ci doit être en ".jpg" et le nom de l'image doit correspondre au
     * code associant les bytes ({
     * @see vtplayer.parametreur}).
     */
    public GestionaireFichier(String rep_images) {

        // pour chaque fichier présent dans le répertoire
        for (File file : new File(rep_images).listFiles()) {
            String fileName = file.getName();
            // On verifie que l'éléments est bien une image jpg
            if (file.isFile() && fileName.endsWith(".jpg")) {
                //O n récupére son nom
                fileName = fileName.split("\\.")[0];
                // On créer notre nouvelle image
                pictures.put(Integer.valueOf(fileName), new ImageIcon(file.getAbsolutePath()).getImage());
            }
        }
    }

    /**
     * La fonction permet grâce à un code d'image passé en paramètre de
     * retourner l'image correspondante.
     *
     * @param code code d'une image
     * @return image associé au code ou nul si aucune image n'est associé au
     * code
     */
    public Image getPicture(Integer code) {
        return pictures.get(code);
    }

    /**
     * Nous permet d'avoir toutes les images de notre HashMap.
     *
     * @return la hash map d'images
     */
    public HashMap<Integer, Image> getPictures() {
        return pictures;
    }

    /**
     * Permet de charger un fichier schéma.
     * 
     * @param path correspond au chemin absolu du fichier
     * @return ArrayList tableau de code correspondant au image
     * @throws FileNotFoundException Si le fichier n'existe pas
     * @throws IOException erreur de lecture
     */
    public static ArrayList<Integer[]> loadFileShemat(String path) throws FileNotFoundException, IOException {
        ArrayList<Integer[]> al = new ArrayList<>();

        String ligne;

        InputStream ips = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(ips);

        try (BufferedReader buff = new BufferedReader(isr)) {
            // lecture du fichier ligne par ligne
            while ((ligne = buff.readLine()) != null) {
                //Si la ligne n'est pas vide
                if (!ligne.isEmpty()) {
                    // On sépare celon le délimiteur 
                    String subLigne[] = ligne.split(DELIMITTEUR);
                    Integer value[] = new Integer[subLigne.length];

                    for (int cpt = 0; cpt < subLigne.length; cpt++) {
                        //
                        value[cpt] = Integer.valueOf(subLigne[cpt]);
                    }
                    al.add(value);
                }
            }
        }

        return al;
    }

    /**
     * Sauvegarde d'un schéma.
     * 
     * @param tab tableau de code
     * @param path fichier ou enregistrer
     * @throws FileNotFoundException si le fichier n'a pas pu être trouver
     * @throws IOException erreur d'écriture
     */
    public static void saveFileSchema(Integer[][] tab, String path) throws FileNotFoundException, IOException {
//        if (!path.endsWith(EXTENTION_FILE)) {
//            path += EXTENTION_FILE;
//        }

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            for (Integer[] integers : tab) {
                int cpt;
                for (cpt = 0; cpt < integers.length - 1; cpt++) {
                    pw.print(integers[cpt] + DELIMITTEUR);
                }
                pw.println(integers[cpt]);
            }
        }
    }
}
