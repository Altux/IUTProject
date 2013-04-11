package parametres;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ParametresModele extends Config {

    public final static String FICHIER = "config/parametre.ini";
    protected String pathConfig;
    private Properties proprietes;

    public static Properties load(String filename) throws IOException, FileNotFoundException {
        Properties proprietes = new Properties();

        try (FileInputStream input = new FileInputStream(filename)) {
            proprietes.load(input);
        } finally {
            return proprietes;
        }
    }

    public ParametresModele(String path) {
        pathConfig = path;
        try {
            proprietes = ParametresModele.load(path);
        } catch (IOException exception) {
        }
    }

    public ParametresModele() {
        this(FICHIER);
    }

    public void sauvegarder() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(FICHIER)) {
            proprietes.store(fos, " configuration du son et de la norme \n DEFAULT CONFIGURATION \n norme=US or EU \n son=true or false");
        }

        setChanged();
        notifyObservers();
        System.out.println("Mise à jour effectuée");
    }

    @Override
    public String getNorme() {
        return proprietes.getProperty("Norme", "EU");
    }

    public void setNorme(String norme) {
        proprietes.setProperty("Norme", norme);
    }

    @Override
    public boolean getSon() {
        return Boolean.valueOf(proprietes.getProperty("Son", "false"));
    }

    public void setSon(String sonValue) {
        proprietes.setProperty("Son", sonValue);
    }

    @Override
    public boolean getVTPlayerMouse() {
        return Boolean.valueOf(proprietes.getProperty("VTPlayerMouse", "false"));
    }

    public void setVTPlayerMouse(String vtpmouse) {
        proprietes.setProperty("VTPlayerMouse", vtpmouse);
    }

    @Override
    public boolean getAutoResize() {
        return Boolean.valueOf(proprietes.getProperty("AutoResize", "false"));
    }

    public void setAutoResize(String autoRz) {
        proprietes.setProperty("AutoResize", autoRz);
    }

    @Override
    public int getFrequencePicots() {
        return Integer.valueOf(proprietes.getProperty("FrequencePicots", "1000"));
    }

    public void setFreqPicots(String freqPicot) {
        proprietes.setProperty("FrequencePicots", freqPicot);
    }

    @Override
    public int getKeyRotation() {
        return Integer.valueOf(proprietes.getProperty("KeyRotation", String.valueOf(KeyEvent.VK_R)));
    }

    public void setKeyRot(String keyRot) {
        proprietes.setProperty("KeyRotation", keyRot);
    }

    @Override
    public int getKeySuppresion() {
        return Integer.valueOf(proprietes.getProperty("KeySuppresion", String.valueOf(KeyEvent.VK_DELETE)));
    }

    public void setKeySuppr(String keySupp) {
        proprietes.setProperty("KeySuppresion", keySupp);
    }

    @Override
    public int getKeyEchapement() {
        return Integer.valueOf(proprietes.getProperty("KeyEchapement", String.valueOf(KeyEvent.VK_ESCAPE)));
    }

    public void setKeyEchapement(String keyEsc) {
        proprietes.setProperty("KeyEchapement", keyEsc);
    }

    @Override
    public int getKeyChangeBit() {
        return Integer.valueOf(proprietes.getProperty("KeyChangeBit", String.valueOf(KeyEvent.VK_B)));
    }

    public void setKeyChangeBit(String keyChgeBit) {
        proprietes.setProperty("KeyChangeBit", keyChgeBit);
    }

    @Override
    public int getKeySpatialization() {
        return Integer.valueOf(proprietes.getProperty("KeySpatialization", String.valueOf(KeyEvent.VK_C)));
    }

    public void setKeySpatialization(String keySpa) {
        proprietes.setProperty("KeySpatialization", keySpa);
    }

    Properties getProprietes() {
        return proprietes;
    }

    void setProprietes(Properties proprietes) {
        this.proprietes = proprietes;
    }

    public String getPathConfig() {
        return pathConfig;
    }
}
