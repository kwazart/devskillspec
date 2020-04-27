package controller;


import connectionUtil.InputByUser;
import model.Specialty;
import repository.SpecialtyRepository;
import java.util.List;

public class SpecialtyController implements Controller {
    SpecialtyRepository specialtyRepository = new SpecialtyRepository();

    @Override
    public void create() {
        specialtyRepository.createSpecialty();
    }

    @Override
    public Specialty read() {
        int id = InputByUser.inputInt();
        if (id <= 0 || id > specialtyRepository.searchMaxIndex()) return null;
        return specialtyRepository.get(id);
    }

    @Override
    public List<Specialty> readAll() {
        return  specialtyRepository.getAll();
    }

    @Override
    public void update(int var) {
        specialtyRepository.update(var);
    }

    @Override
    public void delete() {
        specialtyRepository.delete();
    }
}
