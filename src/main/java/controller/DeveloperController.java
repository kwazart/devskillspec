package controller;

import connectionUtil.InputByUser;
import model.Developer;
import repository.DeveloperRepository;
import java.util.List;


public class DeveloperController implements Controller {
    private DeveloperRepository developerRepository = new DeveloperRepository();

    @Override
    public void create() {
        developerRepository.createDeveloper();
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
    public void update(int var) {
        developerRepository.update(var);
    }

    @Override
    public void delete() {
        developerRepository.delete();
    }
}
