package lesson5.repository;

import lesson5.domain.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> findAll();
    Product findById(Long id);
    void save(Product product);
    void update(Product product);
    void delete(Product product);
}
