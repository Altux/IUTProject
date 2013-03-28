package vtplayer.parametreur;

import vtplayer.VTPlayerException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import vtplayer.ByteUtilitaire;
import vtplayer.linux.VTPlayer;
import vtplayer.VTPlayerInterface;

/**
 *
 * @author godeau
 */
public class ParametreurModele {

    /**
     * Map des description associ� a un num�ro unique.
     */
    HashMap<Integer, String> desciption;
    /**
     * Map des bytes associ� a une cl� unique.
     */
    HashMap<Integer, Byte[]> picots;
    /**
     * Chemin par d�faut ({@value #PATH_DESCRIPTION_FILE}) vers le fichier de
     * description.<br/>
     * Le fichier ce compose, sur chaque ligne d'un num�ro suivi d'une phrase
     * descriptive s�parer par un d�limiteur {@value #DELIMITEUR}.<br/>
     * <u>Exemple de fichier valide :</u><br/> 1{@value #DELIMITEUR}Porte
     * OU<br/> 2{@value #DELIMITEUR}Porte ET<br/>
     */
    protected final static String PATH_DESCRIPTION_FILE = "./config/description.conf";
    /**
     * Chemin par d�faut ({@value #PATH_PICOTS_FILE}) vers le fichier de
     * configuration des picots. Le fichier est automatiquement g�n�r� gr�ce a 
     * {@link #save()} ou {@link #save(java.lang.String)} (si vous voulez sp�cifier un chemin
     * particulier).
     * <u>Exemple de fichier valide :</u><br/>
     * 1{@value #DELIMITEUR}b9{@value #DELIMITEUR}5d{@value #DELIMITEUR}99{@value #DELIMITEUR}77<br/>
     * 2{@value #DELIMITEUR}99{@value #DELIMITEUR}77{@value #DELIMITEUR}b9{@value #DELIMITEUR}5d<br/><br/>
     * <strong><u>ATTENTION :</u></strong> ne modifier pas ce fichier a la main
     * a moins d'�tre sur de vous.
     */
    protected final static String PATH_PICOTS_FILE = "./config/default.conf";
    /**
     * D�limiteur de champ par d�faut.
     */
    protected final static String DELIMITEUR = "#";
    /**
     * Fichier de configuration des picots (choisi par l'utilisateur).
     */
    private String pathPicot;

    /**
     * Charge les fichier par d�faut de description et de configuration des
     * picots.(Respectivement {@value #PATH_DESCRIPTION_FILE} et
     * {@value #PATH_PICOTS_FILE}).
     *
     * @throws FileNotFoundException Si le fichier de de description n'est pas
     * trouver. Par contre un WARNING sera �crit sur le terminal pour le fichier
     * de configuration des picots.
     * @throws IOException erreur de lecture �criture.
     * @throws VTPlayerException erreur de conversion de byte.
     * @see #PATH_DESCRIPTION_FILE
     * @see #PATH_PICOTS_FILE
     */
    public ParametreurModele() throws FileNotFoundException, IOException, VTPlayerException {
        this(PATH_PICOTS_FILE, PATH_DESCRIPTION_FILE, true);
    }

    public ParametreurModele(HashMap<Integer, Byte[]> picots) throws FileNotFoundException, IOException {
        this(picots, PATH_PICOTS_FILE, PATH_DESCRIPTION_FILE);
    }
    
    public ParametreurModele(HashMap<Integer, Byte[]> picots, String pathPicot, String pathDescription) throws FileNotFoundException, IOException {
        this.pathPicot = pathPicot;
        desciption = loadDescription(pathDescription);
        this.picots = picots;
    }
    
    

    /**
     * Charge les fichier sp�cifier.
     *
     * @param pathPicot chemin vers le fichier de configuration des picots. Ce
     * chemin est garder a en m�moire pour l'appel de {@link #save()}.
     * @param pathDescription chemin vers le fichier de description. {@link #pathDescription}.
     * @param onlyWarningPicotFile si est a vrai, un WARNING sera �crit sur le
     * terminal pour le fichier de configuration des picots (si celui-ci n'est
     * pas trouver). Sinon l'erreur sera reporter.
     * @throws FileNotFoundException si l'un des fichier n'est pas trouver.
     * @throws IOException erreur de lecture �criture.
     * @throws VTPlayerException erreur de conversion de byte.
     */
    public ParametreurModele(String pathPicot, String pathDescription, boolean onlyWarningPicotFile) throws FileNotFoundException, IOException, VTPlayerException {
        this.pathPicot = pathPicot;
        desciption = loadDescription(pathDescription);
        try {
            picots = loadPicotFile(pathPicot);
        } catch (FileNotFoundException exception) {
            if (onlyWarningPicotFile) {
                Logger.getLogger(ParametreurModele.class.getName()).log(Level.WARNING, "The file {0} can not be find.", pathPicot);
            } else {
                throw exception;
            }
        }
    }

    /**
     * Charge le fichier de configuration des picots par d�faut
     * {@value #PATH_PICOTS_FILE}.
     *
     * @param hashMap map a remplir avec le contenu du fichier.
     * @throws IOException erreur lors de la lecture du fichier.
     * @throws FileNotFoundException fichier non trouver.
     * @throws VTPlayerException erreur lors de la conversion d'un byte.
     * @see #loadDescription(java.util.HashMap, java.lang.String)
     */
    public static HashMap<Integer, Byte[]> loadPicotFile() throws IOException, VTPlayerException, FileNotFoundException {
        return loadPicotFile(PATH_PICOTS_FILE);
    }

