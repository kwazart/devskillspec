package java.com.kwazarart.app.repository;


import java.com.kwazarart.app.inputoutput.InputByUser;
import java.com.kwazarart.app.inputoutput.Connector;
import java.com.kwazarart.app.model.JdbcProperties;
import java.com.kwazarart.app.model.Skill;
import java.com.kwazarart.app.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class SkillRepository implements Repository<Skill> {
    Logger log = Logger.getLogger(DeveloperRepository.class.getName());
    private static final String TABLE = "skills";

    public static String getTABLE() {
        return TABLE;
    }

    @Override
    public void add(Skill skill) {
        logging("add skill to table-skills");
        String line = "INSERT INTO "+ getTABLE() + " VALUES (" +
                (searchMaxIndex() + 1) +
                ", '" + skill.getName() +
                "', 'ACTIVE');";
        Connector.addOrUpdate(line);
    }


    @Override
    public Skill get(int id) {
        logging("get");

        Skill skill = null;
        String query = "SELECT * FROM "
                + JdbcProperties.getTableSkills()
                + " WHERE id = " + id + ";";
        List<List<String>> list = Connector.select(query);
        skill = new Skill(
                Integer.parseInt(list.get(0).get(0)),
                list.get(0).get(1),
                Status.valueOf(list.get(0).get(2))
        );
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        logging("getAll");

        String query = "SELECT * FROM " + JdbcProperties.getTableSkills() + ";";
        List<List<String>> list = Connector.select(query);
        List<Skill> listSkills = new ArrayList<Skill>();
        for (List<String> line : list) {
            listSkills.add(new Skill(Integer.parseInt(line.get(0)), line.get(1), Status.valueOf(line.get(2))));
        }
        return  listSkills;
    }


    @Override
    public void update(int variant) {
        logging("updater");
        int id = InputByUser.inputInt();
        if (id <= 0 || id > searchMaxIndex()) return;
        String query = null;
        if (variant == 1) {
            System.out.println("Updating procedure. New data.");
            System.out.println("Enter new skill name: ");
            query =" UPDATE " + getTABLE() + " SET skill_name = '" + InputByUser.inputData() + "' WHERE id = " + id + ";";
        } else if (variant == 2) {
            System.out.println("Updating procedure. New data.");
            Skill skill = get(id);
            if (skill.getStatus().equals(Status.ACTIVE)) {
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

    public static Skill stringToSkill(String line) {
        String[] elements = line.split(",");
        return new Skill(Integer.parseInt(elements[0]), elements[1], Status.valueOf(elements[2]));
    }

    public Skill createSkill() {
        System.out.print("Enter skill name: ");
        Skill skill = new Skill(0, InputByUser.inputData(), Status.ACTIVE);
        add(skill);
        return skill;
    }

    public int searchMaxIndex() {
        return Connector.searchMaxIndex("skills");
    }
}
