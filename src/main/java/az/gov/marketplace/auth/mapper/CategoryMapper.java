package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.Category;
import az.gov.marketplace.auth.dto.response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        if (category == null) return null;
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getParent() != null ? category.getParent().getName() : null,
                category.getIsActive()
        );

    }
}
