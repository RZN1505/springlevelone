package pro.bolshakov.geekbrains.dz6.service;

import org.springframework.stereotype.Service;
import pro.bolshakov.geekbrains.dz6.domain.Product;
import pro.bolshakov.geekbrains.dz6.repository.ProductDAO;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
        init();
    }

    private void init(){

        productDAO.saveAll(Arrays.asList(
                new Product(null, "Cheese", 450.75),
                new Product(null, "Milk", 50.75),
                new Product(null, "Chocolate", 123.75),
                new Product(null, "Eggs", 75.0)
                ));

    }

    @Override
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productDAO.save(product);
    }

    @Override
    public List<Product> getByPrice(double priceFrom, double priceTo) {
        return productDAO.findAllByPriceBetween(priceFrom, priceTo);
    }
}
