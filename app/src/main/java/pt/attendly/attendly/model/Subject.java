package pt.attendly.attendly.model;

/**
 * Created by JOAO on 21/03/2018.
 */

public class Subject {
    private int id;
    private String name;
    private String course;
    private int[] schedules;
    private int[] teachers;

    public Subject() {
    }

    public Subject(int id, String name, String course, int[] schedules, int[] teachers) {
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

    public int[] getSchedules() {
        return schedules;
    }

    public void setSchedules(int[] schedules) {
        this.schedules = schedules;
    }

    public int[] getTeachers() {
        return teachers;
    }

    public void setTeachers(int[] teachers) {
        this.teachers = teachers;
    }
}
