package java.com.kwazarart.app.controller;

import java.com.kwazarart.app.inputoutput.InputByUser;
import java.com.kwazarart.app.model.Developer;
import java.com.kwazarart.app.repository.DeveloperRepository;

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
