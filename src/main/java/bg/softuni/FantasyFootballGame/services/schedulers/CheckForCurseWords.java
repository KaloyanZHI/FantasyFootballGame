package bg.softuni.FantasyFootballGame.services.schedulers;

import bg.softuni.FantasyFootballGame.services.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class CheckForCurseWords {
    private final NewsService newsService;


    private final Logger logger = LoggerFactory.getLogger(CheckForCurseWords.class);

    public CheckForCurseWords(NewsService newsService) {
        this.newsService = newsService;
    }


    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 10)
    public void checkWords() throws IOException {
        boolean checkNews = this.newsService.checkForCurseWords();
        if (checkNews) {
            logger.info("News with inappropriate content found and deleted!");
        } else {
            logger.info("All news are clear");
        }
    }
}
