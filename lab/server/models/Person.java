package ifmo.lab.server.models;


import java.util.Arrays;

public class Person {
    private int id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private float weight; //Значение поля должно быть больше 0
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null
    public Person (String name, float weight, Color hairColor, Country nationality, Location location){
        this.name = name;
        this.weight = weight;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Person() {
    }

    public String toCSV() {
        return "\"" + name + "\"," +
                weight + "," +
                (hairColor != null ? hairColor.name() : "") + "," +
                (nationality != null ? nationality.name() : "") + "," +
                (location != null ? location.toCSV() : "");
    }

    public static Person fromCSV(String csv) {
        String[] parts = csv.split(",");
        Person person = new Person();
        person.name = parts[0].replaceAll("\"", "");
        person.weight = Float.parseFloat(parts[1]);
        person.hairColor = !parts[2].isEmpty() ? Color.valueOf(parts[2]) : null;
        person.nationality = !parts[3].isEmpty() ? Country.valueOf(parts[3]) : null;
        person.location = parts.length > 4 ? Location.fromCSV(String.join(",", Arrays.copyOfRange(parts, 4, parts.length))) : null;
        return person;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "\n\tname='" + name + '\'' +
                ",\n\tweight=" + weight +
                ",\n\thairColor='" + hairColor + '\'' +
                ",\n\tnationality='" + nationality + '\'' +
                ",\n\tlocation=" + location +
                "\n}";
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}