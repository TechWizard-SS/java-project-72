package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Url {

    @Setter
    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

}