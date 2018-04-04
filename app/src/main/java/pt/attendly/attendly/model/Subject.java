package pt.attendly.attendly.model;

import java.util.ArrayList;

/**
 * Created by JOAO on 21/03/2018.
 */

public class Subject {
    private int id;
    private String name;
    private String course;
    private ArrayList<Integer> schedules;
    private ArrayList<Integer> teachers;

    public Subject() {
    }

    public Subject(int id, String name, String course, ArrayList<Integer> schedules, ArrayList<Integer> teachers) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.schedules = schedules;
        this.teachers = teachers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public ArrayList<Integer> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Integer> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<Integer> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Integer> teachers) {
        this.teachers = teachers;
    }
}
