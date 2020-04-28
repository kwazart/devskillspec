package controller;


import connectionUtil.InputByUser;
import model.Specialty;
import repository.SpecialtyRepository;
import java.util.List;

public class SpecialtyController implements Controller<Specialty> {
    private SpecialtyRepository specialtyRepository = new SpecialtyRepository();

    @Override
    public void create(Specialty specialty) {
        specialtyRepository.createSpecialty(specialty);
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
    public void update(Specialty specialty) {
        specialtyRepository.update(specialty);
    }

    @Override
    public void delete(Specialty specialty) {
        specialtyRepository.delete(specialty);
    }
}
