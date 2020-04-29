package viewer;

import connectionutil.InputByUserUtil;
import controller.DeveloperController;
import model.Developer;
import model.Skill;
import model.Specialty;
import model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeveloperViewer implements Viewer<Developer> {
    private DeveloperController developerController = new DeveloperController();

    @Override
    public void veiwAdd() {
        System.out.print("Enter first name: ");
        String firstName = InputByUserUtil.inputData();
        System.out.print("Enter last name: ");
        String lastName = InputByUserUtil.inputData();
        Specialty specialty = null;
        List<Specialty> currentListSpecialties = new SpecialtyViewer().printCurrentListSpecialties();
        while (true) {
            System.out.println("Enter specialty name from list :");
            String specialtyName = InputByUserUtil.inputData();
            for (Specialty spec : currentListSpecialties) {
                if (specialtyName.equalsIgnoreCase(spec.getName())) {
                    specialty = new Specialty(0, specialtyName, Status.ACTIVE);
                    break;
                }
            }
            if (specialty != null) break;
            System.out.println("Wrong input! Try again.");
        }
        List<Skill> newListSkill = new ArrayList<>();
        List<Skill> currentListSkills = new SkillViewer().printCurrentListSkills();
        while (true) {
            System.out.println("Enter skill name from list :");
            String skillName = InputByUserUtil.inputData();
            for (Skill skill : currentListSkills) {
                if (skillName.equalsIgnoreCase(skill.getName())) {
                    newListSkill.add(new Skill(0, skillName, Status.ACTIVE));
                }
            }
            System.out.print("Add?\n\t[y] - yes\n\t[any key] - no\nSelect varian: ");
            String var = InputByUserUtil.inputData();
            if (var.equals("y")) continue;
            if (newListSkill.size() >= 1) break;
            System.out.println("Wrong input! Try again.");
        }
        Developer developer = new Developer(0, firstName, lastName, specialty, newListSkill, Status.ACTIVE);
        developerController.create(developer);
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
        Developer developer = developerController.read();
        if (developer.getStatus().equals(Status.DELETED)) {
            System.out.println("Developer is deleted");
            return;
        }
        developer.setStatus(Status.DELETED);
        developerController.delete(developer);
    }

    public void viewUpdate() {
        Developer developer = developerController.read();

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
            System.out.println("Updating procedure. New data.");
            if (variant.equals("0")) return;
            switch (variant){
                case "1":
                    System.out.println("Enter new first name:");
                    developer.setFirstName(InputByUserUtil.inputData());
                    break;
                case "2":
                    System.out.println("Enter new last name:");
                    developer.setLastName(InputByUserUtil.inputData());
                    break;
                case "3":
                    List<Specialty> currentListSpecialties = new SpecialtyViewer().getSpecialtyController().readAll();
                    for (Specialty spec : currentListSpecialties) {
                        System.out.println(spec.getId() + "\t" + spec.getName() + "\t" + spec.getStatus().toString());
                    }
                    while (true) {
                        System.out.println("Enter specialty name from list :");
                        String specialtyName = InputByUserUtil.inputData();
                        for (Specialty spec : currentListSpecialties) {
                            if (specialtyName.equalsIgnoreCase(spec.getName())) {
                                developer.setSpecialty(new Specialty(0, specialtyName, Status.ACTIVE));
                                break;
                            }
                        }
                        if (developer.getSpecialty() != null) break;
                        System.out.println("Wrong input! Try again.");
                    }
                    break;
                case "4":
                    developer.setSkills(changeSkillList(developer));
                    break;
                case "5":
                    if (developer.getStatus().equals(Status.ACTIVE)) {
                        developer.setStatus(Status.DELETED);
                    } else {
                        developer.setStatus(Status.ACTIVE);
                    }
                    break;
                default:
                    System.out.println("\nWrong choice. Try again.\n");
            }

            developerController.update(developer);
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

    private List<Skill> changeSkillList(Developer developer) {
        List<Skill> allSkills  =new SkillViewer().getSkillController().readAll();
        List<Skill> list = developer.getSkills();
        System.out.println(list);
        System.out.println("Current skill list by developer:");
        for (Skill skill : list) {
            System.out.print(skill.getName() + " ");
        }
        String select = null;
        while (true) {
            System.out.print("Delete or Add element?\n\t[d] - delete\n\t[a] - add\n\t[e] - exit\nSelect variant: ");
            select = new Scanner(System.in).nextLine();
            if (select.equalsIgnoreCase("e")) break;
            else if (select.equalsIgnoreCase("d")) {
                System.out.print("Enter skill name for deleting: ");
                String elementForDeleting = new Scanner(System.in).nextLine();
                for (Skill skill : list) {
                    if (skill.getName().equalsIgnoreCase(elementForDeleting)) {
                        list.remove(skill);
                        break;
                    }
                }
            } else if (select.equalsIgnoreCase("a")) {
                System.out.print("Enter skill name for adding: ");
                String elementForAdding = new Scanner(System.in).nextLine();
                for (Skill skill : allSkills) {
                    if (skill.getName().equalsIgnoreCase(elementForAdding)) {
                        list.add(skill);
                        break;
                    }
                }
            } else {
                System.out.println("Wrong select. Try again.");
            }
        }
        return list;
    }
}
