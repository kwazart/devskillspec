package controller;


import connectionutil.InputByUserUtil;
import model.Specialty;
import repository.jdbc.SpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController implements Controller<Specialty> {
    private SpecialtyRepositoryImpl specialtyRepositoryImpl = new SpecialtyRepositoryImpl();

    @Override
    public Specialty create(Specialty specialty) {
        return specialtyRepositoryImpl.save(specialty);
    }

    @Override
    public Specialty read() {
        int id = InputByUserUtil.inputInt();
        if (id <= 0 || id > specialtyRepositoryImpl.searchMaxIndex()) return null;
        return specialtyRepositoryImpl.getById(id);
    }

    @Override
    public List<Specialty> readAll() {
        return  specialtyRepositoryImpl.getAll();
    }

    @Override
    public Specialty update(Specialty specialty) {
        return specialtyRepositoryImpl.update(specialty);
    }

    @Override
    public Specialty delete(Specialty specialty) {
        return specialtyRepositoryImpl.delete(specialty);
    }
}
