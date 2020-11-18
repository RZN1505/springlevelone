package pro.bolshakov.geekbrains.springlevelone.dz7.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getById(Long id);
    User auth(String name, String password);

    List<User> getAll();
}
