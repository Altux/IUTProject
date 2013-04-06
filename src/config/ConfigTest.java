package config;

import parametres.Config;
import java.awt.event.KeyEvent;

/**
 *
 * @author godeau
 */
public class ConfigTest extends Config {

    @Override
    public boolean getSon() {
        return false;
    }

    @Override
    public boolean getVTPlayerMouse() {
        return false;
    }

    @Override
    public int getFrequencePicots() {
        return 1000;
    }

    @Override
    public String getNorme() {
        return "";
    }

    @Override
    public int getKeyRotation() {
        return KeyEvent.VK_R;
    }

    @Override
    public int getKeySuppresion() {
        return KeyEvent.VK_DELETE;
    }

    @Override
    public int getKeyEchapement() {
        return KeyEvent.VK_ESCAPE;
    }

    @Override
    public int getKeyChangeBit() {
        return KeyEvent.VK_B;
    }

    @Override
    public int getKeySpatialization() {
        return KeyEvent.VK_C;
    }

    @Override
    public boolean getAutoResize() {
        return false;
    }
    
}
