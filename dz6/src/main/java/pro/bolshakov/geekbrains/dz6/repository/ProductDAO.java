package pro.bolshakov.geekbrains.dz6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.bolshakov.geekbrains.dz6.domain.Product;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(Double startPrice, Double endPrice);
}
