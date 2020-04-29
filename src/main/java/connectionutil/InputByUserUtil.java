package connectionutil;

import java.util.Scanner;

public class InputByUserUtil {
    public static String inputData() {
        return new Scanner(System.in).nextLine();
    }
    public static int inputInt() {
        System.out.print("Input index number: ");
        while (true) {
            String s = inputData();
            if (ValidationUtil.isDigit(s)) {
                return Integer.parseInt(s);
            }
        }
    }

}
