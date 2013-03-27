package schema;

/**
 *
 * @author godeau
 */
public interface ActionCommande {

    public final static String AC_OPEN = "#OPEN";
    public final static String AC_EXIT = "#EXIT";
    /**
     * Action dans le cas d'une demande de nouveau schéma.
     *
     * @see #newSchema()
     */
    public final static String ACTION_NEW = "#NEW";
    /**
     * Action dans le cas d'une demande de suppression.
     *
     * @see #delete()
     * @see KeyEvent#VK_DELETE
     */
    public final static String ACTION_DELETE = "#DELETE";
    /**
     * Action dans le cas d'une demande de sauvegarde.
     *
     * @see #saveAs()
     * @see #save()
     */
    public final static String ACTION_SAVE = "#SAVE";
    /**
     * Action dans le cas d'une demande de rotation.
     *
     * @see KeyEvent#VK_R
     * @see #rotation()
     */
    public final static String ACTION_ROTATE = "#ROTATE";
}
