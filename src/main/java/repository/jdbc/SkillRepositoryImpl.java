package repository.jdbc;


import connectionutil.ConnectorUtil;
import model.Skill;
import model.Status;
import repository.SkillRepository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class SkillRepositoryImpl implements SkillRepository {
    Logger log = Logger.getLogger(SkillRepositoryImpl.class.getName());

    SimpleFormatter formatter = new SimpleFormatter();
    FileHandler fh;

    {
        try {
            fh = new FileHandler("src/main/resources/log_skill_table.txt");
            fh.setFormatter(formatter);
            log.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String TABLE = "skills";

    private static String getTABLE() {
        return TABLE;
    }

    private List<List<String>> selectSkills(String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            statement = ConnectorUtil.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                List<String> line = new ArrayList<String>();
                line.add(String.valueOf(resultSet.getInt("id")));
                line.add(resultSet.getString("skill_name"));
                line.add(resultSet.getString("status"));
                list.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public Skill save(Skill skill) {
        logging("add skill to table-skills");
        String query = "INSERT INTO "+ getTABLE() + "(skill_name) VALUES ("
                + " '" + skill.getName() +
                "');";
        updateTable(query);
        return getSkillByName(skill);
    }


    @Override
    public Skill getById(Integer id) {
        logging("get");

        Skill skill = null;
        String query = "SELECT * FROM "
                + getTABLE()
                + " WHERE id = " + id.toString() + ";";
        List<List<String>> list = selectSkills(query);
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
        List<List<String>> list = selectSkills(query);
        List<Skill> listSkills = new ArrayList<Skill>();
        for (List<String> line : list) {
            listSkills.add(new Skill(Integer.parseInt(line.get(0)), line.get(1), Status.valueOf(line.get(2))));
        }
        return  listSkills;
    }


    @Override
    public Skill update(Skill skill) {
        logging("updater");

        String query =" UPDATE " + getTABLE()
                + " SET skill_name = '" + skill.getName()
                + "' WHERE id = " + skill.getId() + ";";

        updateTable(query);
        query =" UPDATE " + getTABLE()
                + " SET status = '" + skill.getStatus().toString()
                + "' WHERE id = " + skill.getId() + ";";
        updateTable(query);

        return getSkillByName(skill);
    }

    @Override
    public Skill delete(Skill skill) {
        logging("delete");
        String query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + skill.getId() + ";";
        updateTable(query);

        return getSkillByName(skill);
    }

    // for reading object from controller
    public int searchMaxIndex() {
        String query = "SELECT * FROM " + getTABLE() + ";";
        List<List<String>> list = selectSkills(query);
        return list.size();
    }

    public void logging(String str) {
        Date date = new Date();
        log.info(date.toString() + " " + this.getClass().getName() + " called " + str + "() method");
    }

    private Skill getSkillByName(Skill skill) {
        String query = "SELECT * FROM "
                + getTABLE()
                + " WHERE skill_name = '" + skill.getName() + "';"; // because value is unique
        List<List<String>> list = selectSkills(query);
        return new Skill(
                Integer.parseInt(list.get(0).get(0)),
                list.get(0).get(1),
                Status.valueOf(list.get(0).get(2))
        );
    }
}
