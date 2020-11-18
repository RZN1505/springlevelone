package pro.bolshakov.geekbrains.dz6.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.bolshakov.geekbrains.dz6.domain.Product;
import pro.bolshakov.geekbrains.dz6.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/products")
@RestController
public class MainRESTController {
    private final ProductService productService;

    public MainRESTController(ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:8080/filter {title:"asd", id: "1", price: "10"}
    @PostMapping("/filter")
    @ResponseBody
    public List<Product> filterByTitle(Model model,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) String id,
                                       @RequestParam(required = false) String price){
        System.out.println(title);
         List<Product> products = productService.getAll().stream()
                .filter(product-> ( (title != null) ? product.getTitle().contains(title) :
                        (id != null) ? product.getId().toString().contains(id) :
                                product.getPrice().toString().contains(price)))
                /*.map(product -> String.valueOf(product.getId()))
                .collect(Collectors.joining(","));*/
        .collect(Collectors.toList());
        // model.addAttribute("products", products);
         return products;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundEx exc) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
