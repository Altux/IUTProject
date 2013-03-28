package vtplayer;

import java.awt.Point;

/**
 * Conversion de Byte.
 *
 * @author godeau
 */
public class ByteUtilitaire {

    private ByteUtilitaire() {
    }

    /**
     * Convertie une chaîne de caractère en byte.
     *
     * @param str chaîne écrire en hexadécimal a convertir en byte.
     * @return l'équivalent en byte de la chaîne hexadécimal.
     * @throws VTPlayerException Si la chaîne est trop long pour être un
     * argument valide de <i>set</i>. Si un des caractère n'est pas une valeur
     * hexadécimal correct.
     */
    public static byte string_hexa_to_byte(String str) throws VTPlayerException {
        int length = str.length();
        if (length > 2) {
            throw new VTPlayerException("The string \"" + str + "\" is not valid for conversion to byte.");
        }
        byte ret = 0;

        try {
            for (int cpt = 0; cpt < length; cpt++) {
                ret *= 16;
                ret += Byte.parseByte(Character.toString(str.charAt(cpt)), 16);
            }
        } catch (NumberFormatException ex) {
            throw new VTPlayerException("Error on convertion of \"" + str + "\n to byte.");
        }
        return ret;
    }

    /**
     * Converti un bit code en hexadécimal en une chaîne de caractère
     * représentant le code binaire de ce même nombre.
     *
     * @param b byte a convertir
     * @return code binaire du byte passer en paramètre (coder sur 8 bits)
     */
    public static String byte_hexa_to_bin_string(Byte b) {
        Integer integer = Integer.parseInt(String.format("%x", b), 16);
        String str = new String();

        while (integer > 1) {
            str = (integer % 2) + str;
            integer = (integer / 2);
        }

        if (integer == 1) {
            str = 1 + str;
        }

        while (str.length() != 8) {
            str = "0" + str;
        }

        return str;
    }

    /**
     * Converti une position x, y en une configuration de deux byte. Les
     * position ne doivent pas dépasser 16.
     *
     * @param tab tableau de deux entier
     * @return
     */
    public static Byte[] position_to_byte(int tab[]) {
        return position_to_byte(tab[0], tab[1]);
    }
    
    public static Byte[] position_to_byte(Point p){
        return position_to_byte(p.x, p.y);
    }

    public static Byte[] position_to_byte(int lig, int col) {
        Byte ret[] = {(byte) 0xff, (byte) 0xff};
        char tab[] = {'1', '1', '1', '1', '1', '1', '1', '1'};
        int col2 = (col > 1) ? col - 2 : col;

        switch (lig) {
            case 0:
                tab[col2] = '0';
                break;

            case 1:
                tab[col2 + 2] = '0';
                break;

            case 2:
                tab[col2 + 6] = '0';
                break;

            case 3:
                tab[col2 + 4] = '0';
                break;
        }

        if (col < 2) {
            ret[0] = ((Integer) Integer.parseInt(new String(tab), 2)).byteValue();
        } else {
            ret[1] = ((Integer) Integer.parseInt(new String(tab), 2)).byteValue();
        }


        return ret;
    }
}
