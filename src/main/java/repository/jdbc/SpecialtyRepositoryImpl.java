package repository.jdbc;


import connectionutil.ConnectorUtil;
import model.Skill;
import model.Specialty;
import model.Status;
import repository.SpecialtyRepository;

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

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    Logger log = Logger.getLogger(SpecialtyRepository.class.getName());

    SimpleFormatter formatter = new SimpleFormatter();
    FileHandler fh;

    {
        try {
            fh = new FileHandler("src/main/resources/log_specialty_table.txt");
            fh.setFormatter(formatter);
            log.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String TABLE = "specialties";

    private static String getTABLE() {
        return TABLE;
    }

    @Override
    public Specialty save(Specialty specialty) {
        logging("add specialty to table-skills");
        String query = "INSERT INTO "+ getTABLE() + "(specialties_name) VALUES ('"
                + specialty.getName() +
                "');";
        updateTable(query);

        return getSpecialtyByName(specialty);
    }


    @Override
    public Specialty getById(Integer id) {
        logging("get");
        String query = "SELECT * FROM "
                + getTABLE()
                + " WHERE id = " + id.toString() + ";";
        List<List<String>> list = selectSpecialties(query);
        Specialty specialty = new Specialty(
                Integer.parseInt(list.get(0).get(0)),
                list.get(0).get(1),
                Status.valueOf(list.get(0).get(2))
        );
        return specialty;
    }

    @Override
    public List<Specialty> getAll() {
        logging("getAll");
        String query = "SELECT * FROM " + getTABLE() + " ;";
        List<List<String>> list = selectSpecialties(query);
        List<Specialty> listSpecialties = new ArrayList<Specialty>();
        for (List<String> line : list) {
            listSpecialties.add(new Specialty(Integer.parseInt(line.get(0)), line.get(1), Status.valueOf(line.get(2))));
        }
        return  listSpecialties;
    }

    @Override
    public Specialty update(Specialty specialty) {
        logging("updater");


        String query =" UPDATE " + getTABLE()
                + " SET specialty_name = '" + specialty.getName()
                + "' WHERE id = " + specialty.getId() + ";";

        updateTable(query);
        query =" UPDATE " + getTABLE()
                + " SET status = '" + specialty.getStatus().toString()
                + "' WHERE id = " + specialty.getId() + ";";
        updateTable(query);

        return getSpecialtyByName(specialty);
    }

    @Override
    public Specialty delete(Specialty specialty) {
        logging("delete");
        String query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + specialty.getId() + ";";
        updateTable(query);

        return getSpecialtyByName(specialty);
    }

    // for reading object from controller
    public int searchMaxIndex() {
        String query = "SELECT * FROM " + getTABLE() + ";";
        List<List<String>> list = selectSpecialties(query);
        return list.size();
    }

    public void logging(String str) {
        Date date = new Date();
        log.info(date.toString() + " " + this.getClass().getName() + " called " + str + "() method");
    }

    private Specialty getSpecialtyByName(Specialty specialty) {
        String query = "SELECT * FROM "
                + getTABLE()
                + " WHERE specialties_name = '" + specialty.getName() + "';"; // because value is unique
        List<List<String>> list = selectSpecialties(query);
        return new Specialty(
                Integer.parseInt(list.get(0).get(0)),
                list.get(0).get(1),
                Status.valueOf(list.get(0).get(2))
        );
    }

    private List<List<String>> selectSpecialties(String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        List<List<String>> list = new ArrayList<List<String>>();
        try {
            statement = ConnectorUtil.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                List<String> line = new ArrayList<String>();
                line.add(String.valueOf(resultSet.getInt("id")));
                line.add(resultSet.getString("specialties_name"));
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

    public int searchMaxIndex(String table) {
        String query = "SELECT * FROM " + table + ";";
        List<List<String>> list = selectSpecialties(query);
        return list.size();
    }
}

