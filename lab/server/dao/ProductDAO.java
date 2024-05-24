package ifmo.lab.server.dao;

import ifmo.lab.server.models.Product;
import java.util.List;
import java.util.Map;

/**
 * Interface for product data access operations.
 */
public interface ProductDAO {
    /**
     * Returns information about the database, such as type, initialization time, and item count.
     *
     * @return the string containing information about the database
     */
    String info();

    /**
     * Retrieves a product by its ID.
     *
     * @param id the product ID
     * @return the product if found, null otherwise
     */
    Product getProductById(long id);

    /**
     * Retrieves all products in the database.
     *
     * @return a list of all products
     */
    List<Product> getListAllProducts();

    /**
     * Retrieves all products in the database.
     *
     * @return a map of all products
     */
    Map<Integer, Product> getAllProducts();

    /**
     * Adds a new product with a specific key to the database.
     *
     * @param key the key associated with the product
     * @param product the product to add
     * @return a success message if added, otherwise a failure message
     */
    String addProduct(Integer key, Product product);

    /**
     * Updates an existing product by ID.
     *
     * @param id the ID of the product to update
     * @param updatedProduct the new product details to apply
     * @return the updated product, null if no product was found for update
     */
    Product updateProduct(long id, Product updatedProduct);

    /**
     * Removes a product by its key.
     *
     * @param key the key of the product to remove
     * @return true if the product was removed successfully, false otherwise
     */
    boolean removeProductByKey(Integer key);

    /**
     * Clears all products from the database.
     *
     * @return true if the database was successfully cleared, false otherwise
     */
    boolean clear();

    /**
     * Saves the current state of the database to persistent storage.
     */
    void save();

    /**
     * Removes products from the database that are "lower" than the specified product.
     * The definition of "lower" should be implemented in a way that makes sense for the specific application.
     *
     * @param product the reference product for comparison
     * @return true if any products were removed, false otherwise
     */
    boolean removeLower(Product product);

    /**
     * Filters products by name containing a specified substring.
     *
     * @param name the substring to filter by
     * @return a list of products whose names contain the specified substring
     */
    List<Product> filterProductsByName(String name);

    /**
     * Filters products by price, returning those with a price less than the specified value.
     *
     * @param price the price to filter by
     * @return a list of products with a price less than the specified value
     */
    List<Product> filterProductsByPriceLessThan(float price);

    /**
     * Retrieves a list of products sorted by the part number in descending order.
     *
     * @return a list of part numbers in descending order
     */
    List<String> getProductsSortedByPartNumberDescending();
}
