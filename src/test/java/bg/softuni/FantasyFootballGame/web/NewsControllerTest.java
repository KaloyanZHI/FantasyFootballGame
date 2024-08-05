package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.dto.WriteNewsDTO;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.services.NewsService;
import bg.softuni.FantasyFootballGame.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NewsController.class)
public class NewsControllerTest {

    @MockBean
    private NewsService newsService;

    @InjectMocks
    private NewsController newsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @WithMockUser
    public void testGetNews() throws Exception {
        News news = new News();
        news.setNewsHeader("Test News");
        List<News> newsList = Collections.singletonList(news);

        when(newsService.findAllNews()).thenReturn(newsList);

        this.mockMvc.perform(get("/news/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allNews"))
                .andExpect(view().name("all-news"));
    }

    @Test
    @WithMockUser
    public void testConsoleDetails() throws Exception {
        News news = new News();
        news.setId(1L);
        news.setNewsHeader("Test News");

        when(newsService.findNewsById(1L)).thenReturn(news);

        this.mockMvc.perform(get("/news/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("soleNews"))
                .andExpect(view().name("sole-news"));
    }

    @Test
    @WithMockUser
    public void testHandleNewsNotFound() throws Exception {
        when(newsService.findNewsById(1L)).thenThrow(new ObjectNotFoundException("News not found!", 1L));

        this.mockMvc.perform(get("/news/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("exceptions/news-not-found"));
    }

    @Test
    @WithMockUser
    public void testDeleteNews() throws Exception {
        this.mockMvc.perform(delete("/news/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/news/all"));
    }

    @Test
    @WithMockUser
    public void testGoToWriteNews() throws Exception {
        this.mockMvc.perform(get("/news/write-news"))
                .andExpect(status().isOk())
                .andExpect(view().name("write-news"));
    }

    @Test
    @WithMockUser
    public void testAddNews_WithBindingErrors() throws Exception {
        WriteNewsDTO writeNewsDTO = new WriteNewsDTO();
        writeNewsDTO.setNewsHeader("Test Header");
        writeNewsDTO.setNewsText("Test Text");

        this.mockMvc.perform(post("/news/write-news")
                        .flashAttr("writeNewsDTO", writeNewsDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/news/write-news"));
    }

    @Test
    @WithMockUser
    public void testAddNews_Success() throws Exception {
        WriteNewsDTO writeNewsDTO = new WriteNewsDTO();
        writeNewsDTO.setNewsHeader("Test Header");
        writeNewsDTO.setNewsText("Test Text");

        this.mockMvc.perform(post("/news/write-news")
                        .param("newsHeader", "Test Header")
                        .param("newsText", "Test Text"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/news/all"));
    }
}
