package vocal;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GODEAU Quentin
 */
public class LecteurTexteThread extends Thread {

    public static final String PATH_PHONETIQUE = "config/phonetique.conf";
    protected SpeakToMe lecteurTexte = new SpeakToMe();

    @Override
    public synchronized void run() {
        try {
            while (!isInterrupted()) {
                wait();
                lecteurTexte.playAll();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(LecteurTexteThread.class.getName()).log(Level.INFO, "Le processus gérant la voix a été interrompu.");
        }
    }

    public synchronized void setTexte(String str) {
        if (str != null && !str.isEmpty()) {
            lecteurTexte.setTexte(str);
            notify();
        }
    }

    public synchronized void positionnement(int col, int ligne) {
        lecteurTexte.positionnement(col, ligne);
        notify();
    }
}
