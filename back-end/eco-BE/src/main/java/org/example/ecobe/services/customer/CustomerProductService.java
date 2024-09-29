package org.example.ecobe.services.customer;

import org.example.ecobe.dto.ProductDetailDto;
import org.example.ecobe.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByName(String name);

    ProductDetailDto getProductDetailById(Long productId);
}
