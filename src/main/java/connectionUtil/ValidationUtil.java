package connectionUtil;

public class ValidationUtil {
    public static boolean isDigit (String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
