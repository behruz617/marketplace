package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.entity.Category;
import az.gov.marketplace.auth.dto.response.CategoryTreeResponse;
import az.gov.marketplace.auth.mapper.CategoryTreeMapper;
import az.gov.marketplace.auth.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepo;
    private final CategoryTreeMapper categoryTreeMapper;
    public List<CategoryTreeResponse>getCategoryTree(){
        List<Category>rootCategories=categoryRepo.findByParentIsNull();
        return categoryTreeMapper.toResponseList(rootCategories);
    }
}
