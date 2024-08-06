package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.repositories.NewsRepository;
import bg.softuni.FantasyFootballGame.services.NewsService;
import bg.softuni.FantasyFootballGame.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NewsRepository newsRepository;

    @Test
    void testShowAllNews() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/news/all")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("all-news"));

    }
    @Test
    void testShowSoleNews() throws Exception {
        News news = this.newsRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/news/{id}", news.getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("sole-news"));

    }

}
