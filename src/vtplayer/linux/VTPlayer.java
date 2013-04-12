package vtplayer.linux;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerImpl;

/**
 * Classe permettant la liaison entre java et la souris (n'est compatible
 * uniquement que avec linux).
 * <br />
 * Les exceptions de cette classe sont a traite ou a afficher a l'utilisateur.
 * <br />
 * <strong><u>ATTENTION :</u></strong> le déplacement de cette classe dans un
 * autre package ou dans un autre sous package peut empêcher le bon
 * fonctionnement de celle-ci.
 * <br />
 * Pour en savoir plus sur le fonctionnement de la souris :
 * {@link <a href="http://vtplayer.sourceforge.net/">VTPlayer</a>}
 *
 * @author godeau
 */
public class VTPlayer extends VTPlayerImpl {

    /**
     * Descripteur de fichier vers le périphérique.
     */
    private boolean open = false;

    private static native int vtplayer_init_mouse(String device);

    private static native int vtplayer_init_thread();

    private static native int vtplayer_init_data();

    private static native void vtplayer_set(byte b1, byte b2, byte b3, byte b4);

    private static native void vtplayer_set(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, long ecart);

    private static native int vtplayer_release_mouse();

    private static native void vtplayer_stop_thread();

    /**
     * Essai de chargement de la librairie.
     *
     * @trows UnsatisfiedLinkError si la librairie n'a pu etre charger.
     */
    static {
        try {
            System.loadLibrary("libJNI");
        } catch (UnsatisfiedLinkError | NullPointerException exception) {
            Logger.getLogger(VTPlayer.class.getName()).log(Level.WARNING, "The library can not be found in the \"{0}\".", System.getProperty("java.library.path").replace(";", "\n"));
            try {
                VTPlayer.loadLibraryFromJar("/vtplayer/linux/libJNI.so");
            } catch (IOException ex) {
                Logger.getLogger(VTPlayer.class.getName()).log(Level.SEVERE, "The library can not be extrat from the .jar file.", ex);
                throw new UnsatisfiedLinkError("The library needed for the VTPlayer mouse can not be find, fatal error.");
            }
        }
    }

    /**
     * Instancie seulement la classe nécessite l'appel de {
     *
     * @see #open(java.lang.String)} ou de {
     * @see #open(java.io.File)} pour pouvoir ensuite être fonctionnel.
     */
    public VTPlayer() {
    }

    /**
     * Permet d'ouvrir directement directement le périphérique.
     *
     * @param device chemin vers le fichier périphérique.
     * @throws VTPlayerException dans le cas d'echec d'ouverture du
     * périphérique.
     * @see #findDevice()
     * @see #findDevice(java.lang.String)
     * @see #findDevices()
     * @see #findDevices(java.lang.String)
     */
    public VTPlayer(String device) throws VTPlayerException {
        open(device);
    }

    /**
     * Permet d'ouvrir directement directement le périphérique.
     *
     * @param file fichier périphérique.
     * @throws VTPlayerException dans le cas d'echec d'ouverture du
     * périphérique.
     * @see #findDevice()
     * @see #findDevice(java.lang.String)
     * @see #findDevices()
     * @see #findDevices(java.lang.String)
     */
    public VTPlayer(File file) throws VTPlayerException {
        if (file != null) {
            open(file);
        }
    }

    /**
     * Permet d'ouvrir une périphérique donner.<br />
     * <strong><u>ATTENTION :</u></strong> penser a vérifier que l'utilisateur a
     * bien les droit sur le périphérique.
     *
     * @param device chemin vers le périphérique.
     * @throws VTPlayerException si l'ouverture a echouer. Cette exception est
     * de preference a afficher a l'utilisateur
     * @see #findDevice()
     * @see #findDevice(java.lang.String)
     * @see #findDevices()
     * @see #findDevices(java.lang.String)
     */
    final public void open(String device) throws VTPlayerException {
        // fermeture du périphérique si celui ci est encore ouvert.
        if (isOpen()) {
            close();
        }

        // ouverture du périphérique grace au fonction native.
        if(vtplayer_init_mouse(device) < 0){
            throw new VTPlayerException("Échec d'initialisation de la souris.\n"
                    + "Vérifier que :\n"
                    + " - Que le périphérique est bien brancher.\n"
                    + " - Que le module et bien charger dans le noyau \"lsmod | grep vtplayer\".\n"
                    + " - Que la souris a correctement ete monter par le noyau \"dsmeg | tail -5\".\n"
                    + " - Que le lien et les droits sont bien attribuer a votre périphérique \"ls -l /dev/vtplayer*\".");
        }


        if (vtplayer_init_data() < 0) {
            vtplayer_release_mouse();
            throw new VTPlayerException("Mémoire insufisante ou echec de création d'un segment de mémoire partagé");
        }

        if (vtplayer_init_thread() < 0) {
            vtplayer_release_mouse();
            throw new VTPlayerException("Erreur lors de la création du thread.");
        }

        open = true;
    }

