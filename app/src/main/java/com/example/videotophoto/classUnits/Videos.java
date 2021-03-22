package com.example.videotophoto.classUnits;

public class Videos {
    private String image;
    private String name;
    private String uri;

    public Videos(String image, String name, String uri) {
        this.image = image;
        this.name = name;
        this.uri = uri;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
