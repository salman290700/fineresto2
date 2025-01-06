package org.babagroup.services.implementations;

import jakarta.inject.Inject;
import org.babagroup.models.Category;
import org.babagroup.repository.CategoryRepository;
import org.babagroup.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryImpl implements CategoryService {
    @Inject
    CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Category category) {
        return categoryRepository.findByName(category.getName()).orElse(categoryRepository.save(category));
    }
}
