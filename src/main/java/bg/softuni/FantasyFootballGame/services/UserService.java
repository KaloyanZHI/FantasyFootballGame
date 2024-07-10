package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.dto.UserLoginDTO;
import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.User;

public interface UserService {
    public boolean register(UserRegisterDTO data);

     void seedUsers();


}



