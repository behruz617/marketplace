package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.ProductSpecification;
import az.gov.marketplace.auth.dto.response.SpecificationResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpecificationMapper {

    public SpecificationResponse toResponse(ProductSpecification spec) {
        if (spec == null) return null;
        SpecificationResponse response = new SpecificationResponse();
        response.setName(spec.getSpecification().getName());
        response.setValue(spec.getValue());

        return response;
    }

    public List<SpecificationResponse>toResponseList(List<ProductSpecification>spec){
        if (spec==null||spec.isEmpty())return List.of();
        return spec.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
