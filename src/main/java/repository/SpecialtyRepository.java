package repository;


import connectionUtil.Connector;
import model.Specialty;
import model.Status;
import repository.jdbc.JdbcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SpecialtyRepository implements Repository<Specialty>, JdbcRepository {
    Logger log = Logger.getLogger(DeveloperRepository.class.getName());

    private static final String TABLE = "specialties";

    private static String getTABLE() {
        return TABLE;
    }

    @Override
    public void add(Specialty specialty) {
        logging("add specialty to table-specialties");
        String line = "INSERT INTO "+ getTABLE() + " VALUES (" +
                (searchMaxIndex() + 1) +
                ", '" + specialty.getName() +
                "', 'ACTIVE');";
        Connector.addOrUpdate(line);
    }


    @Override
    public Specialty get(int id) {
        logging("get");
        String query = "SELECT * FROM "
                + getTABLE()
                + " WHERE id = " + id + ";";
        List<List<String>> list = Connector.select(query);
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
        List<List<String>> list = Connector.select(query);
        List<Specialty> listSpecialties = new ArrayList<Specialty>();
        for (List<String> line : list) {
            listSpecialties.add(new Specialty(Integer.parseInt(line.get(0)), line.get(1), Status.valueOf(line.get(2))));
        }
        return  listSpecialties;
    }

    @Override
    public void update(Specialty specialty) {
        logging("updater");


        String query =" UPDATE " + getTABLE()
                + " SET specialty_name = '" + specialty.getName()
                + "' WHERE id = " + specialty.getId() + ";";

        Connector.addOrUpdate(query);
        query =" UPDATE " + getTABLE()
                + " SET status = '" + specialty.getStatus().toString()
                + "' WHERE id = " + specialty.getId() + ";";
        Connector.addOrUpdate(query);
    }

    @Override
    public void delete(Specialty specialty) {
        logging("delete");
        String query = " UPDATE " + getTABLE() + " SET status = 'DELETED' WHERE id = " + specialty.getId() + ";";
        Connector.addOrUpdate(query);
    }

    public Specialty createSpecialty(Specialty specialty) {
        add(specialty);
        return specialty;
    }

    public int searchMaxIndex() {
        return Connector.searchMaxIndex("specialties");
    }
}
