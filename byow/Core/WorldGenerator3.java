package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.TileEngine.BGSet;

import java.util.HashSet;

public class WorldGenerator3 {
    /* start with room - makes random hallway, if it doesn't meet the requirement by the
     * end of recursion, go with a different path back down until we reach the end.
     */
    private int numOfRooms;
    TETile[][] world;
    static TETile[][] visualWorld;
    static HashSet<Integer> lights;

    public WorldGenerator3() {
        numOfRooms = Engine.random.nextInt(10) + 20;
        world = makeWorld();
        visualWorld = makeWorld();
        lights = new HashSet<>();

        makeRoom(10, 10, 8, 8, false);
        makeLockedDoor();
        makeKey();
        makeLightSwitch();
        for (int i = 0; i < 25; i++) {
            makeLights();
        }

    }

    public static TETile[][] makeWorld() {
        TETile[][] world = new TETile[Engine.WIDTH][Engine.HEIGHT + 1];
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT + 1; j++) {
                /*
                switch (s) {
                    case 0: //Ocean
                        world[i][j] = BGSet.OCEAN;
                        break;
                    case 1: //Forest
                        world[i][j] = randomForest();
                        break;
                    case 2: //Desert
                        world[i][j] = BGSet.SAND;
                        break;
                    case 3: //Mountain
                        world[i][j] = BGSet.MOUNTAIN;
                        break;
                    case 4: //Space
                        world[i][j] = randomSpace;
                    default:
                        world[i][j] = Tileset.NOTHING;
                 */
                world[i][j] = Tileset.NOTHING;
            }
        }
        return world;
    }

    /*
    private static TETile randomForest() {
        int tileNum = RANDOM.nextInt(2);
        switch (tileNum) {
            case 0: return BGSet.LEAVES;
            case 1: return BGSet.TREE;
            default: return BGSet.TREE;
        }
    }
     */

    /*
    private static TETile randomSpace() {
        int tileNum = RANDOM.nextInt(2);
        switch (tileNum) {
            case 0: return BGSet.STAR;
            case 1: return BGSet.SPACE;
            default: return BGSet.SPACE;
        }
    }
     */

    public void makeRoom(int xPos, int yPos, int width, int height, boolean turn) {
        if (numOfRooms <= 0) {
            return;
        }
        try {
            drawRoom(xPos, yPos, width, height, false);
            if (!turn) {
                numOfRooms -= 1;
            }
            int i = 0;
            int sideChooser = Engine.random.nextInt(4);
            int startX = xPos;
            int startY = yPos;
            while (i < 4) {
                int side = (i + sideChooser) % 4;
                switch (side) {
                    case 0:
                        startX = xPos + Engine.random.nextInt(width - 2) + 1;
                        startY = yPos + height - 2;
                        break;
                    case 1:
                        startX = xPos + width - 2;
                        startY = yPos + Engine.random.nextInt(height - 2) + 1;
                        break;
                    case 2:
                        startX = xPos + Engine.random.nextInt(width - 2) + 1;
                        startY = yPos + 1;
                        break;
                    case 3:
                        startX = xPos + 1;
                        startY = yPos + Engine.random.nextInt(height - 2) + 1;
                        break;
                    default:
                        throw new IllegalArgumentException("Side has not been chosen");
                }
                try {
                    makeHallway(startX, startY, side);
                    i = 4;
                } catch (IllegalArgumentException e) {
                    i += 1;
                }
            }
            int j = 0;
            while (numOfRooms > 0 && j < 3) {
                sideChooser = sideChooser + 1;
                startX = xPos;
                startY = yPos;
                int side = (j + sideChooser) % 4;
                switch (side) {
                    case 0:
                        startX = xPos + Engine.random.nextInt(width - 2) + 1;
                        startY = yPos + height - 1;
                        break;
                    case 1:
                        startX = xPos + width - 1;
                        startY = yPos + Engine.random.nextInt(height - 2) + 1;
                        break;
                    case 2:
                        startX = xPos + Engine.random.nextInt(width - 2) + 1;
                        startY = yPos;
                        break;
                    case 3:
                        startX = xPos;
                        startY = yPos + Engine.random.nextInt(height - 2) + 1;
                        break;
                    default:
                        throw new IllegalArgumentException("Side has not been chosen");
                }
                try {
                    makeHallway(startX, startY, side);
                    j += 1;
                } catch (IllegalArgumentException e) {
                    j += 1;
                }
            }
            return;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Adjust spacing!");
        }
    }

    public void drawRoom(int xPos, int yPos, int w, int h, boolean hallway) {
        //w is width, h is height
        if (xPos < 0 || xPos + w > Engine.WIDTH || yPos < 0 || yPos + h > Engine.HEIGHT) {
            throw new IllegalArgumentException("Out of bounds");
        }
        if (!hallway) {
            for (int i = 1; i < w - 1; i++) {
                for (int j = 1; j < h - 1; j++) {
                    if (world[i + xPos][j + yPos].description().equals("floor")) {
                        throw new IllegalArgumentException("Space taken by dif Room");
                    }
                }
            }
        } else {
            if (w == 3) {
                for (int j = 1; j < h - 1; j++) {
                    if (world[xPos][j + yPos].description().equals("floor")
                            || world[xPos + 2][j + yPos].description().equals("floor")) {
                        throw new IllegalArgumentException("Space taken by dif Hallway");
                    }
                }
            } else { // height == 3
                for (int i = 1; i < h - 1; i++) {
                    if (world[xPos + i][yPos].description().equals("floor")
                            || world[xPos + i][yPos + 2].description().equals("floor")) {
                        throw new IllegalArgumentException("Space taken by dif Hallway");
                    }
                }
            }
        }
        for (int i = 1; i < w - 1; i++) {
            for (int j = 1; j < h - 1; j++) {
                world[i + xPos][j + yPos] = TETile.colorVariant(
                        Tileset.FLOOR, 10, 10, 10, Engine.random);
            }
        }
        for (int i = 0; i < w; i++) {
            if (world[i + xPos][yPos].description().equals("nothing")) {
          //if (world[i + xPos][yPos].getClass().equals(BGSet)) {
                world[i + xPos][yPos] = TETile.colorVariant(
                        Tileset.WALL, 10, 10, 10, Engine.random);
            }
            if (world[i + xPos][yPos + h - 1].description().equals("nothing")) {
                world[i + xPos][yPos + h - 1] = TETile.colorVariant(
                        Tileset.WALL, 10, 10, 10, Engine.random);
            }
        } // walls up and down
        for (int j = 1; j < h - 1; j++) {
            if (world[xPos][yPos + j].description().equals("nothing")) {
          //if (world[i + xPos][yPos].getClass().equals(BGSet)) {
                world[xPos][yPos + j] = TETile.colorVariant(
                        Tileset.WALL, 10, 10, 10, Engine.random);
            }
            if (world[xPos + w - 1][yPos + j].description().equals("nothing")) {
          //if (world[i + xPos][yPos].getClass().equals(BGSet)) {
                world[xPos + w - 1][yPos + j] = TETile.colorVariant(
                        Tileset.WALL, 10, 10, 10, Engine.random);
            }
        } // walls left and right
        // possible that locked door gets replaced by hallway. prob better to add at the end
    }

    public void makeHallway(int startX, int startY, int direction) {
        int length = Engine.random.nextInt(7) + 4;
        int roomX = 0;
        int roomY = 0;
        int roomWidth = 0;
        int roomHeight = 0;
        boolean turn = Engine.random.nextBoolean();
        if (!turn) {
            roomWidth = Engine.random.nextInt(5) + 4; // max 8
            roomHeight = Engine.random.nextInt(5) + 4;
        } else {
            roomWidth = 3;
            roomHeight = 3;
        }
        switch (direction) {
            case 0:
                drawRoom(startX - 1, startY - 1, 3, length, true);
                roomX = startX - 1 - Engine.random.nextInt(roomWidth - 2);
                roomY = startY + length - 3;
                break;
            case 1:
                drawRoom(startX - 1, startY - 1, length, 3, true);
                roomX = startX + length - 3;
                roomY = startY - 1 - Engine.random.nextInt(roomHeight - 2);
                break;
            case 2:
                drawRoom(startX - 1, startY - (length - 2), 3, length, true);
                roomX = startX - 1 - Engine.random.nextInt(roomWidth - 2);
                roomY = startY - (length - 3) - (roomHeight - 1);
                break;
            case 3:
                drawRoom(startX - (length - 2), startY - 1, length, 3, true);
                roomX = startX - (length - 3) - (roomWidth - 1);
                roomY = startY - 1 - Engine.random.nextInt(roomHeight - 2);
                break;
            default:
                throw new IllegalArgumentException("Direction has not been chosen");
        }
        makeRoom(roomX, roomY, roomWidth, roomHeight, turn);
    }

    public void makeLockedDoor() {
        int position = 0;
        boolean viable = false;
        while (!viable) {
            position = Engine.random.nextInt(Engine.WIDTH * Engine.HEIGHT);
            if (world[position % Engine.WIDTH]
                    [position / Engine.WIDTH].description().equals("wall")) {
                try {
                    if (world[position % Engine.WIDTH]
                            [(position / Engine.WIDTH) + 1].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[(position % Engine.WIDTH) + 1]
                            [position / Engine.WIDTH].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[position % Engine.WIDTH]
                            [(position / Engine.WIDTH) - 1].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[(position % Engine.WIDTH) - 1]
                            [position / Engine.WIDTH].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
            }
        }
        world[position % Engine.WIDTH][position / Engine.WIDTH] = Tileset.LOCKED_DOOR;
    }

    public void makeKey() {
        int position = 0;
        boolean viable = false;
        while (!viable) {
            position = Engine.random.nextInt(Engine.WIDTH * Engine.HEIGHT);
            if (world[position % Engine.WIDTH]
                    [position / Engine.WIDTH].description().equals("floor")) {
                viable = true;
            }
        }
        world[position % Engine.WIDTH][position / Engine.WIDTH] = Tileset.KEY;
    }

    public void makeLightSwitch() {
        int position = 0;
        boolean viable = false;
        while (!viable) {
            position = Engine.random.nextInt(Engine.WIDTH * Engine.HEIGHT);
            if (world[position % Engine.WIDTH]
                    [position / Engine.WIDTH].description().equals("wall")) {
                try {
                    if (world[position % Engine.WIDTH]
                            [(position / Engine.WIDTH) + 1].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[(position % Engine.WIDTH) + 1]
                            [position / Engine.WIDTH].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[position % Engine.WIDTH]
                            [(position / Engine.WIDTH) - 1].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[(position % Engine.WIDTH) - 1]
                            [position / Engine.WIDTH].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
            }
        }
        world[position % Engine.WIDTH][position / Engine.WIDTH] = Tileset.LIGHT_SWITCH_OFF;
    }

    private void makeLights() {
        int position = 0;
        boolean viable = false;
        while (!viable) {
            position = Engine.random.nextInt(Engine.WIDTH * Engine.HEIGHT);
            if (world[position % Engine.WIDTH]
                    [position / Engine.WIDTH].description().equals("wall")) {
                try {
                    if (world[position % Engine.WIDTH]
                            [(position / Engine.WIDTH) + 1].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[(position % Engine.WIDTH) + 1]
                            [position / Engine.WIDTH].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[position % Engine.WIDTH]
                            [(position / Engine.WIDTH) - 1].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
                try {
                    if (world[(position % Engine.WIDTH) - 1]
                            [position / Engine.WIDTH].description().equals("floor")) {
                        viable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    viable = false;
                }
            }
        }
        world[position % Engine.WIDTH][position / Engine.WIDTH] = Tileset.LIGHT_OFF;
        lights.add(position);
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        WorldGenerator3 worldGen = new WorldGenerator3();
        ter.renderFrame(worldGen.world);
    }
}
