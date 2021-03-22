package com.example.videotophoto.classUnits;

public class Images {
    private String name;
    private String image;
    private String uri;

    public Images(String name, String image, String uri) {
        this.name = name;
        this.image = image;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
