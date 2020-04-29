package controller;

import connectionutil.InputByUserUtil;
import model.Skill;
import repository.jdbc.SkillRepositoryImpl;
import java.util.List;


public class SkillController implements Controller<Skill>{
    private SkillRepositoryImpl skillRepositoryImpl = new SkillRepositoryImpl();

    @Override
    public Skill create(Skill skill) {
        return skillRepositoryImpl.save(skill);
    }

    @Override
    public Skill read() {
        int id = InputByUserUtil.inputInt();
        if (id <= 0 || id > skillRepositoryImpl.searchMaxIndex()) return null;
        return skillRepositoryImpl.getById(id);

    }

    @Override
    public List<Skill> readAll() {
        return skillRepositoryImpl.getAll();
    }

    @Override
    public Skill update(Skill skill) {
        return skillRepositoryImpl.update(skill);
    }

    @Override
    public Skill delete(Skill skill) {
        return skillRepositoryImpl.delete(skill);
    }
}
