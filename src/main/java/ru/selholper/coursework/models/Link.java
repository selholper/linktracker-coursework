package ru.selholper.coursework.models;


import lombok.Getter;
import lombok.Setter;
import ru.selholper.coursework.models.enums.LinkType;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Ссылка не может быть пустой!")
    @Pattern(
        regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$",
        message = "Неверный формат ссылки!"
    )
    private String url;

    @Enumerated(EnumType.STRING)
    private LinkType type;

    private byte[] imageData;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private User user;
}
