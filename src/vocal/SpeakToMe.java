package vocal;

import java.awt.Point;

public class SpeakToMe extends t2s.son.LecteurTexte {
    public static final String PATH_PHONETIQUE = "config/phonetique.conf";
    public static final String PATH_CONFIG = "donnees/si_vox.conf";

    /**
     * Certaines lettres ne sont pas prononcées correctement, on remplace alors
     * dans le tableau les lettres en question par leur l'équivalent en
     * phonétique
     */
    protected static String[] tabAlphabet = {"A", "B", "C", "Dé", "E", "F", "G", "H", "I", "J", "K", "L", "aime", "haineu", "eau", "Pé", "Cul", "aire", "aisse", "Té", "U", "V", "W", "ixe", "igrec", "Z"};

//    public void playTexteLater(String str) {
//        if (str != null && !str.isEmpty()) {
//            setTexte(str);
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    playAll();
//                }
//            });
//        }
//    }
    public void positionnement(Point p) {
        positionnement(p.x, p.y);
    }

    public void positionnement(int ligne, int col) {

        String h = tabAlphabet[col];

        // De manière similaire ï¿½ certaines lettres, il éxiste des chiffres et 
        // des nombres qui ne sont pas prononcés correctement
        // Le switch réalisé lit la colonne puis la ligne contenant éventuellement 
        // les nombres dont la prononciation n'est pas facilement compréhensible

        switch (ligne) {
            case 8:
                setTexte(h + "  " + "wite");
                break;
            case 18:
                setTexte(h + "  " + "diz wite");
                break;
            case 19:
                setTexte(h + "  " + "diz neuf");
                break;
            case 21:
                setTexte(h + "  " + "vinté ain");
                break;
            case 22:
                setTexte(h + "  " + "vinte2");
                break;
            case 23:
                setTexte(h + "  " + "vinte3");
                break;
            case 24:
                setTexte(h + "  " + "vinte4");
                break;
            case 25:
                setTexte(h + "  " + "vinteu5");
                break;
            default:
                setTexte(h + "  " + ligne);
        }
    }

    public void lectureBit(int b) {
        /**
         * Procï¿½dure LectureBit Si le premier chiffre de l'identifiant de
         * l'image est ï¿½gal ï¿½ 1 on lit le dernier chiffre de cet
         * identifiant, ce dernier chiffre correspond au bit du fil En revanche,
         * si le premier chiffre ne correspond pas ï¿½ 1, on sort de la
         * procï¿½dure
         */
        if (b / 1000 == 1) {
            String val_bit = String.valueOf(b % 10);
            setTexte("biteuh" + val_bit);
        }

    }
}
