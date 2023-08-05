package com.mihaighise.controller;

import com.mihaighise.entity.Product;
import com.mihaighise.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private ProductCommandService productCommandService;

    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productCommandService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productCommandService.updateProduct(id, product);
    }
}
