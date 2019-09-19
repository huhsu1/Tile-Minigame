package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Sight {

    public static void sight(int avatarPos, int avatar2Pos, TETile[][] world, TETile[][] visualWorld) {
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT + 1; j++) {
                visualWorld[i][j] = Tileset.NOTHING;
            }
        }
        sightHelper(avatarPos, world, visualWorld, 0);
        sightHelper(avatar2Pos, world, visualWorld, 6);
        if (Engine.isSwitch) {
            for (int light : WorldGenerator3.lights) {
                sightHelper(light, world, visualWorld, 0);
            }
        }
    }

    private static void sightHelper(int position, TETile[][] world, TETile[][] visualWorld, int far) {
        if (position >= Engine.WIDTH * Engine.HEIGHT) {
            throw new IllegalArgumentException("avatar is out of bounds");
        }
        int xPos = position % Engine.WIDTH;
        int yPos = position / Engine.WIDTH;
        visualWorld[xPos][yPos] = world[xPos][yPos];
        diagonalUL(xPos - 1, yPos + 1, world, visualWorld, far + 1);
        diagonalUR(xPos + 1, yPos + 1, world, visualWorld, far + 1);
        diagonalDL(xPos - 1, yPos - 1, world, visualWorld, far + 1);
        diagonalDR(xPos + 1, yPos - 1, world, visualWorld, far + 1);
        sightUp(xPos, yPos + 1, world, visualWorld, far);
        sightRight(xPos + 1, yPos, world, visualWorld, far);
        sightDown(xPos, yPos - 1, world, visualWorld, far);
        sightLeft(xPos - 1, yPos, world, visualWorld, far);
    }
    private static void sightUp(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalUL(xPos - 1, yPos + 1, world, visualWorld, far + 1);
        diagonalUR(xPos + 1, yPos + 1, world, visualWorld, far + 1);
        sightUp(xPos, yPos + 1, world, visualWorld, far + 1);
    }
    private static void sightRight(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalUR(xPos + 1, yPos + 1, world, visualWorld, far + 1);
        diagonalDR(xPos + 1, yPos - 1, world, visualWorld, far + 1);
        sightRight(xPos + 1, yPos, world, visualWorld, far + 1);
    }
    private static void sightDown(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalDL(xPos - 1, yPos - 1, world, visualWorld, far + 1);
        diagonalDR(xPos + 1, yPos - 1, world, visualWorld, far + 1);
        sightDown(xPos, yPos - 1, world, visualWorld, far + 1);
    }
    private static void sightLeft(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalUL(xPos - 1, yPos + 1, world, visualWorld, far + 1);
        diagonalDL(xPos - 1, yPos - 1, world, visualWorld, far + 1);
        sightLeft(xPos - 1, yPos, world, visualWorld, far + 1);
    }
    private static void diagonalUR(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalUR(xPos + 1, yPos + 1, world, visualWorld, far + 1);
    }
    private static void diagonalUL(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalUL(xPos - 1, yPos + 1, world, visualWorld, far + 1);
    }
    private static void diagonalDR(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalDR(xPos + 1, yPos - 1, world, visualWorld, far + 1);
    }
    private static void diagonalDL(int xPos, int yPos, TETile[][] world, TETile[][] visualWorld, int far) {
        if (xPos < 0 || xPos >= Engine.WIDTH || yPos < 0 || yPos >= Engine.HEIGHT) {
            return;
        }
        TETile newTile = TETile.makeDimmer(world[xPos][yPos], far);
        if (visualWorld[xPos][yPos].getTextColor().getRed() <= newTile.getTextColor().getRed()) {
            visualWorld[xPos][yPos] = newTile;
        }
        if (!(world[xPos][yPos].description().equals("floor")
                ||world[xPos][yPos].description().equals("key"))) {
            return;
        }
        diagonalDL(xPos - 1, yPos - 1, world, visualWorld, far + 1);
    }
}
