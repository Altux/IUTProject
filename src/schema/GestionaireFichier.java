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
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author groupe interface
 */
public class GestionaireFichier {
    /**
     * nombre de rotation qu'un Ã©lÃ©ments peut faire.
     */
    public final static int ROTATION = 4;
    /**
     * Permet de savoir quel est le caratere qui separe deux valeurs dans le fichier texte.
     */
    public final static String DELIMITTEUR = " ";
    /**
     * Indique le rÃ©pertoire oÃ¹ ce trouve les images.
     */
    public final static String REP_IMAGES = "./porte";
    /**
     * Correspond au code d'une image vide.
     */
    public final static int EMPTY_PICTURE = 0;
    /**
     * Nous permet de ranger nos images, l'index d'une HashMap va Ãªtre Ã©gale
     * au code de notre image.
     */
    protected HashMap<Integer, Image> pictures = new HashMap();

    /**
     * 
     * @param rep_images rÃ©pertoire ou ce trouve les images; l'extension de 
     * celle-ci doit Ãªtre en jpg et le nom de l'image doit correspondre au 
     * code associant les bytes ({@see vtplayer.parametreur}).
     */
    public GestionaireFichier(String rep_images) {
        
        for (File file : new File(rep_images).listFiles()) {
            String fileName = file.getName();
            //On verifie que l'éléments est bien une image jpg
            if (file.isFile() && fileName.endsWith(".jpg")) {
                //On récupére son nom
                fileName = fileName.split("\\.")[0];
                //On créer notre nouvelle image
                pictures.put(Integer.valueOf(fileName), new ImageIcon(file.getAbsolutePath()).getImage());
            }
        }
    }
    /**
     * La fonction permet grâce ï¿½ un code d'image passé en paramétre de retourner l'image 
     * correspondante.
     * 
     * @param code correspond au code d'une image
     * @return Image correspond Ã  l'image associÃ© au code de l'image
     */
    public Image getPicture(Integer code) {
        //On boucle sur notre HashMap
        for (Map.Entry<Integer, Image> entry : pictures.entrySet()) {
            //Si le code de l'ï¿½lï¿½ments(image) courrant correspond
            // ï¿½ notre code passï¿½ en paramï¿½tre on rentre dans le if
            if (entry.getKey().equals(code)) {
                //retourne l'ï¿½lï¿½ments 
                return entry.getValue();
            }
        }
        //Sinon l'ï¿½lï¿½ments n'existe pas 
        return null;

    }
    /**
     * Nous permet d'avoir toutes les images de notre HashMap.
     * 
     * @return pictures 
     */
    public HashMap<Integer, Image> getPictures() {
        return pictures;
    }
    /**
     * 
     * @param path correspond au chemin absolu du fichier
     * @return ArrayList 
     * @throws FileNotFoundException Si le fichier n'existe pas
     * @throws IOException 
     */
    public static ArrayList<Integer[]> loadFileShemat(String path) throws FileNotFoundException, IOException {
        ArrayList<Integer[]> al = new ArrayList();
        
        String ligne;

        InputStream ips = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(ips);

        try (BufferedReader buff = new BufferedReader(isr)) {
            // lecture du fichier ligne par ligne
            while ((ligne = buff.readLine()) != null) {
                //Si la ligne n'est pas vide
                if (!ligne.isEmpty()) {
                    //On sï¿½pare celon le dï¿½limiteur 
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
     * 
     * @param al
     * @param path
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void saveFileSchema(Integer[][] tab, String path) throws FileNotFoundException, IOException {
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
