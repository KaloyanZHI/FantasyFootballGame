package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.services.NewsService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
}
