package az.gov.marketplace.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CategoryTreeResponse {

    private Long id;
    private String name;
    private List<CategoryTreeResponse> children;

}
