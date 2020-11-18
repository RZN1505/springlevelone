package pro.bolshakov.geekbrains.springlevelone.dz7.service;

import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    void deleteById(Long id);

    void updateCols(Long id, String title, Double price);

    boolean existsById(Long id);

    Product save(Product product);

    List<Product> findAllByPriceBetween(double priceFrom, double priceTo);
}
