package pro.bolshakov.geekbrains.springlevelone.dz7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Order;

public interface OrderDao extends JpaRepository<Order, Long> {
}
