package java.com.kwazarart.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Specialty {

    private int id;
    private String name;
    private Status status;

    @Override
    public String toString() {
        return name ;
    }
}
