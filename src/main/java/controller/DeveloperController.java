package controller;

import connectionutil.InputByUserUtil;
import model.Developer;
import repository.jdbc.DeveloperRepositoryImpl;

import java.util.List;


public class DeveloperController implements Controller<Developer> {
    private DeveloperRepositoryImpl developerRepositoryImpl = new DeveloperRepositoryImpl();

    @Override
    public Developer create(Developer developer)  {
        return developerRepositoryImpl.save(developer);
    }

    @Override
    public Developer read() {
        int id = InputByUserUtil.inputInt();
        if (id <= 0 || id > developerRepositoryImpl.searchMaxIndex()) return null;
        return developerRepositoryImpl.getById(id);
    }

    @Override
    public List<Developer> readAll() {
        return developerRepositoryImpl.getAll();
    }

    @Override
    public Developer update(Developer developer) {
        return developerRepositoryImpl.update(developer);
    }

    @Override
    public Developer delete(Developer developer) {
        return developerRepositoryImpl.delete(developer);
    }
}
