package pro.bolshakov.geekbrains.springlevelone.dz7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(Double startPrice, Double endPrice);
}
