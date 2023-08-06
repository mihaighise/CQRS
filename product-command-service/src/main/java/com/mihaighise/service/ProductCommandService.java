package com.mihaighise.service;


import com.mihaighise.dto.ProductEvent;
import com.mihaighise.entity.Product;
import com.mihaighise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public Product createProduct(Product product) {
        Product productEntity = productRepository.save(product);
        ProductEvent event = new ProductEvent("CreateEvent", productEntity);
        kafkaTemplate.send("product-event", event);
        return productEntity;
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).get();
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        Product productEntity = productRepository.save(existingProduct);
        ProductEvent event = new ProductEvent("UpdateEvent", productEntity);
        kafkaTemplate.send("product-event-topic", event);
        return productEntity;
    }
}
