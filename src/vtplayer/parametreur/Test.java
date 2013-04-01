package vtplayer.parametreur;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vtplayer.VTPlayerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vtplayer.VTPlayerInterface;
import vtplayer.VTPlayerManager;

/**
 *
 * @author godeau
 */
public class Test {

    public static void main(String args[]) throws FileNotFoundException, IOException, VTPlayerException {
        //VTPlayerInterface vtp = VTPlayerManager.getInstance();
        //VTPlayerManager.open(vtp);

        JFrame frame = new JFrame();
        JButton jb = new JButton("test");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
            }
        });
        frame.add(jb);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        /**********************************************************************/

        ParametreurModele modele = new ParametreurModele();
        ParametreurVue vue = new ParametreurVue(modele);
        ParametreurControleur controleur = new ParametreurControleur(modele, vue, null/*vtp*/);

        vue.addMouseListener(controleur);
        vue.addActionListener(controleur);

        vue.setPreferredSize(new Dimension(700, 500));
        
        JButton apply = new  JButton("Appliquer");
        apply.setActionCommand(ParametreurVue.ACTION_COMMAND_SAVE);
        apply.addActionListener(controleur);

        Object[] selValues = {"Sauvegarder", "Annuler", apply};

        int ret = JOptionPane.showOptionDialog(frame, vue, "Paramétrer la configuration de la souris VTPlayer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, selValues, selValues[1]);
        if(ret == JOptionPane.YES_OPTION){
            modele.save();
        }
    }
}
