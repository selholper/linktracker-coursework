package ru.xpressed.javatemplatescoursework.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @NotEmpty(message = "Логин не может быть пустым!")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{2,14}$", message = "Логин должен состоять из латинских букв или цифр, " +
            "первый символ не должен быть цифрой")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым!")
    private String password;

    @Column(updatable = false, insertable = false)
    @NotEmpty(message = "Повторенный пароль не может быть пустым!")
    private String repeated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    public List<Order> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    public List<Link> links = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
