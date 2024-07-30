package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.services.NewsService;
import bg.softuni.FantasyFootballGame.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/all")
    public ModelAndView getNews() throws IOException {
        List<News> newsList = this.newsService.findAllNews();
        ModelAndView modelAndView = new ModelAndView("all-news");
        modelAndView.addObject("allNews", newsList);

        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView consoleDetails(@PathVariable("id") Long id) {


        News news = newsService.findNewsById(id);

        ModelAndView modelAndView = new ModelAndView("sole-news");
        modelAndView.addObject("soleNews", news);

        return modelAndView;
    }
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleNewsNotFound(ObjectNotFoundException onfe){
        ModelAndView modelAndView = new ModelAndView("exceptions/news-not-found");
        modelAndView.addObject("newsId", onfe.getId());
        return modelAndView;
    }
}
