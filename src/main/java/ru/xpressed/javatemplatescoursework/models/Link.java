package ru.xpressed.javatemplatescoursework.models;

import lombok.Data;
import ru.xpressed.javatemplatescoursework.models.enums.LinkType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Ссылка не может быть пустой!")
    @Pattern(
        regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$",
        message = "Неверный формат ссылки!"
    )
    private String link;

    @Enumerated(EnumType.STRING)
    private LinkType type;

    @Size(max = 262144, message = "Размер изображения не должен превышать 512x512 пикселей!")
    private byte[] imageData;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private User user;
}
