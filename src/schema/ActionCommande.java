package schema;

/**
 *
 * @author godeau
 */
public interface ActionCommande {

    /**
     * 
     */
    public final static String AC_OPEN = "#OPEN";
    /**
     * 
     */
    public final static String AC_EXIT = "#EXIT";
    /**
     * 
     */
    public final static String AC_PREFERENCE = "#PREFERENCE";
    /**
     * Action dans le cas d'une demande de nouveau schéma.
     */
    public final static String AC_NEW = "#NEW";
    /**
     * Action dans le cas d'une demande de suppression.
     */
    public final static String AC_DELETE = "#DELETE";
    /**
     * Action dans le cas d'une demande de sauvegarde.
     */
    public final static String AC_SAVE = "#SAVE";
    /**
     * 
     */
    public final static String AC_SAVEAS = "#SAVEAS";
    /**
     * Action dans le cas d'une demande de rotation.
     */
    public final static String AC_ROTATE = "#ROTATE";
    /**
     * 
     */
    public final static String AC_ABOUT = "#ABOUT";
    public final static String AC_ESCAPE = "#ESCAPE";
    public final static String AC_CHANGEBIT = "#CHANGEBIT";
}
