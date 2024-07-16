package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.entities.News;

import java.util.List;

public interface NewsService {
    void seedNews();

    List<News> findAllNews();

    News findNewsById(Long id);
}
