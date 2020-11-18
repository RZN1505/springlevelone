package pro.bolshakov.geekbrains.dz2.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.bolshakov.geekbrains.dz2.domain.Product;
import pro.bolshakov.geekbrains.dz2.repository.ProductJpaDAO;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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
                .collect(toList());
    }

    @Transactional
    public Product save(Product product){
         productJpaDAO.save(product);
         return product;
    }

    @Transactional(readOnly = true)
    public List<Product> getByPriceMinMax(String value) {
        List<Product> pr = productJpaDAO.findAll();
       List<Product> list;
        List<Product> listMax = pr.stream()
                .collect(groupingBy(Product::getPrice, TreeMap::new, toList()))
                .lastEntry()
                .getValue();
       List<Product> listMin = pr.stream()
                .collect(groupingBy(Product::getPrice, TreeMap::new, toList()))
                .firstEntry()
                .getValue();
       switch (value) {
            case "MAXMIN":
                list = Stream.of(listMax, listMin)
                        .flatMap(Collection::stream)
                        .collect(toList());
                break;
            case "MIN":
                list = listMin;
                break;
            case "MAX":
                list = listMax;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + value);
        }
        return list;
        
    }

}
