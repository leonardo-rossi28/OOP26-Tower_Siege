package it.unibo.TowerSiege.view.pausemenu;

import it.unibo.TowerSiege.controller.gamecontroller.api.GameController;
import it.unibo.TowerSiege.controller.maincontroller.api.MainController;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class PauseMenuDialog {
    
    private final JDialog dialog;

    public PauseMenuDialog(JFrame parentFrame, final MainController mc, final GameController gc){
        dialog = new JDialog(parentFrame, "Pausa", true);
        dialog.setUndecorated(true);
        dialog.setSize(300, 350);
        dialog.setLocationRelativeTo(parentFrame);


        final JPanel p = new JPanel();
        p.setBackground(new Color(20, 20, 20, 240));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        
        final JLabel title = new JLabel("PAUSA");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(new Color(255, 255, 0));
        title.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        final JButton btnResume = createBtn("RIPRENDI", new Color(40, 160, 60));
        btnResume.addActionListener(e -> gc.togglePause());

        final JButton btnRestart = createBtn("TORNA AL MENU", new Color(180, 40, 40));
        btnMenu.addActionListener(e -> mc.backToMenu());

        p.add(Box.createRigidArea(new Dimension(0, 30)));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 40)));
        p.add(btnResume);
        p.add(Box.createRigidArea(new Dimension(0, 15)));
        p.add(btnResume);
        p.add(Box.createRigidArea(new Dimmension(0, 15)));
        p.add(btnMenu);

        dialog.add(p);
    }

    public void show(){
        dialog.setVisible(true);
    }

    public void hide(){
        dialog.dispose();
    }
}
