package vtplayer;

/**
 *
 * @author godeau
 */
public interface VTPlayerInterface {

    /**
     * Valeurs pour la quel tous les picots sont baisser.
     */
    public static final Byte[] NULLBYTES = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
    /**
     * Écart par défaut entre deux configuration de picots lorsque celui-ci n'est pas spécifier.
     * L'écart est toujours exprimer en milliseconde
     */
    public static final int DEFAULT_ECART = 500;
    /**
     * Séparateur des bytes dans une chaîne par défaut.
     */
    public static final String DEFAULT_SEP = " ";

    /**
     * Permet de vérifier si le périphérique est bien ouvert. <br/>
     * <strong><u>ATTENTION :</u></strong> ne garantie pas que le périphérique
     * est encore branché.
     *
     * @return vrai si le périphérique est ouvert faux sinon.
     */
    public boolean isOpen();

    /**
     * Lève est abaisse les picots. Si la chaîne est composer de huit bytes
     * alors la fonction ce comporte comme si on avez appelez set(bytes,
     * {@value #DEFAULT_ECART}).
     *
     * @param bytes chaîne de byte séparer par des la valeur par défaut
     * {@value #DEFAULT_SEP} (au maximum 8 bytes).
     * @throws VTPlayerException Si la chaîne est composer de trop de byte, ou
     * si l'un des byte est invalide.
     */
    public void set(String bytes) throws VTPlayerException;

    /**
     * Lève est abaisse les picots a une fréquence donner. Si la chaîne n'est
     * composer que de quatre bytes alors la fréquence est ignorer.
     *
     * @param bytes chaîne de byte séparer par des la valeur par défaut
     * {@value #DEFAULT_SEP} (au maximum 8 bytes).
     * @param ecart fréquence entre les deux configuration de picots donner. Est
     * exprimer en millisecond
     * @throws VTPlayerException
     */
    public void set(String bytes, int ecart) throws VTPlayerException;

    /**
     * lève est abaisse les picots.
     *
     * @param bytes chaîne de byte séparer par un séparateur.
     * @param separateur caractères séparent chaque bytes
     * @throws VTPlayerException Si la chaîne est composer de trop de byte, ou
     * si l'un des byte est invalide.
     */
    public void set(String bytes, String separateur) throws VTPlayerException;

    /**
     *
     * @param bytes
     * @param separateur
     * @param ecart
     * @throws VTPlayerException
     */
    public void set(String bytes, String separateur, int ecart) throws VTPlayerException;

    /**
     * lève est abaisse les picots. Revient a appeler <i>set(stringToByte(b1),
     * stringToByte(b2), stringToByte(b3), stringToByte(b4))</i>.
     *
     * @param b1, b2, b3, b4 chaîne de caractère a convertir en byte.
     * @throws VTPlayerException si l'un des byte est invalide.
     */
    public void set(String b1, String b2, String b3, String b4) throws VTPlayerException;

    /**
     *
     * @param b1, b2, b3, b4
     * @param b5, b6, b7, b8
     * @param ecart
     * @throws VTPlayerException
     */
    public void set(String b1, String b2, String b3, String b4, String b5, String b6, String b7, String b8, int ecart) throws VTPlayerException;

    /**
     *
     * @param b1, b2, b3, b4
     * @param b5, b6, b7, b8
     * @throws VTPlayerException
     */
    public void set(String b1, String b2, String b3, String b4, String b5, String b6, String b7, String b8) throws VTPlayerException;

    /**
     * Lève est abaisse les picots.
     *
     * @param buffer tableau de au plus quatre bytes.
     * @throws VTPlayerException Si la liaison avec le périphérique échoue.
     */
    public void set(Byte buffer[]) throws VTPlayerException;

    /**
     *
     * @param buffer
     * @param ecart
     * @throws VTPlayerException
     */
    public void set(Byte buffer[], int ecart) throws VTPlayerException;

    /**
     *
     * @param buffer1
     * @param buffer2
     * @param ecart
     * @throws VTPlayerException
     */
    public void set(Byte buffer1[], Byte buffer2[], int ecart) throws VTPlayerException;

    /**
     *
     * @param buffer1
     * @param buffer2
     * @throws VTPlayerException
     */
    public void set(Byte buffer1[], Byte buffer2[]) throws VTPlayerException;

