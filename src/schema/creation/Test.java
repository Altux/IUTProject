//package schema.creation;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import javax.swing.JFrame;
//import schema.GestionaireFichier;
//
///**
// *
// */
//public class Test {
//    public static void main(String args[]) throws InterruptedException, FileNotFoundException, IOException {
//        
//        GestionaireFichier gf = new GestionaireFichier(GestionaireFichier.REP_IMAGES);
//        CreationVue schema = new CreationVue(gf/*, GestionaireFichier.loadFileShemat("./save/test.save")*/);
//        SchemaCreationControleur controleur = new SchemaCreationControleur(null, null, schema, null, null, null);
//
//        JFrame frame = new JFrame("Test");
//        frame.setSize(800, 600);
//        frame.addKeyListener(controleur);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(schema);
//        frame.setVisible(true);
//    }
//}
