package it.unibo.TowerSiege.commons.maploader;

import it.unibo.TowerSiege.commons.mapdata.MapData;
import it.unibo.TowerSiege.commons.gameconstants.GameConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Loader for custom JSON map format with waypoints and building spots
 */

public class MapLoader {

    private static final Logger LOGGER = Logger.getLogger(MapLoader.class.getName());

    /**
     * Loads a map from a file path
     * @param filePath the path to the map file
     * @return the parsed map data
     * @throws IOException if an error occurs reading the file
     */
    
    public MapData loadMap(final String filePath) throws IOException {
        final String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        return parseJson(content);
    }

    /**
     * Loads a map from the classpath
     * @param resourcePath the path to the resource
     * @return the parsed map data, or null if not found
     */

    public MapData loadFromClasspath(final String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
               LOGGER.severe("Resource not found on classpath:" + resourcePath);
                return null;
            }
            final String content = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
            return parseJson(content);
        } catch (final IOException e) {
            LOGGER.severe("Error reading classpath resource: " + e.getMessage());
            return null;
        }
    }

    private MapData parseJson(final String content) {
        final MapData mapData = new MapData();
        mapData.setWidth(extractInt(content, "mapwidth", GameConstants.MAP_WIDTH));
        mapData.setheight(extractInt(content, "mapheight", GameConstants.MAP_HEIGHT));
        mapData.setBackground(extractStringField(content, "background"));

        mapData.setWaypoints(extractDoubleArray2D(content, "waypoints"));
        mapData.setBuildingSpots(extractDoubleArray2D(content, "buildingSpots"));
        mapData.setDecorations(extractDoubleArray2D(content,"decorations"));
        return mapData;
    }

    private int extractInt(final String json, final String field, final int fallback) {
        final Pattern p = Pattern.compile("\"" + field + "\"\\s*:\\s*(\\d+)");
        final Matcher m = p.matcher(json);
        if(m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return fallback;
    }

    private String extractStringField(final String json, final String field) {
        final Pattern p = Pattern.compile("\"" + field + "\"\\s*:\\s*\"([^\"]*)\"");
        final Matcher m = p.matcher(json);
        return m.find() ? m.group(1) :null;
    }

    private List<double[]> extractDoubleArray2D(final String json, final String field) {
        final List<double[]> result = new ArrayList<>();
        final Pattern arrayStartPattern = Pattern.compile("\"" + field + "\"\\s*:\\s*\\[");
        final Matcher arrayStartMatcher = arrayStartPattern.matcher(json);

        if(!arrayStartMatcher.find()) {
            return result;
        }

        final int startIdx = arrayStartMatcher.end() - 1;
        final String arrayContent = extractBalancedArray(json, startIdx);
        if(arrayContent == null) {
            return result;
        }

        //Extract [ x, y ]
        final Pattern pairPattern = Pattern.compile("\\[\\s*([\\d.]+)\\s*,\\s*([\\d.]+)\\s*\\]");
        final Matcher pairMatcher = pairPattern.matcher(arrayContent);

        while (pairMatcher.find()) {
            try {
                final double x = Double.parseDouble(pairMatcher.group(1));
                final double y = Double.parseDouble(pairMatcher.group(2));
                result.add(new double[]{x, y});
            }   catch (final NumberFormatException ignored) {}
        }
        return result;
    }

    private String extractBalancedArray(final String json, final int start) {
        if (start < 0 || start >= json.length() || json.charAt(start) != '[') {
            return null;
        }
        int depth = 0;
        for (int i =start; i < json.length(); i++) {
            final char c = json.charAt(i);
            if (c == '[') {
                depth ++;
            } else if (c == ']') {
                depth --;
                if( depth == 0) {
                    return json.substring(start + 1, i);
                }
            }
        }
        return null;
    }
}

