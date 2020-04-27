package viewer;

import controller.SpecialtyController;
import model.Specialty;
import java.util.List;
import java.util.Scanner;

public class SpecialtyViewer implements Viewer<Specialty> {
    private SpecialtyController specialtyController = new SpecialtyController();

    @Override
    public void veiwAdd() {
        specialtyController.create();
    }

    @Override
    public void viewGet() {
        printByIndex(specialtyController.read());
    }

    @Override
    public void viewGetAll() {
        printAll(specialtyController.readAll());
    }

    @Override
    public void viewDelete() {
        specialtyController.delete();
    }

    @Override
    public void viewUpdate() {
        System.out.println("What do you need to change?");
        String variant;
        while (true) {
            System.out.println("\n\t1 - specialty name");
            System.out.println("\t2 - status");
            System.out.println("\t0 - Exit");
            System.out.print("Enter variant: ");
            Scanner sc = new Scanner(System.in);
            variant = sc.nextLine();
            if (variant.equals("0")) { return; }
            if (variant.equals("1")) { specialtyController.update(1);}
            else if (variant.equals("2")) { specialtyController.update(2);}
            else { System.out.println("\nWrong choice. Try again.\n"); }
        }
    }

    @Override
    public void printByIndex(Specialty specialty) {
        System.out.println(specialty.getId() + "\t" + specialty.getName() + "\t" + specialty.getStatus());
    }

    @Override
    public void printAll(List<Specialty> list) {
        for (Specialty specialty : list) {
            printByIndex(specialty);
        }
    }
}
