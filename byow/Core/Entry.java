package byow.Core;

public class Entry {
    private Long seed;
    private StringInput si;

    public Entry(Long see, StringInput s) {
        seed = see;
        si = s;
    }

    public Long getSeed() {
        return seed;
    }
    public StringInput getSInput() {
        return si;
    }
}