    /**
     * lève est abaisse les picots.
     *
     * @param b1, b2, b3, b4 byte permettent de définir les picots a lever et a
     * baisse.
     * @throws VTPlayerException Si le périphérique n'est pas ouvert ou si un
     * problème est reporter depuis jni.
     */
    public void set(byte b1, byte b2, byte b3, byte b4) throws VTPlayerException;

    /**
     *
     * @param b1, b2, b3, b4
     * @param b5, b6, b7, b8
     * @param ecart
     * @throws VTPlayerException
     */
    public void set(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, int ecart) throws VTPlayerException;

    /**
     *
     * @param b1, b2, b3, b4
     * @param b5, b6, b7, b8
     * @throws VTPlayerException
     */
    public void set(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) throws VTPlayerException;

    /*public void setLeft(String bytes) throws VTPlayerException;
     public void setLeft(String bytes, String separateur)throws VTPlayerException;
     public void setLeft(String bytes, int ecart) throws VTPlayerException;
     public void setLeft(String bytes, String separateur, int ecart)throws VTPlayerException;
     public void setLeft(String b1, String b2, String b3, String b4)throws VTPlayerException;
     public void setLeft(String b1, String b2, String b3, String b4, int ecart)throws VTPlayerException;*/
    /**
     *
     * @param bytes
     * @throws VTPlayerException
     */
    public void setLeft(Byte bytes[]) throws VTPlayerException;

    /**
     *
     * @param bytes
     * @param ecart
     * @throws VTPlayerException
     */
    public void setLeft(Byte bytes[], int ecart) throws VTPlayerException;

    /**
     *
     * @param b1_1
     * @param b2_1
     * @param b1_2
     * @param b2_2
     * @throws VTPlayerException
     */
    public void setLeft(byte b1_1, byte b2_1, byte b1_2, byte b2_2) throws VTPlayerException;

    /**
     *
     * @param b1_1
     * @param b2_1
     * @param b1_2
     * @param b2_2
     * @param ecart
     * @throws VTPlayerException
     */
    public void setLeft(byte b1_1, byte b2_1, byte b1_2, byte b2_2, int ecart) throws VTPlayerException;

    /**
     *
     * @param b1
     * @param b2
     * @throws VTPlayerException
     */
    public void setLeft(byte b1, byte b2) throws VTPlayerException;

    /**
     *
     * @param b1
     * @param b2
     * @param ecart
     * @throws VTPlayerException
     */
    public void setLeft(byte b1, byte b2, int ecart) throws VTPlayerException;

    /*public void setRight() throws VTPlayerException;
     public void setRight(String bytes, String separateur)throws VTPlayerException;
     public void setRight(String bytes, int ecart) throws VTPlayerException;
     public void setRight(String bytes, String separateur, int ecart)throws VTPlayerException;
     public void setRight(String b1, String b2, String b3, String b4)throws VTPlayerException;
     public void setRight(String b1, String b2, String b3, String b4, int ecart)throws VTPlayerException;*/
    /**
     *
     * @param bytes
     * @throws VTPlayerException
     */
    public void setRight(Byte bytes[]) throws VTPlayerException;

    /**
     *
     * @param bytes
     * @param ecart
     * @throws VTPlayerException
     */
    public void setRight(Byte bytes[], int ecart) throws VTPlayerException;

    /**
     *
     * @param b1_1
     * @param b2_1
     * @param b1_2
     * @param b2_2
     * @throws VTPlayerException
     */
    public void setRight(byte b1_1, byte b2_1, byte b1_2, byte b2_2) throws VTPlayerException;

    /**
     *
     * @param b1_1
     * @param b2_1
     * @param b1_2
     * @param b2_2
     * @param ecart
     * @throws VTPlayerException
     */
    public void setRight(byte b1_1, byte b2_1, byte b1_2, byte b2_2, int ecart) throws VTPlayerException;

    /**
     *
     * @param b1
     * @param b2
     * @throws VTPlayerException
     */
    public void setRight(byte b1, byte b2) throws VTPlayerException;

    /**
     *
     * @param b1
     * @param b2
     * @param ecart
     * @throws VTPlayerException
     */
    public void setRight(byte b1, byte b2, int ecart) throws VTPlayerException;

    /**
     * Fermeture du périphérique.
     *
     * @throws VTPlayerException si la fermeture du périphérique échoue
     */
    public void close() throws VTPlayerException;
}
