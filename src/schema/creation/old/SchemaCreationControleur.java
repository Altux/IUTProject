package schema.creation.old;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import schema.Picture;
import schema.SchemaControleurInterface;
import vocal.LecteurTexteThread;
import vtplayer.VTPlayerInterface;

/**
 *
 */
public class SchemaCreationControleur extends SchemaControleurInterface implements ActionListener {

    CreationVue creationVue;

    public SchemaCreationControleur(VTPlayerInterface vtp, HashMap<Integer, Byte[]> bytes, CreationVue cv, LecteurTexteThread lecteurTexte, HashMap<Integer, String> sentence) {
        super(vtp, bytes, lecteurTexte, sentence);
        creationVue = cv;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();

        if (o instanceof Picture) {
            Integer i = creationVue.clickedOn((Picture) o);
            if (i != null) {
                vtpSet(i);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        Object o = e.getSource();

        if (o instanceof PictureCreation) {
            creationVue.enteredOn((PictureCreation) o);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Integer i = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                creationVue.escape();
                break;

            case KeyEvent.VK_DELETE:
                i = creationVue.delete();
                break;

            case KeyEvent.VK_R:
                i = creationVue.rotation();
                break;
        }
        if (i != null) {
            vtpSet(i);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer i = null;
        switch (e.getActionCommand()) {
            case CreationVue.ACTION_DELETE:
                i = creationVue.delete();
                break;

            case CreationVue.ACTION_NEW:


            case CreationVue.ACTION_OPEN:

            case CreationVue.ACTION_ROTATE:
                i = creationVue.rotation();
                break;

            case CreationVue.ACTION_SAVE:
                try {
                    creationVue.save();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SchemaCreationControleur.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SchemaCreationControleur.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        if (i != null) {
            vtpSet(i);
        }
        //creationVue.requestFocus();
    }
}
