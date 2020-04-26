package java.com.kwazarart.app.repository;

import java.util.Date;
import java.util.List;

import java.util.logging.Logger;


public interface Repository<E> {
    Logger log = Logger.getLogger(DeveloperRepository.class.getName());

    void add(E e);

    E get(int x);

    List<E> getAll();

    void update(int x);

    void delete();

    default void printByIndex(E e) {
        System.out.println(e);
    }

    default void printAll(List<E> list) {
        for (E e : list) {
            System.out.println(e);
        }
        System.out.println("\n");
    }

    default void notFound() {
        System.out.println("Object doesn't found");
    }

    default void logging(String str) {
        Date date = new Date();
        log.info(date.toString() + " " + this.getClass().getName() + " called " + str + "() method");

    }
}
