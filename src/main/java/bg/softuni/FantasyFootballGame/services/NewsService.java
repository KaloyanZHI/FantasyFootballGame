package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.dto.WriteNewsDTO;
import bg.softuni.FantasyFootballGame.entities.News;

import java.security.Principal;
import java.util.List;

public interface NewsService {
    void seedNews();

    List<News> findAllNews();

    News findNewsById(Long id);

    void deleteNews(Long id);

    void createNews(WriteNewsDTO dto, Principal principal);
}
