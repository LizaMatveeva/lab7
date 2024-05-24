package ifmo.lab.server.database;

import ifmo.lab.client.CurrentUserManager;
import ifmo.lab.server.models.*;
import ifmo.lab.server.sql.SQLConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class DataBaseProvider {
    private LinkedHashMap<Integer, Product> dataBase;
    private final String fileName;
    private LocalDateTime initializationTime;
    private final SQLConnection sqlConnection;
    private final CurrentUserManager userManager;

    public DataBaseProvider(CurrentUserManager userManager) {
        System.out.println("Подготовка...");
        this.sqlConnection = new SQLConnection();
        this.initializationTime = LocalDateTime.now();
        this.userManager = userManager;
        this.dataBase = loadDataBase();
        this.fileName = generateDataBaseFileName();
    }

    public DataBaseProvider(String fileName) {
        System.out.println("Подготовка базы данных из файла...");
        this.fileName = fileName == null ? generateDataBaseFileName() : fileName;
        this.dataBase = loadDatabaseFromFile();
        this.initializationTime = LocalDateTime.now();
        this.sqlConnection = new SQLConnection();
        this.userManager = null;
    }

    public LocalDateTime getInitializationTime() {
        return initializationTime;
    }

    public LinkedHashMap<Integer, Product> getDataBase() {
        return new LinkedHashMap<>(dataBase);
    }

    private Long generateNextId() {
        String query = "SELECT nextval('product_id_seq')";
        try (Connection connection = sqlConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addData(Integer key, Product product) {
        product.setMapKey(key);
        Long nextId = generateNextId();
        if (nextId == null) return false;

        int coordinatesId = ensureCoordinatesExist(product.getCoordinates());
        if (coordinatesId == -1) return false;

        Integer ownerId = null;
        if (product.getOwner() != null) {
            ownerId = ensurePersonExists(product.getOwner());
            if (ownerId == -1) return false;
        }

        String query = "INSERT INTO Product (id, mapKey, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner, createdBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, nextId);
            stmt.setInt(2, product.getMapKey());
            stmt.setString(3, product.getName());
            stmt.setInt(4, coordinatesId);
            stmt.setTimestamp(5, new Timestamp(product.getCreationDate().getTime()));
            stmt.setFloat(6, product.getPrice());
            stmt.setString(7, product.getPartNumber());
            stmt.setDouble(8, product.getManufactureCost());
            stmt.setString(9, product.getUnitOfMeasure().name());
            stmt.setObject(10, ownerId, java.sql.Types.INTEGER);
            stmt.setString(11, userManager.getUserName());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                product.setId(nextId);
                product.getCoordinates().setId(coordinatesId);
                if (ownerId != null) product.getOwner().setId(ownerId);
                product.setCreatedBy(userManager.getUserName());
                dataBase.put(key, product);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int ensurePersonExists(Person person) {
        String selectQuery = "SELECT id FROM Person WHERE name = ? AND weight = ? AND hairColor = ? AND nationality = ?";
        String insertQuery = "INSERT INTO Person (name, weight, hairColor, nationality, location) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, person.getName());
            selectStmt.setFloat(2, person.getWeight());
            selectStmt.setString(3, person.getHairColor().name());
            selectStmt.setString(4, person.getNationality().name());

            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                int locationId = ensureLocationExists(person.getLocation());
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, person.getName());
                    insertStmt.setFloat(2, person.getWeight());
                    insertStmt.setString(3, person.getHairColor().name());
                    insertStmt.setString(4, person.getNationality().name());
                    insertStmt.setInt(5, locationId);

                    ResultSet insertResultSet = insertStmt.executeQuery();
                    if (insertResultSet.next()) {
                        return insertResultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int ensureLocationExists(Location location) {
        String selectQuery = "SELECT id FROM Location WHERE x = ? AND y = ? AND z = ?";
        String insertQuery = "INSERT INTO Location (x, y, z) VALUES (?, ?, ?) RETURNING id";

        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, location.getX());
            selectStmt.setDouble(2, location.getY());
            selectStmt.setDouble(3, location.getZ());

            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, location.getX());
                    insertStmt.setDouble(2, location.getY());
                    insertStmt.setDouble(3, location.getZ());

                    ResultSet insertResultSet = insertStmt.executeQuery();
                    if (insertResultSet.next()) {
                        return insertResultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int ensureCoordinatesExist(Coordinates coordinates) {
        String selectQuery = "SELECT id FROM Coordinates WHERE x = ? AND y = ?";
        String insertQuery = "INSERT INTO Coordinates (x, y) VALUES (?, ?) RETURNING id";

        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setDouble(1, coordinates.getX());
            selectStmt.setInt(2, coordinates.getY());

            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setDouble(1, coordinates.getX());
                    insertStmt.setInt(2, coordinates.getY());
                    ResultSet insertResultSet = insertStmt.executeQuery();
                    if (insertResultSet.next()) {
                        return insertResultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Product updateData(long id, Product updatedProduct) {
        if (!canModifyProduct(id)) {
            System.out.println("Вы не можете редактировать данный продукт.");
            return null;
        }

        String query = "UPDATE Product SET name = ?, coordinates = ?, price = ?, partNumber = ?, manufactureCost = ?, unitOfMeasure = ?, owner = ? " +
                "WHERE id = ?";
        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, updatedProduct.getName());
            stmt.setInt(2, updatedProduct.getCoordinates().getId());
            stmt.setFloat(3, updatedProduct.getPrice());
            stmt.setString(4, updatedProduct.getPartNumber());
            stmt.setDouble(5, updatedProduct.getManufactureCost());
            stmt.setString(6, updatedProduct.getUnitOfMeasure().name());
            stmt.setInt(7, updatedProduct.getOwner() != null ? updatedProduct.getOwner().getId() : null);
            stmt.setLong(8, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                dataBase.put(getKeyById(id), updatedProduct);
                return updatedProduct;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removeDataByKey(Integer key) {
        Product product = dataBase.get(key);
        if (product == null || !canModifyProduct(product.getId())) {
            System.out.println("Вы не можете редактировать данный продукт.");
            return false;
        }

        String query = "DELETE FROM Product WHERE id = ?";
        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, product.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                dataBase.remove(key);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean clear() {
        String query = "DELETE FROM Product WHERE createdBy = ?";
        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userManager.getUserName());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return dataBase.entrySet().removeIf(entry -> entry.getValue().getCreatedBy().equals(userManager.getUserName()));
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LinkedHashMap<Integer, Product> loadDataBase() {
        LinkedHashMap<Integer, Product> loadedDatabase = new LinkedHashMap<>();
        String query = "SELECT p.id, p.mapKey, p.name, p.creationDate, p.price, p.partNumber, p.manufactureCost, p.unitOfMeasure, " +
                "c.id AS coordinatesId, c.x AS coordinatesX, c.y AS coordinatesY, " +
                "o.id AS ownerId, o.name AS ownerName, o.weight AS ownerWeight, o.hairColor, o.nationality, " +
                "l.id AS locationId, l.x AS locationX, l.y AS locationY, l.z AS locationZ, p.createdBy " +
                "FROM Product p " +
                "LEFT JOIN Coordinates c ON p.coordinates = c.id " +
                "LEFT JOIN Person o ON p.owner = o.id " +
                "LEFT JOIN Location l ON o.location = l.id";

        try (Connection connection = sqlConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            while (resultSet.next()) {
                Coordinates coordinates = new Coordinates(
                        resultSet.getDouble("coordinatesX"),
                        resultSet.getInt("coordinatesY")
                );
                coordinates.setId(resultSet.getInt("coordinatesId"));

                Location location = new Location(
                        resultSet.getInt("locationX"),
                        resultSet.getDouble("locationY"),
                        resultSet.getDouble("locationZ")
                );
                location.setId(resultSet.getInt("locationId"));

                Person owner = new Person(
                        resultSet.getString("ownerName"),
                        resultSet.getFloat("ownerWeight"),
                        Color.valueOf(resultSet.getString("hairColor")),
                        Country.valueOf(resultSet.getString("nationality")),
                        location
                );
                owner.setId(resultSet.getInt("ownerId"));

                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        coordinates,
                        new Date(resultSet.getTimestamp("creationDate").getTime()),
                        resultSet.getFloat("price"),
                        resultSet.getString("partNumber"),
                        resultSet.getDouble("manufactureCost"),
                        UnitOfMeasure.valueOf(resultSet.getString("unitOfMeasure")),
                        owner,
                        resultSet.getString("createdBy")
                );
                product.setMapKey(resultSet.getInt("mapKey"));

                loadedDatabase.put(resultSet.getInt("mapKey"), product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loadedDatabase;
    }

    private boolean canModifyProduct(long productId) {
        String query = "SELECT createdBy FROM Product WHERE id = ?";
        try (Connection connection = sqlConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, productId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String createdBy = resultSet.getString("createdBy");
                return createdBy.equals(userManager.getUserName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Integer getKeyById(long id) {
        for (Map.Entry<Integer, Product> entry : dataBase.entrySet()) {
            if (entry.getValue().getId() == id) {
                return entry.getKey();
            }
        }
        return null;
    }

    public LinkedHashMap<Integer, Product> loadDatabaseFromFile() {
        LinkedHashMap<Integer, Product> loadedDatabase = new LinkedHashMap<>();
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int firstCommaIndex = line.indexOf(',');
                if (firstCommaIndex == -1) continue;

                Integer key = Integer.parseInt(line.substring(0, firstCommaIndex));
                String productData = line.substring(firstCommaIndex + 1);
                Product product = Product.fromCSV(productData);

                loadedDatabase.put(key, product);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка чтения из файла: " + e.getMessage());
        }
        return loadedDatabase;
    }

    private static String generateDataBaseFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy:HH.mm");
        return "data_copy_" + now.format(formatter);
    }
    public void saveDatabase() {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("Key,Id,Name,Coordinates,CreationDate,Price,PartNumber,ManufactureCost,UnitOfMeasure,Owner\n");
            for (Map.Entry<Integer, Product> entry : dataBase.entrySet()) {
                Integer key = entry.getKey();
                Product product = entry.getValue();
                writer.write(key + "," + product.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }

}
