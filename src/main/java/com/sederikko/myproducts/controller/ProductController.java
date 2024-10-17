package com.sederikko.myproducts.controller;

import com.sederikko.myproducts.model.Product;
import com.sederikko.myproducts.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private static final int PAGE_SIZE = 10; // Define the number of products per page

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        List<Product> products = productService.getAllProducts(page, PAGE_SIZE);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) productService.getTotalProducts() / PAGE_SIZE));
        return "index";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam() BigDecimal price, @RequestParam(required = false) BigDecimal salePrice, @RequestParam(required = false) String description) {
        Product product = new Product(name, price, salePrice, description);
        productService.saveProduct(product);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam String name, Model model, @RequestParam(defaultValue = "0") int page) {
        List<Product> products = productService.searchProducts(name, page, PAGE_SIZE);
        model.addAttribute("products", products);
        model.addAttribute("searchName", name);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) productService.getTotalSearchResults(name) / PAGE_SIZE));
        return "index"; // Reuse the index template for displaying results
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id); // Fetch the product by ID
        model.addAttribute("product", product); // Add product to the model for the view
        return "update"; // Return the view name for the update form
    }

    @PostMapping("/priceUpdate/{id}")
    public String updateProductPrice(@PathVariable Integer id, @RequestParam BigDecimal price) {
        productService.updateProductPrice(id, price); // Update the product price
        return "redirect:/"; // Redirect to the home page
    }

    @PostMapping("/salePriceUpdate/{id}")
    public String updateProductSalePrice(@PathVariable Integer id, @RequestParam BigDecimal salePrice) {
        productService.updateProductSalePrice(id, salePrice); // Update the product price
        return "redirect:/"; // Redirect to the home page
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam Integer id, Model model) {
        productService.deleteProduct(id); // Call service to delete the product by ID
        return "redirect:/"; // Redirect back to the home page after deletion
    }
}
