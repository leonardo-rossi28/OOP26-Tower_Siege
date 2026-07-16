package it.unibo.TowerSiege.view.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageLoader {
    
    public static Image spTowerBasic;
    public static Image spTowerSniper;
    public static Image spTowerRapid;
    public static Image spTowerIce;
    public static Image spEnemyBasic;
    public static Image spEnemyFast;
    public static Image spEnemyTank;
    public static Image imgTree;
    public static Image imgBush;
    public static Image imgRock;
    public static Image imgRockBush;
    
    private static boolean loaded = false;

    private ImageLoader(){
    }

    public static void loadAll() {
        if (loaded) return;
        try {
            final ClassLoader c1 = ImageLoader.class.getClassLoader();
            final String b = "Images/Images pack/Assets/";

            spTowerBasic    = loadImg(c1, b + "Structures/Towers/magic_crystal_tower.png");
            spTowerSniper   = loadImg(c1,b + "Characters/Heroes/knight_hero.png");
            spTowerRapid    = loadImg(c1, b + "Characters/Heroes/mage_hero.png");
            spTowerIce      = tintImg(spTowerBasic, new Color(100, 180, 255, 120));

            spEnemyBasic    = loadImg(c1, b + "Enemies/Orcs/orc_brute.png");
            spEnemyFast     = loadImg(c1, b + "Enemies/Orcs/orc_raider.png");
            spEnemyTank     = loadImg(c1, b + "Enemies/Orcs/orc_brute.png");

            imgTree = loadImg(c1, b + "Props/Nature/pine_tree.png");
            imgBush = loadImg(c1, b + "Props/Nature/bush_round.png");
            imgRock = loadImg(c1, b + "Props/Nature/rock_bush_cluster.png");
            imgRockBush = loadImg(c1, b + "Props/Nature/rock_bush_cluster.png");
            loaded = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Image loadImg(final ClassLoader c1, final String path) {
        try {
            final java.io.InputStream is = c1.getResourceAsStream(path);
            if (is != null) {
                return javax.imageio.ImageIO.read(is);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Image tintImg(final Image src, final Color tint) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        if (w <= 0 || h <= 0) {
            w = 128;
            h = 128;
        }
        final BufferedImage t = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = t.createGraphics();
        g.drawImage(src, 0, 0, w, h, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
        g.setColor(tint);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return t;
    }
}
