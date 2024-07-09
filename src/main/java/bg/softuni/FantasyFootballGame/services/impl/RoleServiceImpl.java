package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.UserRolesEnum;
import bg.softuni.FantasyFootballGame.repositories.RoleRepository;
import bg.softuni.FantasyFootballGame.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRoles() {
        List<Role> roleList = new ArrayList<>();
        Arrays.stream(UserRolesEnum.values())
                .forEach(userRole -> {
                    Role role = new Role();
                    role.setName(userRole);
                    roleList.add(role);
                });
        this.roleRepository.saveAll(roleList);
    }
}
