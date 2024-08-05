package bg.softuni.FantasyFootballGame.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRolesEnum name;

    public Role(){

    }

    public Long getId() {
        return id;
    }



    public UserRolesEnum getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role setName(UserRolesEnum name) {
        this.name = name;
        return this;
    }
}
