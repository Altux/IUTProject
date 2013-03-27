package vtplayer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author godeau, lemonnier, 
 */
public class Test {

    public static void main(String args[]) {
        try {
            VTPlayerInterface vtp = VTPlayerManager.getInstance();
            VTPlayerManager.open(vtp);
            if (vtp.isOpen()) {
                for (int i = 0; i < 10; i++) {
                    vtp.set("99 77 B9 5D");
                    Thread.sleep(1000);
                    vtp.set("B9 5D 99 77");
                    Thread.sleep(1000);
                }
                vtp.close();
            } else {
                System.err.println("The device could be opened.");
            }
        } catch (VTPlayerException | InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
