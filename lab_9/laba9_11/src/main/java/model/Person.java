package model;

/**
 * @author nafis
 * @since 14.05.2022
 */

public class Person {
    private int id;

    private String fio;
    private String position;
    private String phone;
    private int age;

    public Person(String fio, String position, String phone, int age) {
        this.fio = fio;
        this.position = position;
        this.phone = phone;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }
}
