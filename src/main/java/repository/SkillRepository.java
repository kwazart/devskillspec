package repository;


import connectionUtil.Connector;
import model.Skill;
import model.Status;
import repository.jdbc.JdbcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class SkillRepository implements Repository<Skill>, JdbcRepository {
    Logger log = Logger.getLogger(DeveloperRepository.class.getName());
    private static final String TABLE = "skills";

    private static String getTABLE() {
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
                + getTABLE()
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

        String query = "SELECT * FROM " + getTABLE() + ";";
        List<List<String>> list = Connector.select(query);
        List<Skill> listSkills = new ArrayList<Skill>();
        for (List<String> line : list) {
            listSkills.add(new Skill(Integer.parseInt(line.get(0)), line.get(1), Status.valueOf(line.get(2))));
        }
        return  listSkills;
    }


    @Override
    public void update(Skill skill) {
        logging("updater");

        String query =" UPDATE " + getTABLE()
                + " SET skill_name = '" + skill.getName()
                + "' WHERE id = " + skill.getId() + ";";

        Connector.addOrUpdate(query);
        query =" UPDATE " + getTABLE()
                + " SET status = '" + skill.getStatus().toString()
                + "' WHERE id = " + skill.getId() + ";";
        Connector.addOrUpdate(query);
    }

    @Override
    public void delete(Skill skill) {
        logging("delete");
        String query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + skill.getId() + ";";
        Connector.addOrUpdate(query);
    }


    public Skill createSkill(Skill skill) {
        add(skill);
        return skill;
    }

    public int searchMaxIndex() {
        return Connector.searchMaxIndex("skills");
    }
}
