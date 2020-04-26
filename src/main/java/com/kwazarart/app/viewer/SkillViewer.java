package java.com.kwazarart.app.viewer;

import java.com.kwazarart.app.controller.SkillController;
import java.com.kwazarart.app.model.Skill;

import java.util.List;
import java.util.Scanner;


public class SkillViewer implements Viewer<Skill> {

    private SkillController skillController = new SkillController();

    @Override
    public void veiwAdd() {
        skillController.create();
    }

    @Override
    public void viewGet() {
        printByIndex(skillController.read());
    }

    @Override
    public void viewGetAll() {
        printAll(skillController.readAll());
    }

    @Override
    public void viewDelete() {
        skillController.delete();
    }

    @Override
    public void viewUpdate() {
        System.out.println("What do you need to change?");
        String variant;
        while (true) {
            System.out.println("\n\t1 - skill name");
            System.out.println("\t2 - status");
            System.out.println("\t0 - Exit");
            System.out.print("Enter variant: ");
            Scanner sc = new Scanner(System.in);
            variant = sc.nextLine();
            if (variant.equals("0")) { return; }
            if (variant.equals("1")) { skillController.update(1);}
            else if (variant.equals("2")) { skillController.update(2);}
            else { System.out.println("\nWrong choice. Try again.\n"); }
        }
    }

    @Override
    public void printByIndex(Skill skill) {
        System.out.println(skill.getId() + "\t" + skill.getName() + "\t" + skill.getStatus());
    }

    @Override
    public void printAll(List<Skill> list) {
        for (Skill skill : list) {
            printByIndex(skill);
        }
    }
}
