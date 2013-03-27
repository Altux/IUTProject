package schemaV2.porte;

import java.awt.Image;

/**
 *
 * @author godeau
 */
abstract public class Porte_2E_1S extends Porte {
    protected static final int MAX_PREVIOUS = 2;
    protected Porte next = null;
    protected Porte previous[] = new Porte_2E_1S[MAX_PREVIOUS];
    protected int cptPrev = 0;

    protected Porte_2E_1S(String nom, int code, int maxRotation) {
        super(nom, code, maxRotation);
    }

    public Porte getNext() {
        return next;
    }

    @Override
    public Porte[] getPrevious() {
        return previous;
    }

    @Override
    public void addNext(Porte p) {
        next = p;
    }

    @Override
    public void addPrevious(Porte p, int index) throws IndexOutOfBoundsException {
        if(index >= MAX_PREVIOUS){
            throw new IndexOutOfBoundsException();
        }
        
        previous[index] = p;
    }
    
    
}
