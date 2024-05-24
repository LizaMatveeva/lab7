package ifmo.lab.server.models;
/**
 * The type Coordinates.
 */
public class Coordinates{
    private int id;
    private Double x; //Значение поля должно быть больше -441, Поле не может быть null
    private Integer y; //Поле не может быть null

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public Coordinates (Double x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Coordinates(int id, Double x, Integer y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Coordinates.
     */
    public Coordinates() {
    }
    public String toCSV() {
        return x + "," + y;
    }

    public static Coordinates fromCSV(String csv) {
        String[] parts = csv.split(",");
        Coordinates coordinates = new Coordinates();
        coordinates.x = Double.parseDouble(parts[0]);
        coordinates.y = Integer.parseInt(parts[1]);
        return coordinates;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public Double getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Integer y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "\n\tx=" + x +
                ",\n\ty=" + y +
                "\n}";
    }

}
