package bg.softuni.FantasyFootballGame.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WriteNewsDTO {
    @NotBlank(message = "Header cannot be empty!")
    private String newsHeader;
    @NotBlank(message = "Text cannot be empty!")
    private String newsText;
    @NotBlank(message = "URL cannot be empty!")
    private String newsURL;

    public WriteNewsDTO() {

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

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }
}
