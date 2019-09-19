package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Scanner;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};
    private static final String[] ENCOURAGEMENT_LOL = {"Bruh...", "What the heck", "You dumb",
            "My grandma can do better than you", "LMAO you suck",
            "You smell like dick cheese", "Poopoo head", "Can you even lift???",
            "Omae wa mou shindeiru", "Peepee", "Donald Trump", "Ring-a-ding ding. You dumb"};

    public static void main(String[] args) {
        //@Source https://stackoverflow.com/questions/5287538/how-can-i-get-the-user-input-in-java
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        if (args.length < 1) {
            System.out.println("Please enter a seed");
        }

        long seed = reader.nextLong();
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder randString = new StringBuilder();

        while (randString.length() < n) {
            randString.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return randString.toString().toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen

        StdDraw.clear();
        StdDraw.clear(Color.black);

        double midWidth = width / 2;
        double midHeight = height / 2;
        Font large = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setFont(large);
        StdDraw.setPenColor(Color.yellow);
        StdDraw.text(midWidth, midHeight, s);

        if (!gameOver) {
            Font genText = new Font("Helvetica", Font.ITALIC, 10);
            StdDraw.setFont();
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, height - 1, "Round" + round);
            if (playerTurn == false) {
                StdDraw.text(midWidth, height - 1, "Watch");
            } else { //if playerTurn == true
                StdDraw.text(midWidth, height - 1, "Type!");
            }
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        }

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame(" ");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
            drawFrame(input);
        }
        StdDraw.pause(1000);
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Engine loop

        gameOver = false;
        playerTurn = false;
        round = 1;

        while (!gameOver) {
            if (round == 1) {
                playerTurn = false;
                drawFrame("Round " + round + " Have fun~");
                StdDraw.pause(1500);
            } else if (round == 2) {
                playerTurn = false;
                drawFrame("Round " + round + " Okay, that was an easy cookie.");
                StdDraw.pause(1500);
            } else if (round == 3) {
                playerTurn = false;
                drawFrame("Round " + round + " Still pretty easy...");
                StdDraw.pause(1500);
            } else if (round == 4) {
                playerTurn = false;
                drawFrame("Round " + round);
                StdDraw.pause(1500);
                drawFrame("Okay, time to amp up the difficulty");
                StdDraw.pause(1500);
            } else if (round == 5) {
                playerTurn = false;
                drawFrame("Round " + round);
                StdDraw.pause(1500);
                drawFrame("I'm just gonna start throwing insults");
                StdDraw.pause(1500);
            } else {
                playerTurn = false;
                drawFrame("Round " + round);
                StdDraw.pause(1500);
                drawFrame(ENCOURAGEMENT_LOL[rand.nextInt(ENCOURAGEMENT_LOL.length)]);
                StdDraw.pause(1500);
            }

            String roundString = generateRandomString(round);
            flashSequence(roundString);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);

            if (!userInput.equals(roundString)) {
                gameOver = true;
                drawFrame("Game Over! Final level: " + round);
            } else {
                drawFrame("Correct");
                StdDraw.pause(1500);
                round += 1;
            }
        }
    }

}
