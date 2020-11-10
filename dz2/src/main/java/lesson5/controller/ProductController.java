package lesson5.controller;

import lesson5.domain.Product;
import lesson5.service.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {this.productService = productService;}

    //http://localhost:8080/app/ - GET
    @GetMapping("/")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
        return "redirect:/page";
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Product> products = productService.findPage(currentPage - 1, pageSize);
        model.addAttribute("products", products);
        int totalPages = products.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "list";
    }

    //http://localhost:8080/app/1 - GET
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String getById(Model model,@PathVariable("id") Integer id){
        Product byId = productService.findById(id);
        model.addAttribute("product",
                byId == null ? new Product(): byId);
        return "product";
    }

    //http://localhost:8080/app/1 - POST
    @RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
    public String updateProduct(Product product){
        productService.saveAndSet(product);
        return "redirect:/product/{id}";
    }

    //http://localhost:8080/app/new - GET
    @GetMapping("/new")
    public String getNewProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    //http://localhost:8080/app/new - POST
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewProduct(Product product){
        productService.saveAndSet(product);
        return "redirect:/";
    }

    //http://localhost:8080/app/new - GET
    @GetMapping("/newTest")
    public String addNewTestProduct(){
        for (int i = 0; i < 20; i++) {
            Product product = new Product();
            product.setTitle("Product " + (i + 1));
            product.setPrice(5 + i*1.5f);
            productService.saveAndSet(product);
        }
        return "redirect:/";
    }

    //http://localhost:8080/app/price?priceFrom=5&priceTo=12 - GET
    @GetMapping("/price")
    public String listPrice(Model model,
                            @RequestParam(name = "priceFrom", defaultValue = "0") float priceFrom,
                            @RequestParam(name = "priceTo", defaultValue = "9999999") float priceTo){
        List<Product> products = productService.findAllByPriceBetween(priceFrom, priceTo);
        model.addAttribute("products", products);
        return "list";
    }





}
