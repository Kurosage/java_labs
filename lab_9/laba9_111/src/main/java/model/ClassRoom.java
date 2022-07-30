package model;

/**
 * @author nafis
 * @since 14.05.2022
 */

public class ClassRoom {
    private int id;

    private int building;
    private int number;
    private Double surface;
    private String name;

    private int responsiblePerson;

    public ClassRoom(int building, int number, Double surface, String name, int responsiblePerson) {
        this.building = building;
        this.number = number;
        this.surface = surface;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
    }

    public int getId() {
        return id;
    }

    public int getBuilding() {
        return building;
    }

    public int getNumber() {
        return number;
    }

    public Double getSurface() {
        return surface;
    }

    public String getName() {
        return name;
    }

    public int getResponsiblePerson() {
        return responsiblePerson;
    }
}
