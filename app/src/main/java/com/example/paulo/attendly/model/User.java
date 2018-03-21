package com.example.paulo.attendly.model;

/**
 * Created by JOAO on 21/03/2018.
 */

public class User {
    private String id;
    private int type;
    private String name;
    private String url_picture;
    private String course;
    private int[] subjects;

    public User(String id, int type, String name, String url_picture, String course, int[] subjects) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.url_picture = url_picture;
        this.course = course;
        this.subjects = subjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_picture() {
        return url_picture;
    }

    public void setUrl_picture(String url_picture) {
        this.url_picture = url_picture;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int[] getSubjects() {
        return subjects;
    }

    public void setSubjects(int[] subjects) {
        this.subjects = subjects;
    }


}
