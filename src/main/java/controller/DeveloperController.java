package controller;

import connectionUtil.InputByUser;
import model.Developer;
import repository.DeveloperRepository;
import java.util.List;


public class DeveloperController implements Controller<Developer> {
    private DeveloperRepository developerRepository = new DeveloperRepository();

    @Override
    public void create(Developer developer)  {
        developerRepository.createDeveloper(developer);
    }

    @Override
    public Developer read() {
        int id = InputByUser.inputInt();
        if (id <= 0 || id > developerRepository.searchMaxIndex()) return null;
        return developerRepository.get(id);
    }

    @Override
    public List<Developer> readAll() {
        return developerRepository.getAll();
    }

    @Override
    public void update(Developer developer) {
        developerRepository.update(developer);
    }

    @Override
    public void delete(Developer developer) {
        developerRepository.delete(developer);
    }
}
