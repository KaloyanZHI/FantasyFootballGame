package bg.softuni.FantasyFootballGame.repositories;

import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.UserRolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(UserRolesEnum userRolesEnum);
}
