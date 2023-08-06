package com.mihaighise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihaighise.dto.ProductEvent;
import com.mihaighise.entity.Product;
import com.mihaighise.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductQueryService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @KafkaListener(topics = "product-event", groupId = "product-event-group")
    public void processProductEvents(ProductEvent productEvent) {
        log.info(productEvent.toString());
        Product product = productEvent.getProduct();
        if (productEvent.getEventType().equals("CreateEvent")) {
            productRepository.save(product);
        } else if (productEvent.getEventType().equals("UpdateEvent")) {
            Product existingProduct = productRepository.findById(product.getId()).get();
            existingProduct.setDescription(product.getDescription());
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            productRepository.save(existingProduct);
        }
    }
}
