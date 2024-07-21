package bg.softuni.FantasyFootballGame.repositories;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FantasyTeamRepository extends JpaRepository<FantasyTeam, Long> {
    Optional<FantasyTeam> findByTeamName(String fantasyTeam);
}
