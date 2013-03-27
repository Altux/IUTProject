package vtplayer.windows;

import vtplayer.ByteUtilitaire;
import vtplayer.VTPlayerException;

/**
 *
 * @author GODEAU Quentin
 */
public class Test {

    public static void main(String args[]) throws VTPlayerException, InterruptedException {
        VTPlayer vtp = new VTPlayer();
        vtp.open();
        // initialise 
        vtp.set((byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff);
        Thread.sleep(1000);

        while (true) {
            for (int cptL = 0; cptL < 4; cptL++) {
                for (int cptC = 0; cptC < 4; cptC++) {
                    vtp.setLeft(ByteUtilitaire.position_to_byte(cptL, cptC));
                    vtp.setRight(ByteUtilitaire.position_to_byte(cptL, 3 - cptC));
                    Thread.sleep(200);
                }

            }
            vtp.set((byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff);
        }
        /*
         * vtp.setLeft((byte) 0xff, (byte) 0xff, 500); System.out.println("FIN
         * TEST 1"); Thread.sleep(5000); vtp.set((byte) 0x00, (byte) 0x00,
         * (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff,
         * (byte) 0xff, 500); Thread.sleep(5000); vtp.setLeft((byte) 0xff,
         * (byte) 0xff, 500); System.out.println("FIN TEST 2");
         */
        //Thread.sleep(5000);
    }
}
