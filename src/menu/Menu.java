package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import javax.swing.Box;

/**
 *
 * @author Icinhoo
 */
public class Menu extends JPanel implements MenuAction {

    private final static String FONT = "font/Basically_Serif.ttf";
    JButton ouvrir = new JButton("Ouvrir", new ImageIcon("ico/ouvrir.png"));
    JButton creer = new JButton("Créer", new ImageIcon("ico/creer.png"));
    JButton parametre = new JButton("Paramètres", new ImageIcon("ico/parametre.png"));
    JButton quitter = new JButton("Quitter", new ImageIcon("ico/quitter.png"));
    JButton credit = new JButton("Crédits", new ImageIcon("ico/vide.png"));
    Image background = new ImageIcon("ico/background.png").getImage();

    public Menu() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getSource()).requestFocus();
            }
        };

        FocusAdapter focusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JButton jb = (JButton) e.getSource();
                jb.setForeground(Color.WHITE);
                jb.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                JButton jb = (JButton) e.getSource();
                jb.setForeground(Color.decode("#04FF46"));
                jb.repaint();
            }
        };

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ((JButton) e.getSource()).doClick();
                }
            }
        };

        Font newButtonFont = new Font(FONT, ouvrir.getFont().getStyle(), 40);

        ouvrir.setFont(newButtonFont);
        ouvrir.setBorderPainted(false);
        ouvrir.setFocusPainted(false);
        ouvrir.setContentAreaFilled(false);
        ouvrir.setForeground(Color.decode("#04FF46"));
        ouvrir.setActionCommand(AC_OPEN);
        ouvrir.addMouseListener(mouseAdapter);
        ouvrir.addFocusListener(focusListener);
        ouvrir.addKeyListener(keyAdapter);

        creer.setFont(newButtonFont);
        creer.setBorderPainted(false);
        creer.setFocusPainted(false);
        creer.setContentAreaFilled(false);
        creer.setForeground(Color.decode("#04FF46"));
        creer.setActionCommand(AC_NEW);
        creer.addMouseListener(mouseAdapter);
        creer.addFocusListener(focusListener);
        creer.addKeyListener(keyAdapter);

        parametre.setFont(newButtonFont);
        parametre.setBorderPainted(false);
        parametre.setFocusPainted(false);
        parametre.setContentAreaFilled(false);
        parametre.setForeground(Color.decode("#04FF46"));
        parametre.setActionCommand(AC_PREFERENCE);
        parametre.addMouseListener(mouseAdapter);
        parametre.addFocusListener(focusListener);
        parametre.addKeyListener(keyAdapter);

        quitter.setFont(newButtonFont);
        quitter.setBorderPainted(false);
        quitter.setFocusPainted(false);
        quitter.setContentAreaFilled(false);
        quitter.setForeground(Color.decode("#04FF46"));
        quitter.setActionCommand(AC_EXIT);
        quitter.addMouseListener(mouseAdapter);
        quitter.addFocusListener(focusListener);
        quitter.addKeyListener(keyAdapter);

        credit.setFont(newButtonFont);
        credit.setBorderPainted(false);
        credit.setFocusPainted(false);
        credit.setContentAreaFilled(false);
        credit.setForeground(Color.decode("#04FF46"));
        credit.setActionCommand(AC_ABOUT);
        credit.addMouseListener(mouseAdapter);
        credit.addFocusListener(focusListener);
        credit.addKeyListener(keyAdapter);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(false);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setAlignmentX(CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(ouvrir);
        panel.add(creer);
        panel.add(parametre);
        panel.add(quitter);
        panel.add(credit);
        
        add(Box.createVerticalGlue());
        add(panel);
        add(Box.createVerticalStrut(10));

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
}
