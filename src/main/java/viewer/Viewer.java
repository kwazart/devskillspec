package viewer;

import java.util.List;
import java.util.Scanner;

public interface Viewer<E> {
    default void viewInnerMenu() {
        String select;
        int x;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inner menu:");
            System.out.println("\t1 - Add");
            System.out.println("\t2 - Get");
            System.out.println("\t3 - Get all");
            System.out.println("\t4 - Update");
            System.out.println("\t5 - Delete");
            System.out.println("\t0 - Exit");
            System.out.println("Select variant:");
            select = scanner.nextLine();
            if (select.equals("0")) return;
            if ("1".equals(select)) {
                veiwAdd();
            } else if ("2".equals(select)) {
                viewGet();
            } else if ("3".equals(select)) {
                viewGetAll();
            } else if ("4".equals(select)) {
                viewUpdate();
            } else if ("5".equals(select)) {
                viewDelete();
            } else {
                System.out.println("Wrong variant. Try again.\n\n");
            }
        }
    }
    void veiwAdd();

    void viewGet();

    void viewGetAll();

    void viewUpdate();

    void viewDelete();

    default void printAll(List<E> list) {
        for (E e : list) {
            System.out.println(e);
        }
        System.out.println("\n");
    }

    default void printByIndex(E e) {
        System.out.println(e);
    }
}
