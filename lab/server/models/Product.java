package ifmo.lab.server.models;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Product implements Comparable<Product> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private int mapKey;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private String partNumber; //Длина строки должна быть не меньше 25, Длина строки не должна быть больше 48, Поле не может быть null
    private Double manufactureCost; //Поле не может быть null
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private Person owner; //Поле может быть null
    private String createdBy; // Новый атрибут для хранения имени пользователя, создавшего продукт

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Product(long id, String name, Coordinates coordinates, Date creationDate, float price, String partNumber,
                   Double manufactureCost, UnitOfMeasure unitOfMeasure, Person owner, String createdBy) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate != null ? creationDate : new Date();
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        this.createdBy = createdBy;
    }

    public Product(String name, Coordinates coordinates, float price, String partNumber, Double manufactureCost,
                   UnitOfMeasure unitOfMeasure, Person owner, String createdBy) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        this.createdBy = createdBy;
    }

    public Product() {
        this.creationDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Double getManufactureCost() {
        return manufactureCost;
    }

    public void setManufactureCost(Double manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getMapKey() {
        return mapKey;
    }

    public void setMapKey(int mapKey) {
        this.mapKey = mapKey;
    }

    @Override
    public int compareTo(Product other) {
        return Float.compare(this.price, other.price);
    }

    public String toCSV() {
        return id + "," +
                "\"" + name + "\"," +
                coordinates.toCSV() + "," +
                "\"" + dateFormat.format(creationDate) + "\"," +
                price + "," +
                "\"" + partNumber + "\"," +
                manufactureCost + "," +
                (unitOfMeasure != null ? unitOfMeasure.name() : "") + "," +
                (owner != null ? owner.toCSV() : "") + "," +
                "\"" + createdBy + "\"";
    }

    public static Product fromCSV(String csv) {
        String[] parts = csv.split(",");
        Product product = new Product();
        try {
            product.id = Long.parseLong(parts[0]);
            product.name = parts[1].replaceAll("\"", "");
            product.coordinates = Coordinates.fromCSV(parts[2] + "," + parts[3]);
            product.creationDate = dateFormat.parse(parts[4].replaceAll("\"", ""));
            product.price = Float.parseFloat(parts[5]);
            product.partNumber = parts[6].replaceAll("\"", "");
            product.manufactureCost = Double.parseDouble(parts[7]);
            product.unitOfMeasure = !parts[8].isEmpty() ? UnitOfMeasure.valueOf(parts[8]) : null;
            product.owner = parts.length > 9 ? Person.fromCSV(String.join(",", Arrays.copyOfRange(parts, 9, parts.length))) : null;
            product.createdBy = parts[parts.length - 1].replaceAll("\"", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public String toString() {
        return "Product{" +
                "\n\tid=" + id +
                ",\n\tname='" + name + '\'' +
                ",\n\tcoordinates=" + coordinates +
                ",\n\tprice=" + price +
                ",\n\tpartNumber='" + partNumber + '\'' +
                ",\n\tmanufactureCost=" + manufactureCost +
                ",\n\tunitOfMeasure=" + unitOfMeasure +
                ",\n\towner=" + owner +
                ",\n\tcreation date=" + creationDate +
                ",\n\tcreatedBy='" + createdBy + '\'' +
                "\n}";
    }

    public boolean validate() {
        if (id < 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (price < 0) return false;
        if (partNumber.length() < 25 || partNumber.length() > 48) return false;
        if (manufactureCost == null) return false;
        return true;
    }
}
