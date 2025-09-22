package az.gov.marketplace.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @PostMapping("/add-product")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public String addProduct(){
        return "Seller and Admin can add product";
    }


}
