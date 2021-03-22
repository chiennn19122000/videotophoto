package com.example.videotophoto.classUnits;

public class Folder {
    private String name;
    private String uri;
    private int cout;

    public Folder(String name, String uri, int cout) {
        this.name = name;
        this.uri = uri;
        this.cout = cout;
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

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }
}
