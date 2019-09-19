package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final int seed = 10;

    /* creates empty world */
    public static TETile[][] createWorld() {
        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    /* creates hexagon size s with upper left corner at (x, y)
     * with TETTiles input
     */
    // little helper to test
    public static void drawHexagon(int x, int y, int s, char c, TETile[][] world) {
        drawHexagon(x, y, s, new TETile(c, Color.white, Color.black, "char"), world);
    }

    public static void drawHexagon(int x, int y, int s, TETile tetile, TETile[][] world) {
        if (x < s - 1 || x > WIDTH - (2 * s - 1) || y >= HEIGHT || y < 2 * s - 1) {
            throw new IllegalArgumentException("Hexagon will draw out of bounds");
        }
        Random r = new Random(seed);
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s + (2 * i); j++) {
                world[x - i + j][y + i] =
                        TETile.colorVariant(tetile, 100, 100, 100, r);
                world[x - i + j][y + 2 * s - 1 - i] =
                        TETile.colorVariant(tetile, 100, 100, 100, r);
            }
        }
    }

    private static TETile randomTETile() {
        Random r = new Random(seed);
        int randomInt = r.nextInt(6);
        switch (randomInt) {
            case 0: return Tileset.TREE;
            case 1: return Tileset.FLOOR;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.FLOWER;
            case 5: return Tileset.SAND;
            case 6: return Tileset.MOUNTAIN;
            default: return null; //shouldn't happen
        }
    }
    /* fills world with hexagons with size s with random viable background tile
     * fills in a hexagonal manner as like in lab
    public static void fillWithHex(TETile[][] world, int size) {
        if (world == null) {
            throw new IllegalArgumentException("invalid input for world");
        }
        int startPosX = (WIDTH / 2) - (size  / 2);
        int height = 0;
        while (true) {
            try {
                drawHexagon(startPosX, height * (2 * size), size, randomTETile(), world);
                height += 1;
            } catch (Exception e) {
                break;
            }
        }

    }
    */

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = createWorld();
        drawHexagon(15, 15, 5, 'a', world);

        ter.renderFrame(world);
    }
}
