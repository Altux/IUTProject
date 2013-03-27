package vtplayer;

/**
 *
 * @author godeau
 */
public class VTPlayerManager {

    private static VTPlayerInterface vtp = null;
    private static final boolean isWindows = System.getProperty("os.name").startsWith("Windows");

    public static VTPlayerInterface getInstance() {
        if (vtp == null) {
            if (isWindows) {
                vtp = new vtplayer.windows.VTPlayer();
            } else {
                vtp = new vtplayer.linux.VTPlayer();
            }
        }

        return vtp;
    }

    public static void open(VTPlayerInterface vpt) throws VTPlayerException {
        if (isWindows) {
            ((vtplayer.windows.VTPlayer)vpt).open();
        } else {
            ((vtplayer.linux.VTPlayer)vpt).open(vtplayer.linux.VTPlayer.findDevice());
        }
    }
}
