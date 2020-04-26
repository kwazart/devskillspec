package java.com.kwazarart.app.viewer;

import java.util.Scanner;

public class MainMenu {
    public static void viewMainMenu() {
        SkillViewer viewerSkill = new SkillViewer();
        SpecialtyViewer viewerSpecialty = new SpecialtyViewer();
        DeveloperViewer viewerDeveloper = new DeveloperViewer();

        String select;
        int x;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Main menu:");
            System.out.println("\t1 - Developer");
            System.out.println("\t2 - Skill");
            System.out.println("\t3 - Specialty");
            System.out.println("\t0 - Exit");
            System.out.println("Select variant:");
            select = scanner.nextLine();
            if (select.equals("0")) return;
            if ("1".equals(select)) {
                viewerDeveloper.viewInnerMenu();
            } else if ("2".equals(select)) {
                viewerSkill.viewInnerMenu();
            } else if ("3".equals(select)) {
                viewerSpecialty.viewInnerMenu();
            } else {
                System.out.println("Wrong variant. Try again.\n\n");
            }
        }
    }
}
