public class UnknownInputException extends Exception {

    public UnknownInputException(String s) {
        super("There is an unknown input: " + s);
    }

}
