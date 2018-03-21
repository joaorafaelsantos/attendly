package pt.attendly.attendly.model;

/**
 * Created by JOAO on 21/03/2018.
 */

public class Log {
    private int id;
    private String id_user;
    private String id_bluetooth;
    private int id_subject;
    private String date;
    private int day_week;
    private int presence;
    private int id_classroom;

    public Log(int id, String id_user, String id_bluetooth, int id_subject, String date, int day_week, int presence, int id_classroom) {
        this.id = id;
        this.id_user = id_user;
        this.id_bluetooth = id_bluetooth;
        this.id_subject = id_subject;
        this.date = date;
        this.day_week = day_week;
        this.presence = presence;
        this.id_classroom = id_classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_bluetooth() {
        return id_bluetooth;
    }

    public void setId_bluetooth(String id_bluetooth) {
        this.id_bluetooth = id_bluetooth;
    }

    public int getId_subject() {
        return id_subject;
    }

    public void setId_subject(int id_subject) {
        this.id_subject = id_subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay_week() {
        return day_week;
    }

    public void setDay_week(int day_week) {
        this.day_week = day_week;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public int getId_classroom() {
        return id_classroom;
    }

    public void setId_classroom(int id_classroom) {
        this.id_classroom = id_classroom;
    }
}
