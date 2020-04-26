package java.com.kwazarart.app.repository;

import java.com.kwazarart.app.inputoutput.Connector;
import java.com.kwazarart.app.inputoutput.InputByUser;
import java.com.kwazarart.app.model.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;

import java.util.logging.SimpleFormatter;

public class DeveloperRepository implements Repository<Developer> {
    private static final String TABLE = "developers";

    public static String getTABLE() {
        return TABLE;
    }

    SimpleFormatter formatter = new SimpleFormatter();
    FileHandler fh;

    {
        try {
            fh = new FileHandler("C:\\Users\\user\\Desktop\\emulationdb\\src\\main\\resources\\log_test.txt");
            fh.setFormatter(formatter);
            log.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    SkillRepository skillRepository = new SkillRepository();
    SpecialtyRepository specialtyRepository = new SpecialtyRepository();

    @Override
    public void add(Developer developer) {
        logging("add developer to table-developers");
        String line = "INSERT INTO developers VALUES ("
                + (searchMaxIndex() + 1) + ", '"
                + developer.getFirstName() + "', '" +
                developer.getLastName() + "', '" +
                developer.getSpecialty().getName() + "', '" +
                developer.listSkillToString(developer.getSkills()) + "', '" +
                developer.getStatus().toString() +
                "');";
        Connector.addOrUpdate(line);
    }

    @Override
    public Developer get(int id) {
        logging("get");
        Developer developer = null;
        String query = "SELECT * FROM "
                + JdbcProperties.getTableDevelopers()
                + " WHERE id = " + id + ";";
        List<List<String>> list = Connector.select(query);
        String [] arraySkills = list.get(0).get(4).split(",");
        List<Skill> listSkills = new ArrayList<Skill>();
        for (String skill : arraySkills) {
            listSkills.add(new Skill(0, skill, Status.ACTIVE));
        }
        developer = new Developer(
                Integer.parseInt(list.get(0).get(0)),
                list.get(0).get(1),
                list.get(0).get(2),
                new Specialty(0, list.get(0).get(3), Status.ACTIVE),
                listSkills,
                Status.valueOf(list.get(0).get(5))
        );
        return developer;
    }

    @Override
    public List<Developer> getAll() {
        logging("getAll");
        String query = "SELECT * FROM " + JdbcProperties.getTableDevelopers() + " ;";
        List<List<String>> list = Connector.select(query);
        List<Developer> listDevelopers = new ArrayList<Developer>();
        String [] arraySkills = null;
        for (List<String> line : list) {
            arraySkills = line.get(4).split(",");
            List<Skill> listSkills = new ArrayList<Skill>();
            for (String skill : arraySkills) {
                listSkills.add(new Skill(0, skill, Status.ACTIVE));
            }
            listDevelopers.add(new Developer(
                    Integer.parseInt(line.get(0)),
                    line.get(1),
                    line.get(2),
                    new Specialty(0, line.get(3), Status.ACTIVE),
                    listSkills,
                    Status.valueOf(line.get(5))
            ));
        }
        return listDevelopers;
    }

    @Override
    public void update(int variant) {
        logging("updater");
        int id = InputByUser.inputInt();
        if (id <= 0 || id > searchMaxIndex()) return;
        String query = null;
        Developer developer = get(id);
        System.out.println("Updating procedure. New data.");
        if (variant == 1) {
            System.out.println("Enter new first name:");
            query =" UPDATE " + getTABLE() + " SET firstName = '" + InputByUser.inputData() + "' WHERE id = " + id + ";";
        } else if (variant == 2) {
            System.out.println("Enter new last name:");
            query =" UPDATE " + getTABLE() + " SET lastName = '" + InputByUser.inputData() + "' WHERE id = " + id + ";";
        } else if (variant == 3) {
            Specialty specialty = addSpecialty();
            query =" UPDATE " + getTABLE() + " SET specialty = '" + specialty.getName() + "' WHERE id = " + id + ";";
        } else if (variant == 4) {
            query =" UPDATE " + getTABLE() + " SET skills = '" + developer.listSkillToString(changeSkillList(developer)) + "' WHERE id = " + id + ";";
        } else if (variant == 5) {
            if (developer.getStatus().equals(Status.ACTIVE)) {
                query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + id + ";";
            } else {
                query = " UPDATE " + getTABLE() + " SET status = 'ACTIVE' WHERE id = " + id + ";";
            }
        }
        Connector.addOrUpdate(query);
    }

    @Override
    public void delete() {
        logging("delete");
        int id = InputByUser.inputInt();
        if (id <= 0 || id > searchMaxIndex()) return;
        String query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + id + ";";
        Connector.addOrUpdate(query);
    }


    private List<Skill> changeSkillList(Developer developer) {
        List<Skill> list = developer.getSkills();
        System.out.println(list);
        System.out.println("Current skill list:");
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
                list.add(skillRepository.createSkill());
            } else {
                System.out.println("Wrong select. Try again.");
            }
        }
        return list;
    }


    public Developer createDeveloper(){
        System.out.print("Enter first name: ");
        String firstName = InputByUser.inputData();
        System.out.print("Enter last name: ");
        String lastName = InputByUser.inputData();
        Specialty specialty = addSpecialty();
        List<Skill> listSkill = addSkill();
        Developer developer = new Developer(0, firstName, lastName, specialty, listSkill, Status.ACTIVE);
        add(developer);
        return developer;
    }

    private List<Skill> addSkill() {
        List<Skill> list = new ArrayList<>();
        System.out.println("Adding skill: ");
        while (true) {
            System.out.println("\n\t[1] - Continue\n\t[0] - Exit");
            String var = new Scanner(System.in).nextLine();
            if (var.equals("0")) break;
            else if(var.equals("1")) {
                Skill skill = skillRepository.createSkill();
                list.add(skill);
            } else {
                System.out.println("Error. Try again.");
            }
        }
        return list;
    }

    private Specialty addSpecialty() {
        Specialty specialty = specialtyRepository.createSpecialty();
        return specialty;
    }

    public int searchMaxIndex() {
        return Connector.searchMaxIndex("developers");
    }
}
