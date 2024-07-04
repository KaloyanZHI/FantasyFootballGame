package bg.softuni.FantasyFootballGame.repositories;

import bg.softuni.FantasyFootballGame.entities.RealTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealTeamRepository extends JpaRepository<RealTeam, Long> {
    Optional<RealTeam> findByTeamName(String teamName);
}
