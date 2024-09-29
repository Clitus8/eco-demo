package org.example.ecobe.services.admin.adminproduct;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecobe.dto.ProductDto;
import org.example.ecobe.model.Category;
import org.example.ecobe.model.Product;
import org.example.ecobe.repositories.CategoryRepository;
import org.example.ecobe.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductDto addProduct(ProductDto productDto) throws Exception {
        log.info("Adding a new product: {}", productDto.getName());
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        Product product = new Product();

        // Set properties using set() methods
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg() != null ? productDto.getImg().getBytes() : null);
        product.setCategory(category);

        // Save the product to the repository
        Product savedProduct = productRepository.save(product);

        // Log the saved product ID
        log.info("Product added with ID: {}", savedProduct.getId());

        // Return the DTO of the saved product
        return savedProduct.getDto();
    }

    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products.");
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByName(String name) {
        log.info("Fetching all products by name: {}", name);
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long productId) {
        log.info("Fetching product with ID: {}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getDto).orElse(null);
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        log.info("Updating product with ID: {}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());

        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product product = optionalProduct.get().toBuilder()
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .description(productDto.getDescription())
                    .category(optionalCategory.get())
                    .img(productDto.getByteImg() != null ? productDto.getByteImg() : optionalProduct.get().getImg())
                    .build();
            Product savedProduct = productRepository.save(product);
            return savedProduct.getDto();
        }
        return null;
    }
}
