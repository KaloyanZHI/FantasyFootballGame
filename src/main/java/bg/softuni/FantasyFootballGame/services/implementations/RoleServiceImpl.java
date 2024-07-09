package bg.softuni.FantasyFootballGame.services.implementations;

import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.UserRoles;
import bg.softuni.FantasyFootballGame.repositories.RoleRepository;
import bg.softuni.FantasyFootballGame.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRoles() {
        Role userRole = new Role();
        userRole.setName(UserRoles.USER);
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setName(UserRoles.ADMIN);
        roleRepository.save(adminRole);
    }
}
