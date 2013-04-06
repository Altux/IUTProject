package parametres;

import java.util.Observable;

/**
 *
 * @author
 */
abstract public class Config extends Observable {

    /**
     * Permet de savoir si l'utilisateur souhaite avoir le son.
     *
     * @return vrai si il veut le son, faux sinon
     */
    abstract public boolean getSon();

    /**
     * Permet de savoir si l'utilisateur souhaite activer la souris.
     *
     * @return vrai si il veut le son, faux sinon
     */
    abstract public boolean getVTPlayerMouse();

    /**
     * Permet d'obtenir la fréquence entre deux configuration de picots.
     *
     * @return temps exprimer en milliseconde
     */
    abstract public int getFrequencePicots();

    /**
     * Choix de la norme US | EU. Nécessite le redémarrage de l'application pour
     * être pris en compte.
     *
     * @return la norme choisie par l'utilisateur
     */
    abstract public String getNorme();

    /**
     * Touche permettent d'appliquer une rotation a l'élément lorsque que
     * l'utilisateur est en mode création.
     *
     * @return la touche choisie par l'utilisateur
     */
    abstract public int getKeyRotation();

    /**
     * 
     * @return 
     */
    abstract public int getKeySuppresion();
    
    /**
     * 
     * @return 
     */
    abstract public int getKeyEchapement();
    
    /**
     * 
     * @return 
     */
    abstract public int getKeyChangeBit();

    abstract public int getKeySpatialization();
    
    /**
     * Nécessite le redémarrage de l'application pour être pris en compte.
     *
     * @return
     */
    abstract public boolean getAutoResize();
}
