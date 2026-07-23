package it.unibo.towersiege.view.startmenu;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.view.rules.RulesDialog;

/** 
 * Main panel shown at the start of the game
 */

public final class StartMenuPanel extends JPanel {     

    private static final long serialVersionUID = 1L;

    private static final int LOGO_TOP_RATIO = 55;
    private static final int SUBTITLE_SIZE = 18;
    private static final int INFO_SIZE = 13;
    private static final int BUTTON_FONT_SIZE = 18;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 250;
    private static final int BORDER_V = 30;
    private static final int BORDER_H = 50;
    private static final int SPACING_SM = 4;
    private static final int SPACING_MD = 10;
    private static final int SPACING_LG = 12;
    private static final int SPACING_XL = 22;
    private static final int GRADIENT_R1 = 25;
    private static final int GRADIENT_G1 = 18;
    private static final int GRADIENT_B1 = 12;
    private static final int GRADIENT_R2 = 50;
    private static final int GRADIENT_G2 = 35;
    private static final int GRADIENT_B2 = 20;
    private static final int SUB_COLOR_R = 220;
    private static final int SUB_COLOR_G = 200;
    private static final int SUB_COLOR_B = 160;
    private static final int INFO_COLOR_V = 180;
    private static final int BTN_START_G = 150;
    private static final int BTN_START_B = 80;
    private static final int BTN_RULES_R = 50;
    private static final int BTN_RULES_G = 90;
    private static final int BTN_RULES_B = 170;
    private static final int BTN_EXIT_R = 170;
    private static final int BTN_EXIT_G = 45;
    private static final int BTN_EXIT_B = 45;
    private static final String FONT_SERIF = "Serif";
    private static final int OVERLAY_ALPHA = 80;

    private Image backgroundImage;

    /**
     * Creates the start menu panel.
     * 
     * @param controller main controller
     * @param parentFrame parent frame
     */
    public StartMenuPanel(final MainController controller, final JFrame parentFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(BORDER_V, BORDER_H, BORDER_V, BORDER_H));

        //Load background image from classpath
        try {
            final InputStream is = getClass().getClassLoader()
                .getResourceAsStream("images/sfondo iniziale.png");
            if (is != null) {
                backgroundImage = ImageIO.read(is);
                is.close();
            }
        } catch (final IOException e) {
                backgroundImage = null;
        }

        final JLabel sub = mkLabel("Difendi la base dalle ondate nemiche!",
                new Font(FONT_SERIF, Font.ITALIC, SUBTITLE_SIZE),
                new Color(SUB_COLOR_R, SUB_COLOR_G, SUB_COLOR_B));
        final JLabel i1 = mkLabel("Click sx = piazza/potenzia | Click dx = vendi torre",
                new Font(FONT_SERIF, Font.PLAIN, INFO_SIZE),
                new Color(INFO_COLOR_V, INFO_COLOR_V, INFO_COLOR_V));
        final JLabel i2 = mkLabel("W = ondata | F = Fuoco | G = Gelo | ESC = Pausa",
                new Font(FONT_SERIF, Font.PLAIN, INFO_SIZE),
                new Color(INFO_COLOR_V, INFO_COLOR_V, INFO_COLOR_V));

        final JButton start = btn("INIZIA PARTITA",
                new Color(BORDER_V, BTN_START_G, BTN_START_B));
        start.addActionListener(e -> controller.beginGame());

        final JButton rules = btn("REGOLAMENTO",
                new Color(BTN_RULES_R, BTN_RULES_G, BTN_RULES_B));
        rules.addActionListener(e -> new RulesDialog(parentFrame).show());

        final JButton exit = btn("ESCI",
                new Color(BTN_EXIT_R, BTN_EXIT_G, BTN_EXIT_B));
        exit.addActionListener(e -> exitGame());

        init(sub, i1, i2, start, rules, exit);
    }

    private void exitGame() {
        System.exit(0);
    }

    private void init(final JLabel sub,
            final JLabel i1, final JLabel i2,
            final JButton start, final JButton rules, final JButton exit) {
        add(Box.createHorizontalGlue());
        add(Box.createRigidArea(new Dimension(0, LOGO_TOP_RATIO)));
        add(Box.createRigidArea(new Dimension(0, SPACING_MD)));
        add(sub);
        add(Box.createRigidArea(new Dimension(0, SPACING_LG)));
        add(i1);
        add(Box.createRigidArea(new Dimension(0, SPACING_SM)));
        add(i2);
        add(Box.createRigidArea(new Dimension(0, SPACING_XL)));
        add(start);
        add(Box.createRigidArea(new Dimension(0, SPACING_MD)));
        add(rules);
        add(Box.createRigidArea(new Dimension(0, SPACING_MD)));
        add(exit);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        if (backgroundImage != null) {
            //Draw background image scaled to fill the panel
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            //Semi trasparent overlay 
            g2.setColor(new Color(0, 0, 0, OVERLAY_ALPHA));
            g2.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g2.setPaint(new GradientPaint(0, 0,
                new Color(GRADIENT_R1, GRADIENT_G1, GRADIENT_B1),
                0, getHeight(),
                new Color(GRADIENT_R2, GRADIENT_G2, GRADIENT_B2)));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private JLabel mkLabel(final String text, final Font f, final Color c) {
        final JLabel l = new JLabel(text);
        l.setFont(f);
        l.setForeground(c);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    private JButton btn(final String text, final Color bg) {
        final JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, BUTTON_FONT_SIZE));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setAlignmentX(CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return b;
    }
}
