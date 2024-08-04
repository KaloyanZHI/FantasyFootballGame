package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.dto.WriteNewsDTO;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.services.NewsService;
import bg.softuni.FantasyFootballGame.services.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    @ModelAttribute("writeNewsDTO")
    public WriteNewsDTO writeNewsDTO() {
        return new WriteNewsDTO();
    }

    @GetMapping("/all")
    public ModelAndView getNews() throws IOException {
        List<News> newsList = this.newsService.findAllNews();
        ModelAndView modelAndView = new ModelAndView("all-news");
        modelAndView.addObject("writeNewsDTO", new WriteNewsDTO());
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
    public ModelAndView handleNewsNotFound(ObjectNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("exceptions/news-not-found");
        modelAndView.addObject("newsId", onfe.getId());
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteNews(@PathVariable("id") Long id) {

        newsService.deleteNews(id);

        return new ModelAndView("redirect:/news/all");
    }

    @GetMapping("/write-news")
    public ModelAndView goToWriteNews() {
        return new ModelAndView("write-news");
    }



    @PostMapping("/write-news")
    public String addNews(@ModelAttribute @Valid WriteNewsDTO data,
                          BindingResult bindingResult,
                          Principal principal,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.writeNewsDTO", bindingResult);
            redirectAttributes.addFlashAttribute("writeNewsDTO", data);
            return "redirect:/news/write-news";
        }

        this.newsService.createNews(data, principal);
        return "redirect:/news/all";
    }


}
