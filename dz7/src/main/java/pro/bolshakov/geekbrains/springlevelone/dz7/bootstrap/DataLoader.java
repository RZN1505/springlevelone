package pro.bolshakov.geekbrains.springlevelone.dz7.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pro.bolshakov.geekbrains.springlevelone.dz7.dao.OrderDao;
import pro.bolshakov.geekbrains.springlevelone.dz7.dao.ProductDao;
import pro.bolshakov.geekbrains.springlevelone.dz7.dao.UserDao;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Order;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Role;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
@Component
public class DataLoader implements CommandLineRunner {

    public static final User USER = new User();
    public static final User ADMIN = new User();

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    static {
        USER.setName("User");
        USER.setPassword("pass");
        USER.setRole(Role.USER);

        ADMIN.setName("Admin");
        ADMIN.setPassword("admin");
        ADMIN.setRole(Role.ADMIN);
    }

    public DataLoader(OrderDao orderDao, ProductDao productDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.userDao = userDao;
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

        User u1 = new User();
        u1.setName("user1");
        u1.setPassword("user1");
        u1.setRole(Role.ADMIN);

        User u2 = new User();
        u2.setName("user2");
        u2.setPassword("user2");
        u2.setRole(Role.USER);

        userDao.save(u1);
        userDao.save(u2);

    }

}
