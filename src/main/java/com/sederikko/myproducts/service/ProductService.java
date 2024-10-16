package com.sederikko.myproducts.service;

import com.sederikko.myproducts.model.Product;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final String FILE_PATH = "products.txt";
    private int nextId = 1; // Variable to track the next product ID

    public ProductService() {
        loadProductsFromFile();
    }

    private void loadProductsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // Ensure the format includes ID
                    Product product = new Product(parts[1], new BigDecimal(parts[2]), parts[3]); // Adjust index for name and price
                    product.setId(Integer.parseInt(parts[0])); // Set the ID from the file
                    products.add(product);
                    nextId = Math.max(nextId, product.getId() + 1); // Update nextId for new products
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProductPrice(Integer id, BigDecimal newPrice) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setPrice(newPrice);
                saveProductsToFile(); // Save changes to file immediately after updating
                break;
            }
        }
    }

    public void saveProduct(Product product) {
        product.setId(nextId++); // Set ID before adding
        products.add(product);
        saveProductsToFile(); // Save changes to file
    }

    private void saveProductsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                bw.write(product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getDescription());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts(int page, int size) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String name, int page, int size) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparing(Product::getId).reversed())
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public int getTotalProducts() {
        return products.size();
    }

    public int getTotalSearchResults(String name) {
        return (int) products.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .count();
    }

    public Product getProductById(Integer id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null); // Return null if not found
    }

    public void deleteProduct(Integer id) {
        // Remove the product from the list
        products.removeIf(product -> product.getId().equals(id));
        saveProductsToFile(); // Save the updated list to file after deletion
    }
}