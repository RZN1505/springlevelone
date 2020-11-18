package pro.bolshakov.geekbrains.springlevelone.dz7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;
import pro.bolshakov.geekbrains.springlevelone.dz7.service.ProductServiceImpl;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class MainProductController {

    private final ProductServiceImpl productService;

    public MainProductController(ProductServiceImpl productServiceImpl) {
        this.productService = productServiceImpl;
    }

    // http://localhost:8080/products - GET
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8080/products/1 - GET
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(Model model,@PathVariable("id") Long id){
        Product byId = productService.findById(id);
        model.addAttribute("product",
                byId == null ? new Product(): byId);
        return "product";
    }

    // http://localhost:8080/products/1/price - GET
    @RequestMapping(value = "/{id}/price", method = RequestMethod.GET)
    @ResponseBody
    public String apiPrice(@PathVariable Long id){
        Product byId = productService.findById(id);
        return String.valueOf(byId == null ? null : byId.getPrice());
    }

    // http://localhost:8080/products/new - GET
    @GetMapping("/new")
    public String getFormNewProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    // http://localhost:8080/products/new - POST
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewProduct(Product product){
        Product savedProduct = productService.save(product);
        System.out.println(savedProduct);
        return "redirect:/products/" + savedProduct.getId();
    }

    // http://localhost:8080/products/any
    @RequestMapping(value = "any")
    @ResponseBody
    public String anyRequest(){
        return "any request " + UUID.randomUUID().toString();
    }

    // http://localhost:8080/products?price_from=35.4&priceTo=3
    @GetMapping(params = {"price_from", "priceTo"})
    public String productsByPrice(Model model,
                                  @RequestParam(name = "price_from") double priceFrom,
                                  @RequestParam double priceTo){
        List<Product> products = productService.findAllByPriceBetween(priceFrom, priceTo);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8080/filter?price_from=35.4&priceTo=3
    @GetMapping("/filter")
    public String filterByPrice(Model model,
                                  @RequestParam(name = "price_from") double priceFrom,
                                  @RequestParam(required = false) Double priceTo){
        List<Product> products =
                productService.findAllByPriceBetween(priceFrom, priceTo == null ? Double.MAX_VALUE : priceTo);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8080/filter {title:"asd", id: "1", price: "10"}
   /* @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filterByTitle(Model model,@RequestBody filterEntity filterEntity){
        System.out.println(filterEntity.title);
        List<Product> products = productService.findAll().stream()
                .filter(product-> ( filterEntity.title.equals("") ? true : product.getTitle().contains(filterEntity.title)))
                .filter(product-> ( filterEntity.id.equals("") ? true : product.getId().toString().contains(filterEntity.id)))
                .filter(product-> (filterEntity.price.equals("") ? true : product.getPrice().toString().contains(filterEntity.price)))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "list";
    }*/

}
