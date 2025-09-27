package az.gov.marketplace.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Tag(name="Admin",description = "Endpoints for admin-specify actions and statistics")
public class AdminController {


    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public String stats() {
        return "Only admin can see this";
    }


    @GetMapping("/common")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String common() {
        return "Bunu butun login olmus user-ler gore biler";
    }


}
