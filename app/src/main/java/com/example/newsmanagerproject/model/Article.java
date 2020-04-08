package com.example.newsmanagerproject.model;

public class Article {
    private String Title;
    private String Subtitle;
    private String Abstract;
    private String Body;
    private Image image;

    public Article (String Title, String Subtitle, String Abstract, String Body, Image image)
    {
        this.Title = Title;
        this.Subtitle = Subtitle;
        this.Abstract = Abstract;
        this.Body = Body;
        this.image = image;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
