package repository;

import connectionUtil.Connector;
import model.*;
import repository.jdbc.JdbcRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class DeveloperRepository implements Repository<Developer>, JdbcRepository {
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
                + getTABLE()
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
        String query = "SELECT * FROM " + getTABLE() + " ;";
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
    public void update(Developer developer) {
        logging("updater");

        String query = "UPDATE " + getTABLE()
                + " SET firstName = '" + developer.getFirstName()
                + "' WHERE id = +" + developer.getId() +  ";";
        Connector.addOrUpdate(query);
        query = "UPDATE " + getTABLE()
                + " SET lastName = '" + developer.getLastName()
                + "' WHERE id = +" + developer.getId() +  ";";
        Connector.addOrUpdate(query);
        query = "UPDATE " + getTABLE()
                + " SET specialty = '" + developer.getSpecialty().getName()
                + "' WHERE id = +" + developer.getId() +  ";";
        Connector.addOrUpdate(query);
        query = "UPDATE " + getTABLE()
                + " SET skills = '" + developer.listSkillToString(developer.getSkills())
                + "' WHERE id = +" + developer.getId() +  ";";
        Connector.addOrUpdate(query);
        query = "UPDATE " + getTABLE()
                + " SET status = '" + developer.getStatus().toString()
                + "' WHERE id = +" + developer.getId() +  ";";
        Connector.addOrUpdate(query);
    }

    @Override
    public void delete(Developer developer) {
        logging("delete");
        String query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + developer.getId() + ";";
        Connector.addOrUpdate(query);
    }


    public Developer createDeveloper(Developer developer){
        add(developer);
        return developer;
    }

    public int searchMaxIndex() {
        return Connector.searchMaxIndex("developers");
    }
}
