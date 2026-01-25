package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Url {
    private Long id;
    private String name;
    private Instant createdAt;

    public Url(String name) {
        this.name = name;
    }
}
