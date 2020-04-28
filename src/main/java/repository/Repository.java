package repository;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


public interface Repository<E> {
    Logger log = Logger.getLogger(DeveloperRepository.class.getName());

    void add(E e);

    E get(int x);

    List<E> getAll();

    void update(E e);

    void delete(E e);


    default void logging(String str) {
        Date date = new Date();
        log.info(date.toString() + " " + this.getClass().getName() + " called " + str + "() method");

    }
}
