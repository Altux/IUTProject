package menu;


import java.io.IOException;

import javax.swing.*;

public class Test {

    static public void main(String arg[]) throws IOException {
        JFrame w = new JFrame("Menu");
        w.setResizable(false);
        Menu menu = new Menu();
        MenuControleur controleur = new MenuControleur(menu);
        menu.addActionListener(controleur);
        menu.addMouseListener(controleur);

        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(1024, 720);
        w.add(menu);
        w.setVisible(true);
    }
}
