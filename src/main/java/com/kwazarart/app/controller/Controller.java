package java.com.kwazarart.app.controller;

import java.util.List;

public interface Controller<E> {

    void create();

    E read();

    List<E> readAll();

    void update(int x);

    void delete();
}
