package bg.softuni.FantasyFootballGame.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "header")
    private String newsHeader;

    @Column(name = "news_text", columnDefinition = "TEXT")
    private String newsText;

    @ManyToOne
    private User author;

    @Column(name = "date_and_time")
    private LocalDateTime publishingTime;

    public LocalDateTime getPublishingTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return publishingTime.withNano(0);
    }

    public void setPublishingTime(LocalDateTime publishingTime) {
        this.publishingTime = publishingTime;
    }

    @Column(name = "image_url")
    private String imageURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsHeader() {
        return newsHeader;
    }

    public void setNewsHeader(String newsHeader) {
        this.newsHeader = newsHeader;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
