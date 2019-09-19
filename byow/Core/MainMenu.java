package byow.Core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainMenu {
    public static Long seedGetter() {
        return seedGetter(new KeyboardInput("Main Menu"));
    }
    public static Long seedGetter(InputSource inputSource) {
        InputSource input = inputSource;
        StringBuilder seedBuilder = new StringBuilder();
        // total characters = 0;
        while (true) {
            // totalCharacters += 1;
            if (input.getType().equals("Main Menu")) {
                char c = input.getNextKey();
                if (c != 'L') {
                    Engine.inputs.append(c);
                }
                if (c == 'N') {
                    input = new KeyboardInput("New Game");
                }
                if (c == 'L') {
                    try {
                        byte[] encoded = Files.readAllBytes(Paths.get("Load.txt"));
                        String[] string = new String[1];
                        string[0] = new String(encoded, Charset.defaultCharset());
                        Main.main(string);
                    } catch (IOException e) {
                        input = new KeyboardInput("New Game");
                    }
                }
                if (c == 'Q') {
                    System.out.println("done.");
                    break; // quits game
                }
            } else if (input.getType().equals("New Game")) {
                char c = input.getNextKey();
                Engine.inputs.append(c);
                if (c == 'S') {
                    // start the game with the seed
                    Long seed = KeyboardInput.seedStringToLong(seedBuilder.toString());
                    return seed;
                }
                if (c <= 57 && c >= 48) { // they are integer characters
                    seedBuilder.append(c);
                }
            } else if (input.getType().equals("String Input")) {
                String whereInMainMenu = "Main Menu";
                while (input.possibleNextInput()) {
                    char c = input.getNextKey();
                    Engine.inputs.append(c);
                    if (whereInMainMenu.equals("New Game")) {
                        if (c == 'S') {
                            // start the game with the seed
                            Long seed = KeyboardInput.seedStringToLong(seedBuilder.toString());
                            return seed;
                        }
                        if (c <= 57 && c >= 48) { // they are integer characters
                            seedBuilder.append(c);
                        }
                    } else if (whereInMainMenu.equals("Main Menu")) {
                        if (c == 'N') {
                            whereInMainMenu = "New Game";
                        }
                        if (c == 'L') {
                            whereInMainMenu = "Load Game";
                        }
                        if (c == 'Q') {
                            System.out.println("done.");
                            break; // quits game
                        }
                    } else if (input.getType().equals("Load Game")) {
                        if (c <= 57 && c >= 48) { // they are integer characters
                            /** maybe run Engine with the save inputs.
                             * Maybe we can save all of the inputs
                             * we have to implement that anyways so might as well use */
                            System.out.println("load save file " + c);
                            break; //
                        }
                    }
                }
                input = new KeyboardInput(whereInMainMenu);
                // input source works like an iterator. Shouldn't have to keep track of it.
            }
        }
        return null;
    }
}
