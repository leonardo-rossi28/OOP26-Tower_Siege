package it.unibo.TowerSiege.view.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/*
* Utility class designed for loading and managing the game image assets
 */
public final class ImageLoader {
    
    private static Image spTowerBasic;
    private static Image spTowerSniper;
    private static Image spTowerRapid;
    private static Image spTowerIce;
    private static Image spEnemyBasic;
    private static Image spEnemyFast;
    private static Image spEnemyTank;
    private static Image imgTree;
    private static Image imgBush;
    private static Image imgRock;
    private static Image imgRockBush;
    
    private static boolean loaded = false;

    private ImageLoader(){
    }
    /*
    * Loads all the game images 
     */
    public static void loadAll() {
        if (loaded) {
            return;
        }
        try {
            final ClassLoader c1 = ImageLoader.class.getClassLoader();
            final String b = "Images/Images pack/Assets/";

            spTowerBasic = loadImg(c1, b + "Structures/Towers/magic_crystal_tower.png");
            spTowerSniper = loadImg(c1,b + "Characters/Heroes/knight_hero.png");
            spTowerRapid = loadImg(c1, b + "Characters/Heroes/mage_hero.png");
            spTowerIce = tintImg(spTowerBasic, new Color(100, 180, 255, 120));

            spEnemyBasic = loadImg(c1, b + "Enemies/Orcs/orc_brute.png");
            spEnemyFast = loadImg(c1, b + "Enemies/Orcs/orc_raider.png");
            spEnemyTank = loadImg(c1, b + "Enemies/Orcs/orc_brute.png");

            imgTree = loadImg(c1, b + "Props/Nature/pine_tree.png");
            imgBush = loadImg(c1, b + "Props/Nature/bush_round.png");
            imgRock = loadImg(c1, b + "Props/Nature/rock_bush_cluster.png");
            imgRockBush = loadImg(c1, b + "Props/Nature/rock_bush_cluster.png");
            loaded = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param c1 classloader
     * @param path file path
     * @return the loaded image
     */
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
    /**
     * Applies a tint to an image
     * @param src source image
     * @param tint tint color 
     * @return tinted image
     */
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

    /** @return basic tower image */
    public static Image getSpTowerBasic() { return spTowerBasic; }
    /** @return sniper tower image */
    public static Image getSpTowerSniper() { return spTowerSniper; }
    /** @return rapid tower image */
    public static Image getSpTowerRapid() { return spTowerRapid; }
    /** @return ice tower image */
    public static Image getSpTowerIce() { return spTowerIce; }
    /** @return basic enemy image */
    public static Image getSpEnemyBasic() { return spEnemyBasic; }
    /** @return fast enemy image */
    public static Image getSpEnemyFast() { return spEnemyFast; }
    /** @return tank enemy image */
    public static Image getSpEnemyTank() { return spEnemyTank; }
    /** @return tree image */
    public static Image getImgTree() { return imgTree; }
    /** @return bush image */
    public static Image getImgBush() { return imgBush; }
    /** @return rock image */
    public static Image getImgRock() { return imgRock;}
    /** @return rock bush image */
    public static Image getImgRockBush() { return imgRockBush; }
    
}
