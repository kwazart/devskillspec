package controller;

import java.util.List;

public interface Controller<E> {

    void create(E e);

    E read();

    List<E> readAll();

    void update(E e);

    void delete(E e);
}
