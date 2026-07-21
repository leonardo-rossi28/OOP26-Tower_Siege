package it.unibo.towersiege.view.startmenu;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towersiege.controller.maincontroller.api.MainController;
import it.unibo.towersiege.view.rules.RulesDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

/** 
 * Main panel shown at the start of the game
 */

public class StartMenuPanel extends JPanel {

    private static final Color C_GOLD = new Color(255, 215, 0);

    private static final int TITLE_SIZE=52;
    private static final int SUBTITLE_SIZE=18;
    private static final int INFO_SIZE= 13;
    private static final int BUTTON_FONT_SIZE= 18;
    private static final int BUTTON_HEIGHT=50;
    private static final int BUTTON_WIDTH=250;

    /**
     * Creates the start menu panel.
     * 
     * @param controller main controller
     * @param parentFrame parent frame
     */

    public StartMenuPanel(final MainController controller, final JFrame parentFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        final JLabel title = mkLabel("TOWERSIEGE", new Font("Serif", Font.BOLD, TITLE_SIZE), C_GOLD);
        final JLabel sub = mkLabel("Difendi la base dalle ondate nemiche!", new Font("Serif", Font.ITALIC, SUBTITLE_SIZE),
                new Color(220, 200, 160));
        final JLabel i1 = mkLabel("Click sx = piazza/potenzia | Click dx = vendi torre", new Font("Serif", Font.PLAIN,INFO_SIZE),
                new Color(180, 180, 180));
        final JLabel i2 = mkLabel("W = ondata | F = Fuoco | G = Gelo | ESC = Pausa", new Font("Serif", Font.PLAIN,INFO_SIZE),
                new Color(180, 180, 180));

        final JButton start = btn("INIZIA PARTITA", new Color(30, 150, 80));
        start.addActionListener(e -> controller.beginGame());

        final JButton rules = btn("REGOLAMENTO", new Color(50, 90, 170));
        rules.addActionListener(e -> new RulesDialog(parentFrame).show());

        final JButton exit = btn("ESCI", new Color(170, 45, 45));
        exit.addActionListener(e -> System.exit(0));

        add(title);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(sub);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(i1);
        add(Box.createRigidArea(new Dimension(0, 4)));
        add(i2);
        add(Box.createRigidArea(new Dimension(0, 22)));
        add(start);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(rules);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(exit);
    }

    /**
     * {@InheritDoc}
    */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new GradientPaint(0, 0, new Color(25, 18, 12), 0, getHeight(), new Color(50, 35, 20)));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private JLabel mkLabel(final String text, final Font f, final Color c) {
        final JLabel l = new JLabel(text);
        l.setFont(f);
        l.setForeground(c);
        l.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        return l;
    }

    private JButton btn(final String text, final Color bg) {
        final JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, BUTTON_FONT_SIZE));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return b;
    }
}
