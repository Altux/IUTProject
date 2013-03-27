package vtplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class VTPlayerImpl implements VTPlayerInterface {

    private Byte bytes[] = {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};

    @Override
    public void set(String bytes) throws VTPlayerException {
        set(bytes, DEFAULT_SEP);
    }

    @Override
    public void set(String bytes, int ecart) throws VTPlayerException {
        set(bytes, DEFAULT_SEP, ecart);
    }

    @Override
    public void set(String bytes, String separateur) throws VTPlayerException {
        set(bytes, separateur, DEFAULT_ECART);
    }

    @Override
    public void set(String bytes, String separateur, int ecart) throws VTPlayerException {
        String array[] = bytes.split(separateur);
        if (array.length == 4) {
            set(array[0], array[1], array[2], array[3]);
        } else if (array.length == 8) {
            set(array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], ecart);
        } else {
            throw new VTPlayerException("Erreur, le nombre de byte composent la chaine \"" + bytes + "\" n'est pas de 4 ou de 8.");
        }
    }

    @Override
    public void set(String b1, String b2, String b3, String b4) throws VTPlayerException {
        set(ByteUtilitaire.string_hexa_to_byte(b1), ByteUtilitaire.string_hexa_to_byte(b2), ByteUtilitaire.string_hexa_to_byte(b3), ByteUtilitaire.string_hexa_to_byte(b4));
    }

    @Override
    public void set(String b1, String b2, String b3, String b4, String b5, String b6, String b7, String b8) throws VTPlayerException {
        set(b1, b2, b3, b4, b5, b6, b7, b8, DEFAULT_ECART);
    }

    @Override
    public void set(Byte buffer[]) throws VTPlayerException {
        set(buffer, DEFAULT_ECART);
    }

    @Override
    public void set(Byte[] buffer, int ecart) throws VTPlayerException {
        if (buffer.length == 4) {
            set(buffer[0], buffer[1], buffer[2], buffer[3]);
        } else if (buffer.length == 8) {
            set(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5], buffer[6], buffer[7], ecart);
        } else {
            throw new VTPlayerException("Erreur, trop de byte on ete passer en parametre.");
        }
    }

    @Override
    public void set(Byte[] buffer1, Byte[] buffer2) throws VTPlayerException {
        set(buffer1, buffer2, DEFAULT_ECART);
    }

    @Override
    public void set(Byte[] buffer1, Byte[] buffer2, int ecart) throws VTPlayerException {
        if (buffer1.length != 4 || buffer2.length != 4) {
            throw new VTPlayerException("Erreur taille");
        }
        set(buffer1[0], buffer1[1], buffer1[2], buffer1[3], buffer2[0], buffer2[1], buffer2[2], buffer2[3], ecart);
    }

    @Override
    public void set(String b1, String b2, String b3, String b4, String b5, String b6, String b7, String b8, int ecart) throws VTPlayerException {
        set(
                ByteUtilitaire.string_hexa_to_byte(b1),
                ByteUtilitaire.string_hexa_to_byte(b2),
                ByteUtilitaire.string_hexa_to_byte(b3),
                ByteUtilitaire.string_hexa_to_byte(b4),
                ByteUtilitaire.string_hexa_to_byte(b5),
                ByteUtilitaire.string_hexa_to_byte(b6),
                ByteUtilitaire.string_hexa_to_byte(b7),
                ByteUtilitaire.string_hexa_to_byte(b8),
                ecart);
    }

    @Override
    public void set(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) throws VTPlayerException {
        set(b1, b2, b3, b4, b5, b6, b7, b8, DEFAULT_ECART);
    }

    @Override
    public void setLeft(Byte[] bytes) throws VTPlayerException {
        if (bytes.length == 2) {
            setLeft(bytes[0], bytes[1]);
        } else if (bytes.length == 4) {
            setLeft(bytes, DEFAULT_ECART);
        } else {
            throw new VTPlayerException("taille du tableau differente à 4 ou 2");
        }
    }

    @Override
    public void setLeft(Byte[] bytes, int ecart) throws VTPlayerException {
        if (bytes.length == 2) {
            setLeft(bytes[0], bytes[1], ecart);
        } else if (bytes.length == 4) {
            setLeft(bytes[0], bytes[1], bytes[2], bytes[3], ecart);
        } else {
            throw new VTPlayerException("taille du tableau differente à 4 ou 2");
        }
    }

    @Override
    public void setLeft(byte b1_1, byte b2_1, byte b1_2, byte b2_2) throws VTPlayerException {
        setLeft(b1_1, b2_1, b1_2, b2_2, DEFAULT_ECART);
    }

    @Override
    public void setLeft(byte b1, byte b2) throws VTPlayerException {
        set(b1, b2, bytes[2], bytes[3]);
    }

    @Override
    public void setLeft(byte b1, byte b2, int ecart) throws VTPlayerException {
        set(b1, b2, bytes[2], bytes[3],
                bytes[4], bytes[5], bytes[6], bytes[7], ecart);
    }

    @Override
    public void setLeft(byte b1_1, byte b2_1, byte b1_2, byte b2_2, int ecart) throws VTPlayerException {
        set(b1_1, b2_1, bytes[2], bytes[3],
                b1_2, b2_2, bytes[6], bytes[7], ecart);
    }

    @Override
    public void setRight(Byte bytes[]) throws VTPlayerException {
        if (bytes.length == 2) {
            setRight(bytes[0], bytes[1]);
        } else if (bytes.length == 4) {
            setRight(bytes[0], bytes[1], bytes[2], bytes[3], DEFAULT_ECART);
        } else {
            throw new VTPlayerException("taille du tableau differente à 4 ou 2");
        }
    }

    @Override
    public void setRight(Byte bytes[], int ecart) throws VTPlayerException {
        if (bytes.length == 2) {
            setRight(bytes[0], bytes[1], ecart);
        } else if (bytes.length == 4) {
            setRight(bytes[0], bytes[1], bytes[2], bytes[3], ecart);
        } else {
            throw new VTPlayerException("taille du tableau differente à 4 ou 2");
        }
    }

    @Override
    public void setRight(byte b1_1, byte b2_1, byte b1_2, byte b2_2) throws VTPlayerException {
        setRight(b1_1, b2_1, b1_2, b2_2, DEFAULT_ECART);
    }

    @Override
    public void setRight(byte b1_1, byte b2_1, byte b1_2, byte b2_2, int ecart) throws VTPlayerException {
        set(bytes[0], bytes[1], b1_1, b2_1,
                bytes[4], bytes[5], b1_2, b2_2, ecart);
    }

    @Override
    public void setRight(byte b1, byte b2) throws VTPlayerException {
        set(bytes[0], bytes[1], b1, b2);
    }

    @Override
    public void setRight(byte b1, byte b2, int ecart) throws VTPlayerException {
        set(bytes[0], bytes[1], b1, b2,
                bytes[4], bytes[5], bytes[6], bytes[7], ecart);
    }

    protected void copy_bytes(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        bytes[0] = b1;
        bytes[1] = b2;
        bytes[2] = b3;
        bytes[3] = b4;
        bytes[4] = b5;
        bytes[5] = b6;
        bytes[6] = b7;
        bytes[7] = b8;
    }

    protected void copy_bytes(byte b1, byte b2, byte b3, byte b4) {
        bytes[0] = b1;
        bytes[1] = b2;
        bytes[2] = b3;
        bytes[3] = b4;

        bytes[4] = b1;
        bytes[5] = b2;
        bytes[6] = b3;
        bytes[7] = b4;
    }

    /**
     * Charges la librairie depuis le fichier JAR. <br/> Le fichier contenu dans
     * le JAR et copier dans un repertoire temporaire avant d'etre charger. Le
     * repertoire est supprimer apres le chargement de la librairie.
     *
     * @param filename Le nom du fichier a l'interieur du JAR. Doit etre un
     * chemin absolue (commence par '/'), ex. /package/File.ext
     * @throws IOException si la creation du repertoire temporaire echoue ou si
     * les operations de lecture/ecriture echoue.
     * @throws IllegalArgumentException si le fichier source (filename argument)
     * n'existe pas
     * @throws IllegalArgumentException si le chemin n'est pas absolue ou si le
     * nom de fichier et inferieur a trois caractere (restriction du a {
     * @see File#createTempFile(java.lang.String, java.lang.String)})
     */
    protected static void loadLibraryFromJar(String path) throws IOException {

        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Le chemin doit etre absolue (commance par '/').");
        }

        // Recupere le nom du fichier
        String[] parts = path.split("/");
        String filename = (parts.length > 1) ? parts[parts.length - 1] : null;

        // Separation du nom du fichier et de son extension
        String prefix = "";
        String suffix = null;
        if (filename != null) {
            parts = filename.split("\\.", 2);
            prefix = parts[0];
            suffix = (parts.length > 1) ? "." + parts[parts.length - 1] : null; // Thanks, davs! :-)
        }

        // Verifie si le nom de fichier est ok
        if (filename == null || prefix.length() < 3) {
            throw new IllegalArgumentException("le nom de fichier doit contenir au moins trois caractere.");
        }

        // Creation du repertoire temporaire
        File temp = File.createTempFile(prefix, suffix);
        temp.deleteOnExit();

        if (!temp.exists()) {
            throw new FileNotFoundException("Le fichier " + temp.getAbsolutePath() + " n'existe pas.");
        }

        // Preparation du buffer pour la copie
        byte[] buffer = new byte[1024];
        int readBytes;

        // Ouverure et verificaion du flux d'entre
        InputStream is = VTPlayerImpl.class.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("Le fichier " + path + " n'a pas pu etre trouvez dans le JAR.");
        }

        // Ouverture du flus de sortie et copie des donnee
        OutputStream os = new FileOutputStream(temp);
        try {
            while ((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
        } finally {
            // SI la lecture/ecriture echou, on ferme les flus avant de lancer une exception
            os.close();
            is.close();
        }

        // Finallement, chargement de la librairie
        System.load(temp.getAbsolutePath());
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
