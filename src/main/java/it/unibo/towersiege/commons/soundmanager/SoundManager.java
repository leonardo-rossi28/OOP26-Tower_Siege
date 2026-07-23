package it.unibo.towersiege.commons.soundmanager;

import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Utility Class for managing and playing sound effects.
 */
public final class SoundManager {

    private static final Logger LOGGER = Logger.getLogger(SoundManager.class.getName());
    private static final float DEFAULT_VOLUME = 0.5f;
    private static float volume = DEFAULT_VOLUME;

    private SoundManager() {
    }

    /**
     * Plays a spund effect from the classpath.
     * 
     * @param resourcePath the path to the sound file in resources
     */
    public static void playSound(final String resourcePath) {
        try {
            final InputStream is = SoundManager.class.getClassLoader().getResourceAsStream(resourcePath);
            if (is == null) {
                return;
            }
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(is)) {
                final Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                final float min = gainControl.getMinimum();
                final float max = gainControl.getMaximum();
                final float range = max - min;
                final float gain = (range * volume) + min;
                gainControl.setValue(gain);

                clip.start();
            }
        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.fine("Could not play sound " + resourcePath + ": " + e.getMessage());
        }
    }

    /**
     * Plays the enemy killed sound effect.
     */
    public static void playEnemyKilled() {
        playSound("sounds/enemy_killed.wav");
    }

    /**
     * Plays the victory sound effect.
     */
    public static void playVictory() {
        playSound("sounds/victory.wav");
    }

    /**
     * Plays the defeat sound effect.
     */
    public static void playDefeat() {
        playSound("sounds/defeat.wav");
    }

    /**
     * Sets the master volume for sound effects-
     * 
     * @param vol volume level between 0.0 and 1.0
     */
    public static void setVolume(final float vol) {
        if (vol >= 0.0f && vol <= 1.0f) {
            volume = vol;
        }
    }

    /**
     * Returns the current master volume
     * 
     * @return volume level between 0.0 and 1.0
     */
    public static float getVolume() {
        return volume;
    }
}
