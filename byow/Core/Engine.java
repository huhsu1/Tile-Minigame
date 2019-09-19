package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private Long seed;
    static Random random = new Random(123456789);
    public static boolean hasKey;
    public static StringBuilder inputs;
    public static final boolean lightOn = false;
    public static boolean isSwitch = false;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        inputs = new StringBuilder();
        seed = MainMenu.seedGetter();
        if (seed == null) {
            System.exit(0);
        }
        random = new Random(seed);
        hasKey = false;
        WorldGenerator3 worldGen = new WorldGenerator3();
        Game.game(worldGen.world, worldGen.visualWorld, new KeyboardInput("game"));
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        InputSource stringInput = new StringInput(input);
        seed = MainMenu.seedGetter(stringInput);
        random = new Random(seed);
        WorldGenerator3 worldGen = new WorldGenerator3();
        Game.game(worldGen.world, worldGen.visualWorld, stringInput);
        // Game.game(worldGen.world, new KeyboardInput("game"));
        // write some way to make interact with Keyboard happen with the stringInput here

        TETile[][] finalWorldFrame = worldGen.world;
        return finalWorldFrame;
    }
}
