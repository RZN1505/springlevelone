package pro.bolshakov.geekbrains.springlevelone.dz7.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pro.bolshakov.geekbrains.springlevelone.dz7.dao.OrderDao;
import pro.bolshakov.geekbrains.springlevelone.dz7.dao.ProductDao;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Order;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final OrderDao orderDao;
    private final ProductDao productDao;

    public DataLoader(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    private void initData(){

        Product cheese = new Product(1L,"Cheese", 450.0);
        Product milk = new Product(2L,"Milk", 55.0);
        Product bread = new Product(3L,"Bread", 34.0);
        Product beer = new Product(4L,"Beer", 65.0);

        System.out.println("init Products");
        cheese = productDao.save(cheese);
        milk = productDao.save(milk);
        bread = productDao.save(bread);
        beer = productDao.save(beer);
        System.out.println("init orders");
        Order order1 = new Order();
        order1.setProducts(new ArrayList<>(Arrays.asList(cheese, milk, bread)));
        orderDao.save(order1);

        Order order2 = new Order();
        order2.setProducts(new ArrayList<>(Arrays.asList(cheese, beer)));
        orderDao.save(order2);

        Order order3 = new Order();
        order3.setProducts(new ArrayList<>(Arrays.asList(cheese, bread)));
        orderDao.save(order3);

    }

}
