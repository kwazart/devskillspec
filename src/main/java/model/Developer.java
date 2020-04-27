package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private Specialty specialty;
    private List<Skill> skills;
    private Status status;


    @Override
    public String toString() {
        return id +
                "," + firstName  +
                "," + lastName +
                "," + specialty.getName() +
                "," + listSkillToString(skills) +
                "," + status;
    }

    public String listSkillToString(List<Skill> list) {
        StringBuffer line = new StringBuffer("");
        for (Skill skill : list) {
            line.append(skill.getName());
            line.append(",");
        }
        return line.toString();
    }

}
