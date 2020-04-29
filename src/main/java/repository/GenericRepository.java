package repository;

import connectionutil.ConnectorUtil;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;

public interface GenericRepository<E,ID> {

    E save(E e);

    E getById(ID x);

    List<E> getAll();

    E update(E e);

    E delete(E e);

    default void updateTable(String line) {
        Statement statement = null;
        try {
            statement = ConnectorUtil.getConnection().createStatement();
            statement.executeUpdate(line);
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Object exists.");
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
        }
    }
}
