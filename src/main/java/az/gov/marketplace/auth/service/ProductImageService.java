package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.entity.Product;
import az.gov.marketplace.auth.domain.entity.ProductImage;
import az.gov.marketplace.auth.repo.ProductImageRepository;
import az.gov.marketplace.auth.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final S3Service s3Service;
    private final ProductRepository productRepo;
    private final ProductImageRepository productImageRepo;

    @Transactional
    public List<ProductImage> uploadImages(Long productId, List<MultipartFile> files) throws IOException {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductImage> imageList = new ArrayList<>();

        for (MultipartFile file : files) {
           String fileUrl= s3Service.uploadFile(file);

            ProductImage image = ProductImage.builder()
                    .fileName(file.getOriginalFilename())
                    .fileUrl(fileUrl)
                    .product(product)
                    .build();
            imageList.add(image);
        }

        List<ProductImage> saved = productImageRepo.saveAll(imageList);
        product.getImages().addAll(saved);

        return saved;
    }

}
