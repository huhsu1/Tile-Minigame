package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Game {
    // takes in TETile[][] and makes character move and stuff
    public static int spawn(TETile[][] world, TETile avatar) {
        int position = Engine.random.nextInt(Engine.WIDTH * Engine.HEIGHT);
        while (!world[position % Engine.WIDTH][position / Engine.WIDTH].description()
                .equals("floor")) {
            position = Engine.random.nextInt(Engine.WIDTH * Engine.HEIGHT);
        }
        world[position % Engine.WIDTH][position / Engine.WIDTH] = avatar;
        return position;
    }

    private static int avatarActionFromTo(int currentPos, int nextPos, TETile[][] world) {
        if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("floor")) {
            world[currentPos % Engine.WIDTH][currentPos / Engine.WIDTH] = Tileset.FLOOR;
            world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.AVATAR;
            return nextPos;
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("locked door")) {
            if (Engine.hasKey) {
                world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.UNLOCKED_DOOR;
            }
            return currentPos;
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("unlocked door")) {
            winGame(1);
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("key")) {
            Engine.hasKey = true;
            world[currentPos % Engine.WIDTH][currentPos / Engine.WIDTH] = Tileset.FLOOR;
            world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.AVATAR;
            return nextPos;
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("light switch: off")) {
            world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.LIGHT_SWITCH_ON;
            Engine.isSwitch = true;
            for (int light : WorldGenerator3.lights) {
                world[light % Engine.WIDTH][light / Engine.WIDTH] = Tileset.LIGHT_ON;
            }
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("light switch: on")) {
            world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.LIGHT_SWITCH_OFF;
            Engine.isSwitch = false;
            for (int light : WorldGenerator3.lights) {
                world[light % Engine.WIDTH][light / Engine.WIDTH] = Tileset.LIGHT_OFF;
            }
        } else {
            return currentPos;
        }
        return currentPos;
    }

    private static int avatar2ActionFromTo(int currentPos, int nextPos, TETile[][] world) {
        if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("floor")) {
            world[currentPos % Engine.WIDTH][currentPos / Engine.WIDTH] = Tileset.FLOOR;
            world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.AVATAR2;
            return nextPos;
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("light switch: off")) {
            world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.LIGHT_SWITCH_ON;
            Engine.isSwitch = true;
            for (int light : WorldGenerator3.lights) {
                world[light % Engine.WIDTH][light / Engine.WIDTH] = Tileset.LIGHT_ON;
            }
            return currentPos;
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("light switch: on")) {
            world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH] = Tileset.LIGHT_SWITCH_OFF;
            Engine.isSwitch = false;
            for (int light : WorldGenerator3.lights) {
                world[light % Engine.WIDTH][light / Engine.WIDTH] = Tileset.LIGHT_OFF;
            }
            return currentPos;
        } else if (world[nextPos % Engine.WIDTH][nextPos / Engine.WIDTH].description().equals("you")) {
            winGame(2);
        } else {
            return currentPos;
        }
        return currentPos;
    }

    public static void game(TETile[][] world, TETile[][] visualWorld, InputSource inputSource) {
        /** replace with Engine's renderer later */
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH, Engine.HEIGHT + 1);
        int avatarPos = spawn(world, Tileset.AVATAR);
        int avatar2Pos = spawn(world, Tileset.AVATAR2);
        Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
        if (Engine.lightOn) {
            ter.renderFrame(world);
        } else {
            ter.renderFrame(visualWorld);
        }
        while (true) {
            char c = '\u0000';
            Point p = MouseInfo.getPointerInfo().getLocation();
            int pX = Math.floorDiv((int) p.x, 16);
            int pY = Engine.HEIGHT + 4 - Math.floorDiv((int) p.y, 16);
            if (inputSource.possibleNextInput()) {
                c = inputSource.getNextKey();
            } else {
                inputSource = new KeyboardInput("Game");
            }
            if (c != '\u0000' && c != ':') {
                Engine.inputs.append(c);
            }

            if (c == 'W') {
                avatarPos = avatarActionFromTo(avatarPos, avatarPos + Engine.WIDTH, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }
            if (c == 'D') {
                avatarPos = avatarActionFromTo(avatarPos, avatarPos + 1, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }
            if (c == 'S') {
                avatarPos = avatarActionFromTo(avatarPos, avatarPos - Engine.WIDTH, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }
            if (c == 'A') {
                avatarPos = avatarActionFromTo(avatarPos, avatarPos - 1, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }

            if (c == 'I') {
                avatar2Pos = avatar2ActionFromTo(avatar2Pos, avatar2Pos + Engine.WIDTH, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }
            if (c == 'L') {
                avatar2Pos = avatar2ActionFromTo(avatar2Pos, avatar2Pos + 1, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }
            if (c == 'K') {
                avatar2Pos = avatar2ActionFromTo(avatar2Pos, avatar2Pos - Engine.WIDTH, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }
            if (c == 'J') {
                avatar2Pos = avatar2ActionFromTo(avatar2Pos, avatar2Pos - 1, world);
                Sight.sight(avatarPos, avatar2Pos, world, visualWorld);
            }


            if (0 <= pX && pX < Engine.WIDTH) {
                if (0 <= pY && pY < Engine.HEIGHT) {
                    if (world[pX][pY] != null) {
                        String tileDesc = null;
                        if (Engine.lightOn) {
                            tileDesc = getTileDesc(world, pX, pY);
                        } else {
                            tileDesc = getTileDesc(visualWorld, pX, pY);
                        }
                        putDesc(world, tileDesc);
                        putDesc(visualWorld, tileDesc);
                    }
                }
            }

            if (Engine.lightOn) {
                ter.renderFrame(world);
            } else {
                ter.renderFrame(visualWorld);
            }

            if (c == ':') {
                c = inputSource.getNextKey();
                if (c == 'Q') {
                    try {
                        PrintWriter writer = new PrintWriter("Load.txt");
                        writer.write(Engine.inputs.toString());
                        writer.close();
                        System.exit(0);
                    } catch (FileNotFoundException e) {
                        System.out.println("fuck you");
                        return;
                    }
                }
            }
        }

    }

    private static void putDesc(TETile[][] world, String tileDesc) {
        char[] character = tileDesc.toCharArray();
        for (int i = 0; i < character.length; i++) {
            world[i][Engine.HEIGHT] = new TETile(character[i], Color.white, Color.black, "desc");
        }
        for (int j = character.length; j < Engine.WIDTH; j++) {
            world[j][Engine.HEIGHT] = Tileset.NOTHING;
        }

    }

    private static String getTileDesc(TETile[][] world, int x, int y) {
        return world[x][y].description();
    }

    private static void winGame(int i) {
        String winningScreen = "Player " + i + " has won the game!";
        int fontSize = Engine.HEIGHT * 2;
        StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, fontSize));
        StdDraw.text(0.5, 0.5, winningScreen);
        StdDraw.show();
        StdDraw.pause(10000);
        System.exit(0);
    }

}

