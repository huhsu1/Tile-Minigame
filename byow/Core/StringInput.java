package byow.Core;

public class StringInput implements InputSource {
    private int index;
    private String input;
    private int inputSize;

    public StringInput(String stringInput) {
        input = stringInput;
        inputSize = stringInput.length();
        index = 0;
    }
    @Override
    public char getNextKey() {
        if (possibleNextInput()) {
            index += 1;
            return Character.toUpperCase(input.charAt(index - 1));
        }
        return '\u0000'; //default null for char
    }
    @Override
    public boolean possibleNextInput() {
        return index < inputSize;
    }
    @Override
    public String getType() {
        return "String Input";
    }

    public static void main(String[] args) {
        InputSource si = new StringInput("asdf");
        while (si.possibleNextInput()) {
            System.out.println(si.getNextKey());
        }
    }
}