    /**
     * Charge le fichier de configuration des picots sp�cifier en param�tre.
     *
     * @param hashMap map a remplir avec le contenu du fichier.
     * @param path chemin vers le fichier.
     * @throws IOException erreur lors de la lecture du fichier.
     * @throws FileNotFoundException fichier non trouver.
     * @throws VTPlayerException erreur lors de la conversion d'un byte.
     * @see #loadDescription(java.util.HashMap)
     */
    public static HashMap<Integer, Byte[]> loadPicotFile(String path) throws IOException, FileNotFoundException, VTPlayerException {
        HashMap<Integer, Byte[]> hashMap = new HashMap<>();

        Integer key;
        String ligne;

        InputStream ips = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(ips);

        try (BufferedReader buff = new BufferedReader(isr)) {
            // lecture du fichier ligne par ligne
            while ((ligne = buff.readLine()) != null) {
                // si la ligne n'est pas vide
                if (!ligne.isEmpty()) {
                    // on r�cup�re les �l�ments
                    String str[] = ligne.split(DELIMITEUR);
                    // si le nombre d�l�ment est correct
                    if (str.length == 5) {
                        // on r�cup�re la cl�
                        key = Integer.valueOf(str[0]);
                        // on converti les quatres bytes
                        Byte bytes[] = new Byte[4];
                        for (int i = 1; i < 5; i++) {
                            bytes[i - 1] = ByteUtilitaire.string_hexa_to_byte(str[i]);
                        }
                        // on l'ajout a la map
                        hashMap.put(key, bytes);
                    }
                }
            }
        }

        return hashMap;
    }

    /**
     * Charge le fichier de description ({@value #PATH_DESCRIPTION_FILE}). Voir {@link #PATH_DESCRIPTION_FILE} pour le format de fichier.
     *
     * @param hashMap map a remplir.
     * @throws FileNotFoundException si le fichier n'a pas �t� trouver.
     * @throws IOException si un erreur de lecture est survenu.
     * @see #loadDescription(java.util.HashMap, java.lang.String)
     */
    public static HashMap<Integer, String> loadDescription() throws FileNotFoundException, IOException {
        return loadDescription(PATH_DESCRIPTION_FILE);
    }

    /**
     * Charge le fichier de description avec un chemin sp�cifier.
     *
     * @param hashMap map a remplir
     * @param path chemin vers le fichier.
     * @throws FileNotFoundException si le fichier n'a pu �tre trouver.
     * @throws IOException si un erreur de lecture est survenu.
     * @see #loadDescription(java.util.HashMap)
     */
    public static HashMap<Integer, String> loadDescription(String path) throws FileNotFoundException, IOException {
        HashMap<Integer, String> hashMap = new HashMap<>();
        String ligne;
        Integer key;

        InputStream ips = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(ips);

        // lecture du fichier ligne par ligne
        try (BufferedReader buff = new BufferedReader(isr)) {
            while ((ligne = buff.readLine()) != null) {
                if (!ligne.isEmpty()) {
                    String str[] = ligne.split(DELIMITEUR);
                    if (str.length == 2) {
                        key = Integer.valueOf(str[0]);
                        hashMap.put(key, str[1]);
                    }
                }
            }
        }

        return hashMap;
    }

    /**
     * Retourne toute les clef de description.
     *
     * @return clef de description.
     */
    ArrayList<Integer> getKeyDescription() {
        ArrayList<Integer> ret = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : desciption.entrySet()) {
            ret.add(entry.getKey());
        }

        return ret;
    }

    /**
     * Retourne la description associ� a un index donn�e.
     *
     * @param index index de la description voulue.
     * @return description associ� a l'index.
     */
    public String getDescription(Integer index) {
        return desciption.get(index);
    }

    /**
     * Permet d'obtenir les bytes associ� e un index donn�e.
     *
     * @param index index des bytes voulue.
     * @return les bytes charger si il sont pr�sent dans Map sinon NULLBYTES 
     * ({@link VTPlayer#NULLBYTES}).
     */
    public Byte[] getPicots(Integer index) {
        Byte[] bytes = picots.get(index);
        if (bytes == null) {
            bytes = VTPlayerInterface.NULLBYTES;
        }
        return bytes;
    }

    /**
     * Remplace ou ajoute les bytes donn�e a un index donn�e.
     *
     * @param index index des picots.
     * @param bytes bytes associ� a cette index.
     */
    public void setPicots(Integer index, Byte bytes[]) {
        picots.put(index, bytes);
    }

    /**
     * Sauvegarde du fichier dans le r�pertoire d�finie lors de la construction
     * de l'objet.
     *
     * @throws FileNotFoundException si le fichier n'a pu �tre trouver.
     * @throws IOException si une erreur est survenu lors de l'�criture du
     * fichier.
     * @see #save(java.lang.String)
     */
    public void save() throws FileNotFoundException, IOException {
        save(pathPicot);
    }

    /**
     * Sauvegarde du fichier dans le r�pertoire d�finie lors de la construction
     * de l'objet.
     *
     * @param path chemin du fichier dans lequel enregistr�.
     * @throws FileNotFoundException si le fichier n'a pu �tre trouver.
     * @throws IOException si une erreur est survenu lors de l'�criture du
     * fichier.
     * @see #save()
     */
    public void save(String path) throws FileNotFoundException, IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            for (Map.Entry<Integer, Byte[]> entry : picots.entrySet()) {
                Integer integer = entry.getKey();
                Byte[] bytes = entry.getValue();
                // �criture de l'index
                pw.printf("%d", integer);
                for (int i = 0; i < 4; i++) {
                    // �criture des quatre bytes associ� a l'index
                    pw.printf(DELIMITEUR + "%x", bytes[i]);
                }
                // retourn a la ligne
                pw.println();
            }
        }
    }

    public HashMap<Integer, Byte[]> getPicots() {
        return picots;
    }
}
