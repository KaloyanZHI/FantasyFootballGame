package bg.softuni.FantasyFootballGame.dto;

public class WriteNewsDTO {

    private String newsHeader;

    private String newsText;

    private String newsURL;

    public WriteNewsDTO(){

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
