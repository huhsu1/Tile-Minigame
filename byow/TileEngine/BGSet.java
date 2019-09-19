package byow.TileEngine;

import java.awt.*;

public class BGSet {
    public static final TETile OCEAN = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile LEAVES = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile STAR = new TETile('*', Color.yellow, Color.black, "star");
    public static final TETile SPACE = new TETile(' ', Color.black, Color.black, "space");
}
