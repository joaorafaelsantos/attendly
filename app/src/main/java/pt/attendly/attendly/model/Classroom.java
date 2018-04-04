package pt.attendly.attendly.model;

/**
 * Created by JOAO on 21/03/2018.
 */

public class Classroom {
    private int id;
    private String name;
    private String id_beacon;

    public Classroom() {
    }

    public Classroom(int id, String name, String id_beacon) {
        this.id = id;

        this.name = name;
        this.id_beacon = id_beacon;
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

    public String getId_beacon() {
        return id_beacon;
    }

    public void setId_beacon(String id_beacon) {
        this.id_beacon = id_beacon;
    }
}
