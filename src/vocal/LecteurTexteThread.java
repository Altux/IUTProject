package vocal;

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GODEAU Quentin
 * @deprecated 
 */
public class LecteurTexteThread extends Thread {

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

    public synchronized void positionnement(Point p){
        positionnement(p.x, p.y);
    }
    
    public synchronized void positionnement(int ligne, int col) {
        lecteurTexte.positionnement(ligne, col);
        notify();
    }
}
