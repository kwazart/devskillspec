package repository.jdbc;

import connectionutil.ConnectorUtil;
import model.*;
import repository.DeveloperRepository;

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

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final String TABLE = "developers";
    private Logger log = Logger.getLogger(DeveloperRepositoryImpl.class.getName());

    private static String getTABLE() {
        return TABLE;
    }

    SimpleFormatter formatter = new SimpleFormatter();
    FileHandler fh;

    {
        try {
            fh = new FileHandler("src/main/resources/log_developer_table.txt");
            fh.setFormatter(formatter);
            log.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Developer save(Developer developer) {
        logging("add developer to table-developers");
        String query = "INSERT INTO "+ getTABLE() + "(firstName, lastName, specialty, skills) VALUES ("
                +  " '"
                + developer.getFirstName() + "', '" +
                developer.getLastName() + "', '" +
                developer.getSpecialty().getName() + "', '" +
                developer.listSkillToString(developer.getSkills()) + "', '" +
                "');";
        updateTable(query);

        return getDeveloperByFullNameAndSpecialty(developer);
    }

    @Override
    public Developer getById(Integer id) {
        logging("get");
        Developer developer = null;
        String query = "SELECT * FROM "
                + getTABLE()
                + " WHERE id = " + id.toString() + ";";
        List<List<String>> list = selectDevelopers(query);
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
        String query = "SELECT * FROM " + getTABLE() + " ;";
        List<List<String>> list = selectDevelopers(query);
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
    public Developer update(Developer developer) {
        logging("updater");

        String query = "UPDATE " + getTABLE()
                + " SET firstName = '" + developer.getFirstName()
                + "' WHERE id = +" + developer.getId() +  ";";
        updateTable(query);
        query = "UPDATE " + getTABLE()
                + " SET lastName = '" + developer.getLastName()
                + "' WHERE id = +" + developer.getId() +  ";";
        updateTable(query);
        query = "UPDATE " + getTABLE()
                + " SET specialty = '" + developer.getSpecialty().getName()
                + "' WHERE id = +" + developer.getId() +  ";";
        updateTable(query);
        query = "UPDATE " + getTABLE()
                + " SET skills = '" + developer.listSkillToString(developer.getSkills())
                + "' WHERE id = +" + developer.getId() +  ";";
        updateTable(query);
        query = "UPDATE " + getTABLE()
                + " SET status = '" + developer.getStatus().toString()
                + "' WHERE id = +" + developer.getId() +  ";";
        updateTable(query);
        return getDeveloperByFullNameAndSpecialty(developer);
    }

    @Override
    public Developer delete(Developer developer) {
        logging("delete");
        String query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + developer.getId() + ";";
        updateTable(query);
        return getDeveloperByFullNameAndSpecialty(developer);
    }

    // for reading object from controller
    public int searchMaxIndex() {
        String query = "SELECT * FROM " + getTABLE() + ";";
        List<List<String>> list = selectDevelopers(query);
        return list.size();
    }


    public void logging(String str) {
        Date date = new Date();
        log.info(date.toString() + " " + this.getClass().getName() + " called " + str + "() method");
    }

    private Developer getDeveloperByFullNameAndSpecialty(Developer developer) {
        String query = "SELECT * FROM "
                + getTABLE()
                + " WHERE firstName = '" + developer.getFirstName()
                + "' AND lastName = '" + developer.getLastName()
                + "' AND specialty = '" + developer.getSpecialty().getName()
                + "';";
        List<List<String>> list = selectDevelopers(query);
        String [] arraySkills = list.get(0).get(4).split(",");
        List<Skill> listSkills = new ArrayList<Skill>();
        for (String skill : arraySkills) {
            listSkills.add(new Skill(0, skill, Status.ACTIVE));
        }
        return new Developer(
                Integer.parseInt(list.get(0).get(0)),
                list.get(0).get(1),
                list.get(0).get(2),
                new Specialty(0, list.get(0).get(3), Status.ACTIVE),
                listSkills,
                Status.valueOf(list.get(0).get(5))
        );
    }

    private List<List<String>> selectDevelopers(String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            statement = ConnectorUtil.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                List<String> line = new ArrayList<String>();
                line.add(String.valueOf((resultSet.getInt("id"))));
                line.add(resultSet.getString("firstName"));
                line.add(resultSet.getString("lastName"));
                line.add(resultSet.getString("specialty"));
                line.add(resultSet.getString("skills"));
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

}
