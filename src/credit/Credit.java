package credit;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Credit extends JPanel {

    Image iutLogo = new ImageIcon("ico/logoupsud.png").getImage();

    public Credit() {

        JLabel theLabel = new JLabel(readFile()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 550);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(500, 550);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(500, 550);
            }
        };

        setAlignmentX(TOP_ALIGNMENT);
        setAlignmentY(LEFT_ALIGNMENT);
        
        theLabel.setVerticalAlignment(SwingConstants.TOP);
        theLabel.setHorizontalAlignment(SwingConstants.LEFT);

        add(theLabel);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(iutLogo, 350, 10, null);
    }

    static String readFile() {
        String text = "";
        try {
            InputStream flux = new FileInputStream("donnees/credit.html");
            InputStreamReader lecture = new InputStreamReader(flux);
            try (BufferedReader buff = new BufferedReader(lecture)) {
                String ligne;
                while ((ligne = buff.readLine()) != null) {
                    text += ligne + "\n";
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return text;
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, new Credit(), "A propos", JOptionPane.PLAIN_MESSAGE);
    }
}
