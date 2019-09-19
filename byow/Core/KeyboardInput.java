package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;


public class KeyboardInput implements InputSource {
    private static final boolean PRINT_TYPED_KEYS = false;
    String type;

    public KeyboardInput(String type) {
        this.type = type;
        if (type.equals("Main Menu")) {
            int fontSize = Engine.HEIGHT;
            StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, fontSize));
            double shift = fontSize / (Engine.HEIGHT * 16.0);
            StdDraw.text(0.5, 0.3 + shift, "New Game (N)");
            StdDraw.text(0.5, 0.3, "Load Game (L)");
            StdDraw.text(0.5, 0.3 - shift, "Quit (Q)");
            StdDraw.setFont(new Font("Arial", Font.PLAIN, fontSize * 2));
            StdDraw.text(0.5, 0.7, "CS61B: THE GAME");
        } else if (type.equals("New Game")) {
            int fontSize = Engine.HEIGHT * 2;
            StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, fontSize));
            StdDraw.text(0.5, 0.7, "Enter Seed");
            StdDraw.text(0.5, 0.3, "Press S to finish");
            /*
            StdDraw.text(0.5, 0.3, "Press S to go next");
            int fontSize = Engine.HEIGHT * 2;
            StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, fontSize));
            StdDraw.text(0.5, 0.7, "Select a Setting");
            double shift = fontSize / (Engine.HEIGHT * 16.0);
            StdDraw.setFont(new Font("Bazooka", Font.PLAIN, fontSize / 2));
            StdDraw.setPenColor(Color.blue);
            StdDraw.text(0.5, 0.3 + shift, "Ocean (O)");
            StdDraw.setFont(new Font("Georgia", Font.PLAIN, fontSize / 2));
            StdDraw.setPenColor(Color.green);
            StdDraw.text(0.5, 0.3 + (shift / 2), "Forest (F)");
            StdDraw.setFont(new Font("Papyrus", Font.PLAIN, fontSize / 2));
            StdDraw.setPenColor(Color.yellow);
            StdDraw.text(0.5, 0.3, "Desert (D)");
            StdDraw.setFont(new Font("Lithograph", Font.PLAIN, fontSize / 2));
            StdDraw.setPenColor(Color.gray);
            StdDraw.text(0.5, 0.3 - (shift / 2), "Mountains (M)");
            StdDraw.setFont(new Font("Denmark", Font.PLAIN, fontSize / 2));
            StdDraw.setPenColor(Color.white);
            StdDraw.text(0.5, 0.3  - shift, "Space (S)");
             */

        }
    }

    public static Long seedStringToLong(String seedString) {
        try {
            return Long.parseLong(seedString);
        } catch (NumberFormatException e) { // too long
            return seedStringToLong(seedString.substring(1));
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
        }
    }

    @Override
    public boolean possibleNextInput() {
        return StdDraw.hasNextKeyTyped();
    }
}
