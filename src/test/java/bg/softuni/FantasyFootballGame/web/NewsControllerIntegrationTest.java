package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.dto.WriteNewsDTO;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.NewsRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;


    private User admin;

    @BeforeEach
    void setUp() {
        newsRepository.deleteAll();
        userRepository.deleteAll();

        admin = new User();
        admin.setUsername("admin");
        admin.setPassword("password");
        userRepository.save(admin);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllNews() throws Exception {
        News news = new News();
        news.setNewsHeader("Sample News");
        news.setNewsText("This is a sample news text.");
        news.setPublishingTime(LocalDateTime.now());
        news.setAuthor(admin);
        newsRepository.save(news);

        mockMvc.perform(get("/news/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allNews"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetNewsById() throws Exception {
        News news = new News();
        news.setNewsHeader("Sample News");
        news.setNewsText("This is a sample news text.");
        news.setPublishingTime(LocalDateTime.now());
        news.setAuthor(admin);
        newsRepository.save(news);

        mockMvc.perform(get("/news/{id}", news.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("soleNews"))
                .andExpect(model().attribute("soleNews", org.hamcrest.Matchers.hasProperty("newsHeader", org.hamcrest.Matchers.is("Sample News"))))
                .andExpect(model().attribute("soleNews", org.hamcrest.Matchers.hasProperty("newsText", org.hamcrest.Matchers.is("This is a sample news text."))))
                .andExpect(model().attribute("soleNews", org.hamcrest.Matchers.hasProperty("author", org.hamcrest.Matchers.hasProperty("username", org.hamcrest.Matchers.is("admin")))));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteNews() throws Exception {
        News news = new News();
        news.setNewsHeader("Sample News");
        news.setNewsText("This is a sample news text.");
        news.setPublishingTime(LocalDateTime.now());
        news.setAuthor(admin);
        newsRepository.save(news);

        mockMvc.perform(delete("/news/delete/{id}", news.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/news/all"));


    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddNews() throws Exception {
        WriteNewsDTO writeNewsDTO = new WriteNewsDTO();
        writeNewsDTO.setNewsHeader("New News");
        writeNewsDTO.setNewsText("This is new news text.");
        writeNewsDTO.setNewsURL("http://example.com/image.jpg");

        mockMvc.perform(post("/news/write-news")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("newsHeader", writeNewsDTO.getNewsHeader())
                        .param("newsText", writeNewsDTO.getNewsText())
                        .param("newsURL", writeNewsDTO.getNewsURL())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/news/all"));

        mockMvc.perform(get("/news/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allNews"));
    }
}
