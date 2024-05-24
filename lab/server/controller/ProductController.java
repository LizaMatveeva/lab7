package ifmo.lab.server.controller;

import ifmo.lab.server.models.Product;
import java.util.List;
import java.util.Map;

/**
 * Interface for managing product operations.
 */
public interface ProductController {

    /**
     * Provides information about the collection such as type, initialization time, and number of elements.
     *
     * @return the info string
     */
    String info();

    /**
     * Displays all products in the collection.
     *
     * @return the string representation of all products
     */
    String show();

    /**
     * Retrieves all products in the database.
     *
     * @return a map of all products
     */
    Map<Integer, Product> getAllProducts();

    /**
     * Adds a new product with a given key.
     *
     * @param key the key for the new product
     * @param product the product to add
     * @return a message indicating success or failure
     */
    String insert(Integer key, Product product);

    /**
     * Updates an existing product by its ID.
     *
     * @param id the id of the product to update
     * @param product the new product data
     * @return the updated product
     */
    Product update(long id, Product product);

    /**
     * Removes a product by its key.
     *
     * @param key the key of the product to remove
     * @return a message indicating if the removal was successful
     */
    String removeByKey(Integer key);

    /**
     * Clears all products from the collection.
     *
     * @return a message indicating if the clear operation was successful
     */
    String clear();

    /**
     * Saves the current state of the collection to the storage.
     */
    void save();

    /**
     * Filters products by name containing a specified substring.
     *
     * @param name the substring to filter by
     * @return a list of products that match the criteria
     */
    List<Product> filterContainsName(String name);

    /**
     * Filters products by price less than the specified value.
     *
     * @param price the maximum price
     * @return a list of products that match the criteria
     */
    List<Product> filterLessThanPrice(float price);

    /**
     * Displays part numbers of all products in descending order.
     *
     * @return a list of part numbers in descending order
     */
    List<String> printFieldDescendingPartNumber();

    boolean removeGreaterKey(int key);

    boolean removeLower(Product product);
    String getCurrentUser();
}
