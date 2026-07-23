package it.unibo.towersiege.view.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
* Utility class to load and hold images/sprites
 */
public final class ImageLoader {
    
    private static final Logger LOGGER = Logger.getLogger(ImageLoader.class.getName());
    private static final int TINT_RED = 100;
    private static final int TINT_GREEN = 200;
    private static final int TINT_BLUE = 255;
    private static final int TINT_ALPHA_VALUE = 255;
    private static final int PIXEL_MASK_ALPHA = 0xFF000000;

    private static boolean loaded;

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

    private ImageLoader() {
    }

    /**
     * Returns the basic tower sprite.
     * 
     * @return the basic tower image
     */
    public static Image getSpTowerBasic() {
         return spTowerBasic; 
    }

    /**
     * Returns the sniper tower sprite.
     * 
     * @return the sniper tower image
     */
    public static Image getSpTowerSniper() { 
        return spTowerSniper; 
    }

    /**
     * Returns the rapid tower sprite.
     * 
     * @return the basic tower image
     */
    public static Image getSpTowerRapid() {
         return spTowerRapid; 
    }

    /**
     * Returns the ice tower sprite.
     * 
     * @return the ice tower image
     */
    public static Image getSpTowerIce() { 
        return spTowerIce; 
    }

    /**
     * Returns the basic enemy sprite.
     * 
     * @return the basic enemy image
     */
    public static Image getSpEnemyBasic() { 
        return spEnemyBasic; 
    }

    /**
     * Returns the fast enemy sprite.
     * 
     * @return the fast enemy image
     */
    public static Image getSpEnemyFast() {
         return spEnemyFast; 
    }

    /**
     * Returns the tank enemy sprite.
     * 
     * @return the tank enemy image
     */
    public static Image getSpEnemyTank() { 
        return spEnemyTank; 
    }

    /**
     * Returns the tree decoration image.
     * 
     * @return the tree image
     */
    public static Image getImgTree() {
         return imgTree; 
    }

    /**
     * Returns the bush decoration image.
     * 
     * @return the bush image
     */
    public static Image getImgBush() { 
        return imgBush; 
    }

    /**
     * Returns the rock decoration image.
     * 
     * @return the rock image
     */
    public static Image getImgRock() {
         return imgRock;
    }

    /**
     * Returns the rock-bush decoration image.
     * 
     * @return the rock-bush image
     */
    public static Image getImgRockBush() {
         return imgRockBush; 
    }

    /**
     * Loads all game images once.
     */
    public static void loadAll() {
        if (loaded) {
            return;
        }
        try {
            final ClassLoader cl = ImageLoader.class.getClassLoader();
            final String basePath = "images/Images pack/Assets/";

            spTowerBasic = loadImg(cl, basePath + "Structures/Towers/magic_crystal_tower.png");
            spTowerSniper = loadImg(cl, basePath + "Characters/Heroes/knight_hero.png");
            spTowerRapid = loadImg(cl, basePath + "Characters/Heroes/mage_hero.png");
            spTowerIce = tintImg(loadImg(cl, basePath + "Structures/Towers/magic_crystal_tower.png"),
                    new Color(TINT_RED, TINT_GREEN, TINT_BLUE, TINT_ALPHA_VALUE));

            spEnemyBasic = loadImg(cl, basePath + "Enemies/Orcs/orc_brute.png");
            spEnemyFast = loadImg(cl, basePath + "Enemies/Orcs/orc_raider.png");
            spEnemyTank = loadImg(cl, basePath + "Enemies/Orcs/orc_brute.png");

            imgTree = loadImg(cl, basePath + "Props/Nature/pine_tree.png");
            imgBush = loadImg(cl, basePath + "Props/Nature/bush_round.png");
            imgRock = loadImg(cl, basePath + "Props/Nature/rock_cluster.png");
            imgRockBush = loadImg(cl, basePath + "Props/Nature/rock_bush_cluster.png");
            loaded = true;

        } catch (final IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Failed to load game images", e);
        }
    }

    /**
     * Loads an image from the classpath.
     * 
     * @param cl class loader
     * @param path resource path
     * @return the loaded image
     */
    public static Image loadImg(final ClassLoader cl, final String path) {
        final URL url = cl.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Image not found: " + path);
        }
        try {
            return ImageIO.read(url);
        } catch (final IOException ex) {
            throw new IllegalArgumentException("Cannot read image: " + path, ex);
        }
    }
    
    /**
     * Tints an image with a specified color.
     * 
     * @param src source image
     * @param tint tint color 
     * @return tinted image
     */
    public static Image tintImg(final Image src, final Color tint) {
        final int w = src.getWidth(null);
        final int h = src.getHeight(null);
        final BufferedImage bi = new BufferedImage(
               w > 0 ? w : 16, h > 0 ? h : 16, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = bi.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();

        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                final int rgb = bi.getRGB(x, y);
                final int a = rgb & PIXEL_MASK_ALPHA;
                if (a == 0) {
                    continue;
                }
                final int r = (rgb >> 16) & 0xFF;
                final int gComponent = (rgb >> 8) & 0xFF;
                final int b = rgb & 0xFF;

                final int tr = r * tint.getRed() / 255;
                final int tg = gComponent * tint.getGreen() / 255;
                final int tb = b * tint.getBlue() / 255;

                bi.setRGB(x, y, a | (tr << 16) | (tg << 8) | tb);
            }
        }

        return bi;
    }
}

