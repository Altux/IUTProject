package schemaV2.porte;

/**
 *
 * @author godeau
 */
public class Nand extends Porte_2E_1S {

    public Nand() {
        super("nand.jpg", 140, 4);
    }
    
    @Override
    public boolean getResult() {
        return true;
    }
    
    
}
