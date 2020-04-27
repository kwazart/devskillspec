package repository;


import connectionUtil.Connector;
import connectionUtil.InputByUser;
import model.Specialty;
import model.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SpecialtyRepository implements Repository<Specialty> {
    Logger log = Logger.getLogger(DeveloperRepository.class.getName());

    private static final String TABLE = "specialties";

    public static String getTABLE() {
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
    public void update(int variant) {
        logging("updater");
        int id = InputByUser.inputInt();
        if (id <= 0 || id > searchMaxIndex()) return;
        String query = null;
        if (variant == 1) {
            System.out.println("Updating procedure. New data.");
            System.out.println("Enter new specialty name: ");
            query =" UPDATE " + getTABLE() + " SET specialty_name = '" + InputByUser.inputData() + "' WHERE id = " + id + ";";
        } else if (variant == 2) {
            System.out.println("Updating procedure. New data.");
            Specialty specialty = get(id);
            if (specialty.getStatus().equals(Status.ACTIVE)) {
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

    public static Specialty stringToSpecialty(String line) {
        String[] elements = line.split(",");
        return new Specialty(Integer.parseInt(elements[0]), elements[1], Status.valueOf(elements[2]));
    }

    public Specialty createSpecialty() {
        System.out.print("Enter specialty name: ");
        Specialty specialty = new Specialty(0, InputByUser.inputData(), Status.ACTIVE);
        add(specialty);
        return specialty;
    }

    public int searchMaxIndex() {
        return Connector.searchMaxIndex("specialties");
    }
}
