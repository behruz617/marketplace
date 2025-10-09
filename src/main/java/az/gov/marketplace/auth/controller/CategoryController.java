package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.dto.response.CategoryTreeResponse;
import az.gov.marketplace.auth.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/tree")
    public List<CategoryTreeResponse> getCategoryTree() {
        return categoryService.getCategoryTree();
    }


}
