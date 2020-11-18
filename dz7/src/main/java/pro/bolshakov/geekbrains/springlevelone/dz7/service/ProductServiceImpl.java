package pro.bolshakov.geekbrains.springlevelone.dz7.service;

import org.springframework.stereotype.Service;
import pro.bolshakov.geekbrains.springlevelone.dz7.dao.ProductDao;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDAO;

    public ProductServiceImpl(ProductDao productDAO) {
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
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {

        return productDAO.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productDAO.deleteById(id);
    };

    @Override
    public boolean existsById(Long id) {
        return productDAO.existsById(id);
    };

    @Override
    public void updateCols(Long id, String title, Double price) {
        productDAO.updateCols(id, title, price);
    }

    @Override
    public List<Product> findAllByPriceBetween(double priceFrom, double priceTo) {
        return productDAO.findAllByPriceBetween(priceFrom, priceTo);
    }
}
