package controller;

import connectionutil.ConnectorUtil;

import java.util.List;

public interface Controller<E> {

    E create(E e);

    E read();

    List<E> readAll();

    E update(E e);

    E delete(E e);
}
