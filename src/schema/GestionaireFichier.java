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
     * Permet de savoir quel est le cract�re qui s�pare deux valeurs dans le
     * fichier texte.
     */
    public final static String DELIMITTEUR = " ";
    /**
     * Indique le r�pertoire o� ce trouve les images.
     */
    public final static String REP_IMAGES = "porte/";
    /**
     * R�pertoire de sauvegarde par d�faut.
     */
    public final static String REP_SAVE = "save/";
    /**
     * Correspond au code d'une image vide.
     */
    public final static int EMPTY_PICTURE = 0;
    public final static int CODE_PORTE = 2;
    /**
     * Extension par d�faut des fichier g�rer.
     */
    public final static String EXTENTION_FILE = "ustd";
    /**
     * Nous permet de ranger nos images, l'index d'une HashMap va �tre �gale au
     * code de notre image.
     */
    protected HashMap<Integer, Image> pictures = new HashMap<>();

    /**
     * Charges les images en m�moire.
     *
     * @param rep_images r�pertoire ou ce trouve les images; l'extension de
     * celle-ci doit �tre en ".jpg" et le nom de l'image doit correspondre au
     * code associant les bytes ({
     * @see vtplayer.parametreur}).
     */
    public GestionaireFichier(String rep_images) {

        // pour chaque fichier pr�sent dans le r�pertoire
        for (File file : new File(rep_images).listFiles()) {
            String fileName = file.getName();
            // On verifie que l'�l�ments est bien une image jpg
            if (file.isFile() && fileName.endsWith(".jpg")) {
                //O n r�cup�re son nom
                fileName = fileName.split("\\.")[0];
                // On cr�er notre nouvelle image
                pictures.put(Integer.valueOf(fileName), new ImageIcon(file.getAbsolutePath()).getImage());
            }
        }
    }

    /**
     * La fonction permet gr�ce � un code d'image pass� en param�tre de
     * retourner l'image correspondante.
     *
     * @param code code d'une image
     * @return image associ� au code ou nul si aucune image n'est associ� au
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
     * Permet de charger un fichier sch�ma.
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
                    // On s�pare celon le d�limiteur 
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
     * Sauvegarde d'un sch�ma.
     * 
     * @param tab tableau de code
     * @param path fichier ou enregistrer
     * @throws FileNotFoundException si le fichier n'a pas pu �tre trouver
     * @throws IOException erreur d'�criture
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
