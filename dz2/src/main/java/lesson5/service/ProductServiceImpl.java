package lesson5.service;

import lesson5.domain.Product;
import lesson5.repository.ProductJpaDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl {

    private ProductJpaDAO productJpaDAO;

    public ProductServiceImpl(ProductJpaDAO productJpaDAO) {
        this.productJpaDAO = productJpaDAO;
    }

    @Transactional
    public void saveAndSet(Product product){
        productJpaDAO.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Integer id){
        return productJpaDAO.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return productJpaDAO.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllByPriceBetween(Float min, Float max){
        return productJpaDAO.findAllByPriceBetween(min, max);
    }

    @Transactional(readOnly = true)
    public Page<Product> findPage(int page, int size){return productJpaDAO.findAll(PageRequest.of(page, size));
    }

}
