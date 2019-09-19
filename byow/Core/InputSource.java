package byow.Core;

public interface InputSource {
    String getType();
    char getNextKey();
    boolean possibleNextInput();
}