    /**
     * Permet d'ouvrir une périphérique donner.<br />
     *
     * @param file fichier périphérique.
     * @throws VTPlayerException si l'ouverture a echouer ou si l'on ne peut
     * ecrire sur le fichier. Cette exception est de preference a afficher a
     * l'utilisateur.
     * @see #findDevice()
     * @see #findDevice(java.lang.String)
     * @see #findDevices()
     * @see #findDevices(java.lang.String)
     */
    final public void open(File file) throws VTPlayerException {
        if (!file.canWrite()) {
            throw new VTPlayerException("Erreur vous n'avez pas le droit decriture sur votre périphérique, tapez : \""
                    + "sudo chmod 622 "
                    + file.getAbsolutePath() + " pour resoudre le probleme.");
        }
        
        open(file.getAbsolutePath());
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void set(byte b1, byte b2, byte b3, byte b4) throws VTPlayerException {
        if (!isOpen()) {
            throw new VTPlayerException("Echec le périphérique n'est pas ouvert. Veuillez utiliser open avant l'appel de set.");
        }
        
        copy_bytes(b1, b2, b3, b4);
        vtplayer_set(b1, b2, b3, b4);
    }

    @Override
    public void set(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, int ecart) throws VTPlayerException {
        if(!isOpen()){
            throw new VTPlayerException("Echec ! Le péripherique n'est pas ouvert. veuillez utiliser open avant l'appel de set.");
        }
        
        copy_bytes(b1, b2, b3, b4, b5, b6, b7, b8);
        vtplayer_set(b1, b2, b3, b4, b5, b6, b7, b8, ecart);
    }

    /**
     * Recherche dans le repertoire <i>/dev/</i> le fichier susceptible d'etre
     * la souris. Revient a appeler <i>findDevice("/dev/")</i>. <br />
     * <strong><i>ATTENTION :</i></strong> Aucun garanti n'est donner au faite
     * que le fichier retourner soit le bon. En effet le premier fichier
     * trouver, repondant au critere, est immediatement retourner.
     *
     * @return fichier vers le périphérique ou nul si aucun fichier ne
     * correspond au critere.
     * @see #findDevice(java.lang.String)
     * @see #findDevices()
     * @see #findDevices(java.lang.String)
     */
    public static File findDevice() {
        return findDevice("/dev/");
    }

    /**
     * Recherche dans le repertoire dans le repertoire specifier tout fichier
     * susceptible d'etre la souris.<br />
     * <strong><i>ATTENTION :</i></strong> Aucun garanti n'est donner au faite
     * que le fichier retourner soit le bon. En effet le premier fichier
     * trouver, repondant au critere, est immediatement retourner.
     *
     * @param dir repertoire de recherche.
     * @return fichier vers le périphérique ou nul si aucun fichier ne
     * correspond au critere.
     * @see #findDevice()
     * @see #findDevices()
     * @see #findDevices(java.lang.String)
     */
    public static File findDevice(String dir) {
        File listOfFiles[] = new File(dir).listFiles();
        boolean find = false;
        int cpt = 0;

        while (!find && cpt < listOfFiles.length) {
            if (listOfFiles[cpt].getName().startsWith("vtplayer")) {
                find = true;
            }
            cpt++;
        }

        if (find) {
            return listOfFiles[cpt - 1];
        }

        return null;
    }

    /**
     * Recherche dans le repertoire dans le repertoire specifier tout fichier
     * susceptible d'etre la souris. Revient a appeler
     * <i>findDevices("/dev/")</i>. <br/>
     * <strong><i>ATTENTION :</i></strong> Aucun garanti n'est donner au faite
     * que la liste de fichier retourner soit le bonnes.
     *
     * @return liste des fichiers validant les criteres.
     * @see #findDevice()
     * @see #findDevice(java.lang.String)
     * @see #findDevices(java.lang.String)
     */
    public static ArrayList<File> findDevices() {
        return findDevices("/dev/");
    }

    /**
     * Recherche dans le repertoire dans le repertoire specifier tout fichier
     * susceptible d'etre la souris.<br />
     * <strong><i>ATTENTION :</i></strong> Aucun garanti n'est donner au faite
     * que la liste de fichier retourner soit le bonnes.
     *
     * @param dir repertoire de recherche.
     * @return liste des fichiers validant les criteres.
     * @see #findDevice()
     * @see #findDevice(java.lang.String)
     * @see #findDevices()
     */
    public static ArrayList<File> findDevices(String dir) {
        ArrayList<File> path = new ArrayList<>();

        for (File file : new File(dir).listFiles()) {
            if (file.getName().startsWith("vtplayer")) {
                path.add(file);
            }
        }

        return path;
    }

    @Override
    public void close() throws VTPlayerException {
        if (isOpen()) {
            vtplayer_stop_thread();
            
            if (vtplayer_release_mouse() < 0) {
                throw new VTPlayerException("La fermeture du périphérique a echoué.");
            }
            
            open = false;
        }
    }
}
