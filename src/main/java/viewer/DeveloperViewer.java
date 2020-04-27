package viewer;

import controller.DeveloperController;
import model.Developer;
import java.util.List;
import java.util.Scanner;

public class DeveloperViewer implements Viewer<Developer> {
    private DeveloperController developerController = new DeveloperController();

    @Override
    public void veiwAdd() {
        developerController.create();
    }

    @Override
    public void viewGet() {
        printByIndex(developerController.read());
    }

    @Override
    public void viewGetAll() {
        printAll(developerController.readAll());
    }

    @Override
    public void viewDelete() {
        developerController.delete();
    }

    public void viewUpdate() {
        System.out.println("What do you need to change?");
        String variant;
        while (true) {
            System.out.println("\n\t1 - First name");
            System.out.println("\t2 - Last name");
            System.out.println("\t3 - Specialty");
            System.out.println("\t4 - Skill list");
            System.out.println("\t5 - Status");
            System.out.println("\t0 - Exit");
            System.out.print("Enter variant: ");
            Scanner sc = new Scanner(System.in);
            variant = sc.nextLine();
            if (variant.equals("0")) return;
            switch (variant) {
                case "1":
                    developerController.update(1);
                    break;
                case "2":
                    developerController.update(2);
                    break;
                case "3":
                    developerController.update(3);
                    break;
                case "4":
                    developerController.update(4);
                    break;
                case "5":
                    developerController.update(5);
                    break;
                default:
                    System.out.println("\nWrong choice. Try again.\n");
            }
        }
    }

    @Override
    public void printByIndex(Developer developer) {
        System.out.println(developer.getId() + "\t"
                + developer.getFirstName() + "\t"
                + developer.getLastName() + "\t"
                + developer.getSpecialty() + "\t"
                + developer.getSkills() + "\t"
                + developer.getStatus());
    }

    @Override
    public void printAll(List<Developer> list) {
        for (Developer developer : list) {
            printByIndex(developer);
        }
    }
}
