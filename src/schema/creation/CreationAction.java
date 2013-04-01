/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package schema.creation;

/**
 *
 * @author 
 */
public interface CreationAction {
    /**
     * Action dans le cas d'une demande de suppression.
     */
    public final static String AC_DELETE = "#DELETE";
    /**
     * Action dans le cas d'une demande de rotation.
     */
    public final static String AC_ROTATE = "#ROTATE";
    /**
     *
     */
    public final static String AC_ESCAPE = "#ESCAPE";
    /**
     *
     */
    public final static String AC_CHANGEBIT = "#CHANGEBIT";
    
}
