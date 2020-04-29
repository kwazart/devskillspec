package viewer;

import connectionutil.InputByUserUtil;
import controller.SkillController;
import model.Skill;
import model.Status;

import java.util.List;
import java.util.Scanner;


public class SkillViewer implements Viewer<Skill> {

    private SkillController skillController = new SkillController();

    @Override
    public void veiwAdd() {
        System.out.print("Enter skill name: ");
        skillController.create(new Skill(0, InputByUserUtil.inputData(), Status.ACTIVE));
    }

    @Override
    public void viewGet() {
        printByIndex(skillController.read());
    }

    @Override
    public void viewGetAll() {
        printAll(skillController.readAll());
    }

    public SkillController getSkillController() {
        return skillController;
    }

    @Override
    public void viewDelete() {
        Skill skill = skillController.read();
        if (skill.getStatus().equals(Status.DELETED)) {
            System.out.println("Skill is deleted.");
            return;
        }
        skill.setStatus(Status.DELETED);
        skillController.delete(skill);
    }

    @Override
    public void viewUpdate() {
        Skill skill = skillController.read();

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
            if (variant.equals("1")) {
                System.out.print("Enter new skill name: ");
                skill.setName(InputByUserUtil.inputData());
                break;
            }
            else if (variant.equals("2")) {
                if (skill.getStatus().equals(Status.ACTIVE)) {
                    skill.setStatus(Status.DELETED);
                } else  {
                    skill.setStatus(Status.ACTIVE);
                }
                break;
            }
            else { System.out.println("\nWrong choice. Try again.\n"); }
        }
        skillController.update(skill);
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

    public List<Skill> printCurrentListSkills() {
        List<Skill> list = skillController.readAll();
        for (Skill skill : list) {
            printByIndex(skill);
        }
        return list;
    }
}
