package pro.bolshakov.geekbrains.springlevelone.dz7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findFirstByName(String name);
}
