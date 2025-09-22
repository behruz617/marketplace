package az.gov.marketplace.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    //yalniz admin gore biler
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public String stats() {
        return "Only adin can see this";
    }

    //User ve admin her ikisi gore biler
    @GetMapping("/common")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String common() {
        return "Bunu butun login olmus user-ler gore biler";
    }


}
