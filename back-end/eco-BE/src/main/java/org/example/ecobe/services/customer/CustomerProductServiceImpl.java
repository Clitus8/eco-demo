package org.example.ecobe.services.customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecobe.dto.ProductDetailDto;
import org.example.ecobe.dto.ProductDto;
import org.example.ecobe.model.Product;
import org.example.ecobe.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerProductServiceImpl implements CustomerProductService {
@Autowired
private ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByName(String name) {
        log.info("Fetching products by name containing '{}'", name);
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public ProductDetailDto getProductDetailById(Long productId) {
        log.info("Fetching product details for product with ID '{}'", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {

            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setProductDto(optionalProduct.get().getDto());

            return productDetailDto;
        }
        log.error("Product with ID '{}' not found", productId);
        return null;
    }
}
