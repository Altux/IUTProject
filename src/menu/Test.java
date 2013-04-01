package menu;

import java.io.IOException;
import javax.swing.JFrame;


public class Test {

    static public void main(String arg[]) throws IOException, InterruptedException {
        JFrame w = new JFrame("Menu");
        Menu menu = new Menu();

        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(1024, 720);
        w.setContentPane(menu);
        w.setVisible(true);
    }
}
