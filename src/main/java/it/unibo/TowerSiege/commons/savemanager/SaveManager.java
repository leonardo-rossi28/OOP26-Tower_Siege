package it.unibo.TowerSiege.commons.savemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Manages the persistence of core game progression data into a text-based file.
 * This class is responsible for tracking the highest level unlocked and the top score achieved.
 */


public final class SaveManager {
    private static final Logger LOGGER = Logger.getLogger(SaveManager.class.getName());
    private static final String DEFAULT_SAVE_PATH = "save.txt";

    private SaveManager(){

    }

    /**
     * Persists the current game progression data to the storage file.
     * * @param maxUnlockedLevel the furthest level the player has access to
     * @param bestScore        the highest record score obtained by the player
     */

    public static void save(final int maxUnlockedLevel, final int bestScore){
        save(maxUnlockedLevel, bestScore, DEFAULT_SAVE_PATH);
    }


    /**
     * Saves to a custom path(useful for testing)
     * @param maxUnlockedLevel the max unlocked level
     * @param bestScore the best score
     * @param path the path to save to
     */
    public static void save(final int maxUnlockedLevel, final int bestScore, final String path){

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path),StandardCharsets.UTF_8)){
            writer.write("maxLevel=" + maxUnlockedLevel);
            writer.newLine();
            writer.write("bestScore=" + bestScore);
            writer.newLine();
        } catch (final IOException e){
            LOGGER.severe("SaveManager: impossibile  salvare - " + e.getMessage());
        }
    }


    /**
     * Loads the max unlocked level from the save file
     * Returns 1 if no save file exists
     * @return the max unlocked level
     */
    public static int loadMaxLevel(){
        return loadMaxLevel(DEFAULT_SAVE_PATH);
    }

    /**
     * Loads the max level from a custom path
     * @param path the path to load from
     * @return the max unlocked level
     */
    public static int loadMaxLevel(final String path){

        return readField(path, "maxLevel", 1);
    }


    /**
     * Loads the best score from the save file
     * Returns 0 if no file exists
     * @return
     */
    public static int loadBestScore(){
        return loadBestScore(DEFAULT_SAVE_PATH);
    }

    /**
     * Loads the best score forma custom path
     * @param path the path to load from
     * @return the best score
     */
    public static int loadBestScore(final String path){
        return readField(path, "bestScore", 0);
    }


    
    /**
     * Returns truce if a save file exists at a custom path
     * @return true if save exists
     */
    public static boolean saveExists(){
        return saveExists(DEFAULT_SAVE_PATH);
    }

    /**
     * Returns true if a sve file exists at a custom path.
     * @param path the path
     * @return true if save exists
     */
    public static  boolean saveExists(final String path){
        return new File(path).exists();
    }

    /**
     * Deletes the save file(reset progress)
     */
    public static void deleteSave(){

        new File(DEFAULT_SAVE_PATH).delete();
    }


    private static int readField(final String path, final String field, final int fallback){
        if (!saveExists(path)){
            return fallback;
        }
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path),StandardCharsets.UTF_8)){
            String line= reader.readLine();
            while (line  != null){
                if (line.startsWith(field + "=")){
                    return Integer.parseInt(line.split("=")[1].trim());
                }
                line=reader.readLine();
            }
        }catch (final IOException | NumberFormatException e){
            LOGGER.severe("SaveManager: errore lettura campo " + field + " - " + e.getMessage());
        }

        return fallback;
    }
}

