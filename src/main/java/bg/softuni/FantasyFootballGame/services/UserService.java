package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.dto.UserLoginDTO;
import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;

public interface UserService {
    public boolean register(UserRegisterDTO data);

     void seedUsers();
}



