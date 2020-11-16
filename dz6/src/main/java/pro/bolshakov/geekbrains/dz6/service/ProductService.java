package pro.bolshakov.geekbrains.dz6.service;

import pro.bolshakov.geekbrains.dz6.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getById(Long id);

    Product save(Product product);

    List<Product> getByPrice(double priceFrom, double priceTo);
}
