package org.example.ecobe.services.admin.admincategory;

import org.example.ecobe.dto.CategoryDto;
import org.example.ecobe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminCategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategory();
}
