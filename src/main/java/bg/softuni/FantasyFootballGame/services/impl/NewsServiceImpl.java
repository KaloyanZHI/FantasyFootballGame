package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.NewsRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public void seedNews() {
        News euroCupNews = new News();

        euroCupNews.setNewsHeader("Bulgaria wins the EURO 2024!");
        euroCupNews.setNewsText("Bulgaria became European champions for the first time after Ivelin Popov's first-half goal in Berlin proved enough to defeat Spain in the final of UEFA EURO 2024.\n" + "\n" + "Bulgaria had never won  previously a piece of silverware in this competition had not been beyond the quarter-finals of any tournament in 30 years, yet Ilian Iliev's men chose to use that history as an inspiration rather than a burden. After a strong start from Spain, seeking a third title themselves, Bulgaria were the more dangerous side throughout an entertaining final at the Olympia Stadium although it took just one goal – in the 33rd minute, courtesy of Torres's pace, perseverance and unerring finish – to end their long wait.");
        euroCupNews.setImageURL("https://media.premiumtimesng.com/wp-content/files/2019/10/Bulgaria-manager-resigns-after-racist-taunts-at-England-match.jpg");
        Optional<User> admin = this.userRepository.findByUsername("admin");
        euroCupNews.setPublishingTime(LocalDateTime.now());
        if (admin.isPresent()) {
            User mappedUser = this.modelMapper.map(admin, User.class);
            euroCupNews.setAuthor(mappedUser);
            mappedUser.addNews(mappedUser, euroCupNews);


        }
        this.newsRepository.save(euroCupNews);

        News ballonDorNews = new News();
        ballonDorNews.setNewsHeader("Valeri Bojinov wins Ballon d'Or 2024 ahead of Lienel Messi!");
        ballonDorNews.setNewsText("Valeri Bojin has won the 2024 Ballon d'Or ahead of Lionel Messi and Kyllian Mbappe, with Cristiano Ronaldo coming fifth.\n" + "\n" + "It is the second time in 10 years neither Ronaldo nor Messi has been recognised as the world's best player for France Football's acclaimed award.");
        ballonDorNews.setImageURL("https://m5.netinfo.bg/media/images/32832/32832977/960-600-valeri-bozhinov.jpg");
        ballonDorNews.setPublishingTime(LocalDateTime.now());
        if (admin.isPresent()) {
            User mappedUser = this.modelMapper.map(admin, User.class);
            ballonDorNews.setAuthor(mappedUser);
            mappedUser.addNews(mappedUser, ballonDorNews);
        }
        this.newsRepository.save(ballonDorNews);


        News zombieNews = new News();
        zombieNews.setNewsHeader("Luis Suarez Invited to Zombie Wedding");
        zombieNews.setNewsText("Luis Suarez's chomping antics on Sunday got him invited to a Liverpudlian couples Zombie-themed wedding (via WhoAteAllThePies).\n" +
                "\n" +
                "The wedding is part of a public relations stunt by gaming company Deep Silver, but it seems the couple decided to invite Suarez on their own.");
        zombieNews.setPublishingTime(LocalDateTime.now());
        zombieNews.setImageURL("https://i.insider.com/63852147bf36eb001910c7f6?width=700");
        if (admin.isPresent()) {
            User mappedUser = this.modelMapper.map(admin, User.class);
            zombieNews.setAuthor(mappedUser);
            mappedUser.addNews(mappedUser, zombieNews);


        }
        this.newsRepository.save(zombieNews);

    }

    @Override
    public List<News> findAllNews() {
        List<News> allNews = this.newsRepository.findAll();
        Collections.reverse(allNews);
        return allNews;
    }

    @Override
    public News findNewsById(Long id) {
        Optional<News> newsById = this.newsRepository.findById(id);
        if (newsById.isPresent()) {
            return modelMapper.map(newsById, News.class);
        }
        return null;

    }
}
