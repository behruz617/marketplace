package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor()
public class UserController {
    private final UserRepository userRepo;

    @GetMapping
    public List<User> all(){
        return userRepo.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userRepo.save(user);
    }

}
