package az.gov.marketplace.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductImageResponse {
    private Long id;
    private String fileName;
    private String fileUrl;

}
