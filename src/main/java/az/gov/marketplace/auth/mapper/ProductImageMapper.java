package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.ProductImage;
import az.gov.marketplace.auth.dto.response.ProductImageResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ProductImageMapper {


    public List<ProductImageResponse> toResponse(List<ProductImage> images) {
        return images.stream()
                .map(image -> ProductImageResponse.builder()
                        .id(image.getId())
                        .fileName(image.getFileName())
                        .fileUrl(image.getFileUrl())
                        .build())
                .collect(Collectors.toList());


    }
}
