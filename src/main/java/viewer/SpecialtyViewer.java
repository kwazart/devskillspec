package viewer;

import connectionUtil.InputByUser;
import controller.SpecialtyController;
import model.Specialty;
import model.Status;

import java.util.List;
import java.util.Scanner;

public class SpecialtyViewer implements Viewer<Specialty> {
    private SpecialtyController specialtyController = new SpecialtyController();

    @Override
    public void veiwAdd() {
        System.out.print("Enter specialty name: ");
        specialtyController.create(new Specialty(0, InputByUser.inputData(), Status.ACTIVE));
    }

    @Override
    public void viewGet() {
        printByIndex(specialtyController.read());
    }

    public SpecialtyController getSpecialtyController() {
        return specialtyController;
    }

    @Override
    public void viewGetAll() {
        printAll(specialtyController.readAll());
    }

    @Override
    public void viewDelete() {
        Specialty specialty = specialtyController.read();
        if (specialty.getStatus().equals(Status.DELETED)) {
            System.out.println("Specialty is deleted.");
            return;
        }
        specialty.setStatus(Status.DELETED);
        specialtyController.delete(specialty);
    }

    @Override
    public void viewUpdate() {
        Specialty specialty = specialtyController.read();

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
            if (variant.equals("1")) {
                System.out.print("Enter new specialty name: ");
                specialty.setName(InputByUser.inputData());
                break;
            }
            else if (variant.equals("2")) {
                if (specialty.getStatus().equals(Status.ACTIVE)) {
                    specialty.setStatus(Status.DELETED);
                } else {
                    specialty.setStatus(Status.ACTIVE);
                }
                break;
            }
            else { System.out.println("\nWrong choice. Try again.\n"); }
        }
        specialtyController.update(specialty);
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
