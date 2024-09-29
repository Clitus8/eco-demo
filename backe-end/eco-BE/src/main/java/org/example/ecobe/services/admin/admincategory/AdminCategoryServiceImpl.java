package org.example.ecobe.services.admin.admincategory;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecobe.dto.CategoryDto;
import org.example.ecobe.model.Category;
import org.example.ecobe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        log.info("Creating a new category: {}", categoryDto.getName());
        return categoryRepository.save(
                Category.builder()
                        .name(categoryDto.getName())
                        .description(categoryDto.getDescription())
                        .build()
        );
    }

    @Override
    public List<Category> getAllCategory() {
        log.info("Fetching all categories.");
        return categoryRepository.findAll();
    }
}
