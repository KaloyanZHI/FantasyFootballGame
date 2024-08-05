package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.dto.WriteNewsDTO;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.NewsRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Mock
    private Principal principal;

    @Test
    void testFindAllNews() {
        News news = new News();
        when(newsRepository.findAll()).thenReturn(Collections.singletonList(news));

        List<News> result = newsService.findAllNews();
        assertEquals(1, result.size());
        assertEquals(news, result.get(0));
    }

    @Test
    void testFindNewsById() {
        News news = new News();
        Optional<News> optionalNews = Optional.of(news);

        when(newsRepository.findById(1L)).thenReturn(optionalNews);
        when(modelMapper.map(any(News.class), eq(News.class))).thenReturn(news);

        News result = newsService.findNewsById(1L);
        assertEquals(news, result);
    }

    @Test
    void testFindNewsById_NotFound() {
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> newsService.findNewsById(1L));
    }

    @Test
    void testDeleteNews() {
        News news = new News();
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));

        newsService.deleteNews(1L);
        verify(newsRepository, times(1)).delete(news);
    }

    @Test
    void testDeleteNews_NotFound() {
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> newsService.deleteNews(1L));
    }

    @Test
    void testCreateNews() {
        WriteNewsDTO dto = new WriteNewsDTO();
        dto.setNewsHeader("Header");
        dto.setNewsText("Text");
        dto.setNewsURL("URL");

        User user = new User();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("username");

        newsService.createNews(dto, principal);
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    void testCreateNews_UserNotFound() {
        WriteNewsDTO dto = new WriteNewsDTO();
        dto.setNewsHeader("Header");
        dto.setNewsText("Text");
        dto.setNewsURL("URL");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(principal.getName()).thenReturn("username");

        assertThrows(ObjectNotFoundException.class, () -> newsService.createNews(dto, principal));
    }
    @Test
    void testSeedNews() {
        User admin = new User();
        admin.setUsername("admin");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(modelMapper.map(any(), eq(User.class))).thenReturn(admin);

        newsService.seedNews();

        verify(newsRepository, times(3)).save(any(News.class));
        verify(userRepository, times(1)).findByUsername("admin");
        verify(modelMapper, times(3)).map(any(), eq(User.class));
    }

}
