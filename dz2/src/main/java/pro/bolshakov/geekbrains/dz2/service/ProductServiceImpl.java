package pro.bolshakov.geekbrains.dz2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.bolshakov.geekbrains.dz2.domain.Product;
import pro.bolshakov.geekbrains.dz2.repository.ProductJpaDAO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {
    @Autowired
    private final ProductJpaDAO productJpaDAO;

    public ProductServiceImpl(ProductJpaDAO productJpaDAO) {

        this.productJpaDAO = productJpaDAO;
    }

    @Transactional(readOnly = true)
    public Product getById(Long id){
        return productJpaDAO.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll(){
        List<Product> products = productJpaDAO.findAll();
        products.sort(Comparator.comparingLong(Product::getId));
        return products;
    }

    @Transactional(readOnly = true)
    public List<Product> getByPrice(Double start, Double end){
        return productJpaDAO.findAll().stream()
                .filter(product-> product.getPrice() >= start && product.getPrice() <= end)
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    @Transactional
    public Product save(Product product){
         productJpaDAO.save(product);
         return product;
    }


}
