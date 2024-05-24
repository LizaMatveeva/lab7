package ifmo.lab.server.dao;

import ifmo.lab.client.CurrentUserManager;
import ifmo.lab.server.database.DataBaseProvider;
import ifmo.lab.server.models.Product;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for interacting with the product database using LinkedHashMap.
 */
public class ProductDAOImpl implements ProductDAO {
    private final DataBaseProvider source;

    /**
     * Instantiates a new Product DAO.
     *
     * @param fileName the file name for the database file
     */
    public ProductDAOImpl(String fileName) {
        this.source = new DataBaseProvider(fileName);
    }

    public ProductDAOImpl(CurrentUserManager userManager) {
        this.source = new DataBaseProvider(userManager);
    }

    @Override
    public String info() {
        return String.format("Data about database:\nType: %s\nInitialization time: %s\nNumber of items: %d",
                LinkedHashMap.class.getSimpleName(),
                source.getInitializationTime().toString(),
                source.getDataBase().size());
    }

    @Override
    public Product getProductById(long id) {
        return source.getDataBase().values().stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> getListAllProducts() {
        return new ArrayList<>(source.getDataBase().values());
    }

    @Override
    public Map<Integer, Product> getAllProducts() {
        return source.getDataBase();
    }

    @Override
    public String addProduct(Integer key, Product product) {
        if (source.addData(key, product)) {
            return "Product added successfully";
        }
        return "Failed to add product";
    }

    @Override
    public Product updateProduct(long id, Product updatedProduct) {
        return source.updateData(id, updatedProduct);
    }

    @Override
    public boolean removeProductByKey(Integer key) {
        return source.removeDataByKey(key);
    }

    @Override
    public boolean clear() {
        return source.clear();
    }

    @Override
    public void save() {
        source.saveDatabase();
    }

    @Override
    public boolean removeLower(Product product) {
        List<Integer> keysToRemove = source.getDataBase().entrySet().stream()
                .filter(e -> e.getValue().compareTo(product) < 0)
                .map(Map.Entry::getKey)
                .toList();
        keysToRemove.forEach(source::removeDataByKey);
        return !keysToRemove.isEmpty();
    }

    @Override
    public List<Product> filterProductsByName(String name) {
        return source.getDataBase().values().stream()
                .filter(product -> product.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> filterProductsByPriceLessThan(float price) {
        return source.getDataBase().values().stream()
                .filter(product -> product.getPrice() < price)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getProductsSortedByPartNumberDescending() {
        return source.getDataBase().values().stream()
                .sorted(Comparator.comparing(Product::getPartNumber).reversed())
                .map(Product::getPartNumber)
                .collect(Collectors.toList());
    }
}
