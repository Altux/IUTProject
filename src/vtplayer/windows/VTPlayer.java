package vtplayer.windows;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerImpl;

/**
 * Classe permettant la liaison entre java et la souris (n'est compatible
 * uniquement que avec windows). <br /> 
 * Les exceptions de cette classe sont a traite ou a afficher a l'utilisateur. <br /> 
 * 
 * <strong><u>ATTENTION :</u></strong> le déplacement de cette classe dans un 
 * autre package ou dans un autre sous package peut empêcher le bon fonctionnement 
 * de celle-ci. <br />
 * 
 * Pour en savoir plus sur le fonctionnement de la souris :
 * {@link <a href="http://vtplayer.sourceforge.net/">VTPlayer</a>}
 *
 * @author godeau
 */
public class VTPlayer extends VTPlayerImpl {
    
    private final static int VTP_ERROR_OPENING                     = -1;
    private final static int VTP_ERROR_SETTING_CONFIGURATION       = -2;
    private final static int VTP_ERROR_CLAIMING_INTERFACE          = -3;
    private final static int VTP_ERROR_SETTING_ALTERNATE_INTERFACE = -4;
    private final static int VTP_NOT_FOUND                         = -5;

    private final static int VTP_ERROR_USB_RELEASE                 = -1;
    private final static int VTP_ERROR_USB_CLOSE                   = -2 ;   

    /**
     * Descripteur de fichier vers le périphérique.
     */
    private boolean open = false;

    /**
     * Permet d'ouvrir le périphérique.
     *
     * @param device chemin vers le périphérique a ouvrir.
     * @return le descripteur vers le fichier périphérique ou nul en cas
     * d'échec.
     */
    private static native int vtplayer_init_mouse();

    private static native int vtplayer_init_thread();

    private static native void vtplayer_init_data();

    /**
     *
     * @param b1, b2, b3, b4
     */
    private static native void vtplayer_set(byte b1, byte b2, byte b3, byte b4);

    /**
     *
     * @param b1, b2, b3, b4
     * @param b5, b6, b7, b8
     * @param ecart
     */
    private static native void vtplayer_set(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, int ecart);

    /**
     *
     * @return
     */
    private static native int vtplayer_release_mouse();

    /**
     *
     */
    private static native void vtplayer_stop_thread();

    /**
     * Essai de chargement de la librairie.
     *
     * @trows UnsatisfiedLinkError si la librairie n'a pu être charger.
     */
    static {
        boolean x64 = System.getProperty("sun.arch.data.model").equals("64");
        try {
            System.loadLibrary("libJNI" + (x64 ? "x64" : "x86"));
        } catch (UnsatisfiedLinkError | NullPointerException exception) {
            Logger.getLogger(vtplayer.windows.VTPlayer.class.getName()).log(Level.INFO, "The library can not be found in :\n{0}", System.getProperty("java.library.path").replace(";", "\n"));
            try {
                vtplayer.windows.VTPlayer.loadLibraryFromJar("/vtplayer/windows/libJNI" + (x64 ? "x64" : "x86") + ".dll");
            } catch (IOException ex) {
                Logger.getLogger(vtplayer.windows.VTPlayer.class.getName()).log(Level.SEVERE, "The library can not be extrat from the .jar file.", ex);
                throw new UnsatisfiedLinkError("The library needed for the VTPlayer mouse can not be find, fatal error.");
            }
        }
    }

    public VTPlayer() {
    }

    public void open() throws VTPlayerException {
        // fermeture du périphérique si celui ci est encore ouvert.
        if (isOpen()) {
            close();
        }

        switch (vtplayer_init_mouse()){
            case VTP_ERROR_OPENING:
                throw new VTPlayerException("Fail to open the interface");
                
            case VTP_ERROR_SETTING_CONFIGURATION:
                throw new VTPlayerException("Fail to configurate the interface");
                
            case VTP_ERROR_CLAIMING_INTERFACE:
                Logger.getLogger(vtplayer.windows.VTPlayer.class.getName()).log(Level.SEVERE, "Fail to claim the interface");
                // throw new VTPlayerException("Fail to claim the interface");
                break;
                
            case VTP_ERROR_SETTING_ALTERNATE_INTERFACE:
                throw new VTPlayerException("Fail to alternate the interface");
                
            case VTP_NOT_FOUND:
                throw new VTPlayerException("The VTPlayer mouse can not be found, please check the mouse is connected.");
        }
        
        vtplayer_init_data();
        if(vtplayer_init_thread() < 0) throw new VTPlayerException("Erreur lors de la création du thread.");

        // ouverture du périphérique grace au fonction native.
        open = true;

        // verification que l'ouverture c'est bien derouler.
        if (!isOpen()) {
            throw new VTPlayerException(
                    "The device can not be open please check :\n"
                    + " - Que le périphérique est bien brancher.");
        }
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
        if (!isOpen()) {
            throw new VTPlayerException("Echec le périphérique n'est pas ouvert. Veuillez utiliser open avant l'appel de set.");
        }
        
        copy_bytes(b1, b2, b3, b4, b5, b6, b7, b8);
        
        vtplayer_set(b1, b2, b3, b4, b5, b6, b7, b8, ecart);
    }

    @Override
    public void close() throws VTPlayerException {
        if (isOpen()) {
            vtplayer_stop_thread();

            switch (vtplayer_release_mouse()) {
                  case VTP_ERROR_USB_RELEASE: 
                      throw new VTPlayerException("erreur release usb a echoué."); 
                      
                  case VTP_ERROR_USB_CLOSE: 
                      throw new VTPlayerException("La fermeture du périphérique a echoué."); 
            }
        }
        open = false;
    }
}
