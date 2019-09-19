package byow.Core;

public class KeyboardInputTest {

    public static void main(String[] args) {

        int totalCharacters = 0;
        KeyboardInput input = new KeyboardInput("Main Menu");
        StringBuilder seedBuilder = new StringBuilder();
        while (true) {
            totalCharacters += 1;
            char c = input.getNextKey();
            if (input.type.equals("Main Menu")) {
                if (c == 'N') {
                    input = new KeyboardInput("New Game");
                }
                if (c == 'L') {
                    System.out.println("go to loadGame");
                }
                if (c == 'Q') {
                    System.out.println("done.");
                    break; // quits game
                }
            } else if (input.type.equals("New Game")) {
                if (c == 'S') {
                    // start the game with the seed
                    Long seed = KeyboardInput.seedStringToLong(seedBuilder.toString());
                    System.out.println("seedString = " + seedBuilder.toString());
                    System.out.println("seed = " + seed);
                    break; // game starts with seed
                }
                if (c <= 57 && c >= 48) { // they are integer characters
                    seedBuilder.append(c);
                }
            } else if (input.type.equals("Load Game")) {
                if (c <= 57 && c >= 48) { // they are integer characters
                    System.out.println("load save file " + c);
                    break; //
                }
            }
        }

        System.out.println("Processed " + totalCharacters + " characters.");
    }
}
