package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.Category;
import az.gov.marketplace.auth.dto.response.CategoryTreeResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryTreeMapper {

    public CategoryTreeResponse toResponse(Category category) {
            if (category==null)return null;

            List<CategoryTreeResponse>childResponse=category.getChildren()!=null
            ? category.getChildren().stream()
                    .map(this::toResponse)
                    .toList()
                    :new ArrayList<>();
            return CategoryTreeResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .children(childResponse)
                    .build();
    }

    public List<CategoryTreeResponse> toResponseList(List<Category> categories) {

        return categories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }

}
