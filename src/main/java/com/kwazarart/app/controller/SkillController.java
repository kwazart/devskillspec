package java.com.kwazarart.app.controller;

import java.com.kwazarart.app.inputoutput.InputByUser;
import java.com.kwazarart.app.model.Skill;
import java.com.kwazarart.app.repository.SkillRepository;

import java.util.List;


public class SkillController implements Controller{
    private SkillRepository skillRepository = new SkillRepository();

    @Override
    public void create() {
        skillRepository.createSkill();
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
    public void update(int var) {
        skillRepository.update(var);
    }

    @Override
    public void delete() {
        skillRepository.delete();
    }
}
