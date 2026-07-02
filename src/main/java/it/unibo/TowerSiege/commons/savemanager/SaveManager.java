package it.unibo.TowerSiege.commons.savemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the persistence of core game progression data into a text-based file.
 * This class is responsible for tracking the highest level unlocked and the top score achieved.
 */


public final class SaveManager {
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
     * Saves to a custom path (useful for testing).
     */

    public static void save(final int maxUnlockedLevel, final int bestScore, final String path){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            writer.write("maxLevel=" + maxUnlockedLevel);
            writer.newLine();
            writer.write("bestScore=" + bestScore);
            writer.newLine();
        } catch (IOException e){
            System.err.println("SaveManager: impossibile  salvare - " + e.getMessage());
        }
    }



    public static int loadMaxLevel(){
        return loadMaxLevel(DEFAULT_SAVE_PATH);
    }

    public static int loadMaxLevel(final String path){

        return readField(path, "maxLevel", 1);
    }


    public static int loadBestScore(){
        return loadBestScore(DEFAULT_SAVE_PATH);
    }

    public static int loadBestScore(final String path){
        return readField(path, "bestScore", 0);
    }


    

    public static boolean saveExists(){
        return saveExists(DEFAULT_SAVE_PATH);
    }

    public static  boolean saveExists(final String path){
        return new File(path).exists();
    }


    public static void deleteSave(){

        new File(DEFAULT_SAVE_PATH).delete();
    }


    private static int readField(final String path, final String field, final int fallback){
        if (!saveExists(path))
            return fallback;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null){
                if (line.startsWith(field + "=")){
                    return Integer.parseInt(line.split("=")[1].trim());
                }
            }
        }catch (IOException | NumberFormatException e){
            System.err.println("SaveManager: errore lettura campo " + field + " - " + e.getMessage());
        }

        return fallback;
    }
}
