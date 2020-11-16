package pro.bolshakov.geekbrains.springlevelone.dz7.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.bolshakov.geekbrains.springlevelone.dz7.dao.ProductDao;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;
import pro.bolshakov.geekbrains.springlevelone.dz7.dto.EntityNotFoundResponse;
import pro.bolshakov.geekbrains.springlevelone.dz7.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products-rest")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public List<Product> getAll(){
        return productDao.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        checkById(id);
        return productDao.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Product addProduct(@RequestBody Product product){
        System.out.println(product);
        return productDao.save(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@RequestBody Product product, @PathVariable(name = "productId") Long id){
        product.setId(id);
        System.out.println(product);
        return productDao.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        checkById(id);
        productDao.deleteById(id);
    }

    private void checkById(@PathVariable Long id) {
        if(!productDao.existsById(id)){
            throw new EntityNotFoundException("Product", id, "Product not found");
        }
    }

    @PostMapping("/filter")
    @ResponseBody
    public List<Product> filterByTitle(@RequestBody filterEntity filterEntity){
        System.out.println(filterEntity.id);
        List<Product> products = productDao.findAll().stream()
               .filter(product-> ( filterEntity.title.equals("") ? true : product.getTitle().contains(filterEntity.title)))
                .filter(product-> ( filterEntity.id.equals("") ? true : product.getId().toString().contains(filterEntity.id)))
                .filter(product-> (filterEntity.price.equals("") ? true : product.getPrice().toString().contains(filterEntity.price)))
               .collect(Collectors.toList());
        return products;
    }

    @ExceptionHandler
    public ResponseEntity<EntityNotFoundResponse> handleException(EntityNotFoundException ex){
        EntityNotFoundResponse response = new EntityNotFoundResponse();
        response.setEntityName(ex.getEntityName());
        response.setEntityId(ex.getEntityId());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
