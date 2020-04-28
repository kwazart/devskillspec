package controller;

import connectionUtil.InputByUser;
import model.Skill;
import repository.SkillRepository;
import java.util.List;


public class SkillController implements Controller<Skill>{
    private SkillRepository skillRepository = new SkillRepository();

    @Override
    public void create(Skill skill) {
        skillRepository.createSkill(skill);
    }

    @Override
    public Skill read() {
        int id = InputByUser.inputInt();
        if (id <= 0 || id > skillRepository.searchMaxIndex()) return null;
        return skillRepository.get(id);

    }

    @Override
    public List<Skill> readAll() {
        return skillRepository.getAll();
    }

    @Override
    public void update(Skill skill) {
        skillRepository.update(skill);
    }

    @Override
    public void delete(Skill skill) {
        skillRepository.delete(skill);
    }
}
