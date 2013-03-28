package menu;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import schema.ActionCommande;

/**
 *
 * @author Icinhoo
 */
public class Menu extends JPanel implements ActionCommande {

    private final static String FONT = "font/Basically_Serif.ttf";
    JButton ouvrir = new JButton("Ouvrir", new ImageIcon("ico/ouvrir.png"));
    JButton creer = new JButton("Créer", new ImageIcon("ico/creer.png"));
    JButton parametre = new JButton("Paramètres", new ImageIcon("ico/parametre.png"));
    JButton quitter = new JButton("Quitter", new ImageIcon("ico/quitter.png"));
    JButton credit = new JButton("Crédits", new ImageIcon("ico/vide.png"));
    Image background = new ImageIcon("ico/background.png").getImage();

    public Menu() {

        Font newButtonFont = new Font(FONT, ouvrir.getFont().getStyle(), 40);

        ouvrir.setFont(newButtonFont);
        ouvrir.setBorderPainted(false);
        ouvrir.setFocusPainted(false);
        ouvrir.setContentAreaFilled(false);
        ouvrir.setForeground(Color.decode("#04FF46"));
        ouvrir.setActionCommand(AC_OPEN);

        creer.setFont(newButtonFont);
        creer.setBorderPainted(false);
        creer.setFocusPainted(false);
        creer.setContentAreaFilled(false);
        creer.setForeground(Color.decode("#04FF46"));
        creer.setActionCommand(AC_NEW);

        parametre.setFont(newButtonFont);
        parametre.setBorderPainted(false);
        parametre.setFocusPainted(false);
        parametre.setContentAreaFilled(false);
        parametre.setForeground(Color.decode("#04FF46"));
        parametre.setActionCommand(AC_PREFERENCE);


        quitter.setFont(newButtonFont);
        quitter.setBorderPainted(false);
        quitter.setFocusPainted(false);
        quitter.setContentAreaFilled(false);
        quitter.setForeground(Color.decode("#04FF46"));
        quitter.setActionCommand(AC_EXIT);


        credit.setFont(newButtonFont);
        credit.setBorderPainted(false);
        credit.setFocusPainted(false);
        credit.setContentAreaFilled(false);
        credit.setForeground(Color.decode("#04FF46"));
        credit.setActionCommand(AC_ABOUT);

        JPanel vide = new JPanel();
        vide.setPreferredSize(new Dimension(999999999, 250));
        vide.setLayout(new BoxLayout(vide, BoxLayout.PAGE_AXIS));
        add(vide);
        vide.setOpaque(false);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(ouvrir);
        panel.add(creer);
        panel.add(parametre);
        panel.add(quitter);
        panel.add(credit);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(panel);
        setOpaque(false);
    }

    public void mouseMoved(MouseEvent e) {
        ouvrir.setForeground(Color.white);
        creer.setForeground(Color.white);
        parametre.setForeground(Color.white);
        quitter.setForeground(Color.white);
        credit.setForeground(Color.white);

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        super.paint(g);
    }

    public synchronized void addActionListener(ActionListener l) {
        ouvrir.addActionListener(l);
        creer.addActionListener(l);
        parametre.addActionListener(l);
        quitter.addActionListener(l);
        credit.addActionListener(l);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        ouvrir.addMouseListener(l);
        creer.addMouseListener(l);
        parametre.addMouseListener(l);
        quitter.addMouseListener(l);
        credit.addMouseListener(l);
    }
}
