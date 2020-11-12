package pro.bolshakov.geekbrains.dz6;

import org.hibernate.cfg.Configuration;
import pro.bolshakov.geekbrains.dz6.domain.Client;
import pro.bolshakov.geekbrains.dz6.domain.Product;
import pro.bolshakov.geekbrains.dz6.domain.Sell;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory entityFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = entityFactory.createEntityManager();

        Product product = new Product();
        product.setTitle("Milk");
        product.setPrice(45d);
        InitData.initData(em);

        createEntity(em, product);

        Product productApp = new Product();
        productApp.setTitle("Apple");
        productApp.setPrice(120d);

        createEntity(em, productApp);

        Product productOr = new Product();
        productOr.setTitle("Orange");
        productOr.setPrice(140d);

        createEntity(em, productOr);

        Client client1 = new Client();
        client1.setName("Client1");

        createEntity(em, client1);

        Client client2 = new Client();
        client2.setName("Client2");

        createEntity(em, client2);

        Client client3 = new Client();
        client3.setName("Client3");

        createEntity(em, client3);

        Sell sell1 = new Sell();
        sell1.setClient(client1);
        List<Product> productList1 = new ArrayList<>();
        productList1.add(product);
        productList1.add(productApp);
        sell1.setProducts(productList1);
        sell1.setAmount(2L);

        createEntity(em, sell1);

        Sell sell2 = new Sell();
        sell2.setClient(client2);
        List<Product> productList2 = new ArrayList<>();
        productList2.add(productOr);
        sell2.setProducts(productList2);
        sell2.setAmount(1L);

        createEntity(em, sell2);

        Sell sell3 = new Sell();
        sell3.setClient(client3);
        List<Product> productList3 = new ArrayList<>();
        productList3.add(productOr);
        productList3.add(product);
        sell3.setProducts(productList3);
        sell3.setAmount(2L);

        createEntity(em, sell3);

        Product readEntity = readEntity(em, Product.class, 1L);
        readEntity.setTitle("Bread");

        saveEntity(em, readEntity);


        List products = em.createNativeQuery("SELECT p.title FROM products p " +
                "RIGHT JOIN (SELECT * FROM clients c " +
                "JOIN (SELECT * FROM products_sells ps" +
                " RIGHT JOIN sells s ON s.id = ps.sell_id) q " +
                "ON q.clients_id = c.id) qq ON qq.product_id = p.id " +
                "WHERE qq.clients_id = :id")
                .setParameter("id", 1)
                .getResultList();
        products.forEach(System.out::println);


        List clients = em.createNativeQuery("SELECT qq.name FROM products p " +
                "RIGHT JOIN(SELECT * FROM clients c " +
                "JOIN (SELECT * FROM products_sells ps " +
                "RIGHT JOIN sells s ON s.id = ps.sell_id) q " +
                "ON q.clients_id = c.id) qq " +
                "ON qq.product_id = p.id " +
                "WHERE p.id = :id")
                .setParameter("id", 1)
                .getResultList();
        clients.forEach(System.out::println);

        //deleteEntity(em, Product.class, 1L);
        //deleteEntity(em, Client.class, 1L);

    }

    private static <T> void createEntity(EntityManager em, T entity){

        System.out.println("Creating entity");
        //open transaction
        em.getTransaction().begin();
        //put person into persist area of Hibernate
        em.persist(entity);
        //commit/close transaction
        em.getTransaction().commit();

        System.out.println("Creating finished");

    }


    private static <T> void deleteEntity(EntityManager em, Class<T> clazz, long id){
        System.out.println("Start delete");

        em.getTransaction().begin();
        T person = em.find(clazz, id);
        em.remove(person);
        em.getTransaction().commit();

        System.out.println("Delete completed->" + person);
    }
    private static <T> T readEntity(EntityManager em, Class<T> clazz, long id){
        System.out.println("Start reading");

        em.getTransaction().begin();
        T person = em.find(clazz, id);
        em.getTransaction().commit();

        System.out.println("Reading completed->" + person);
        return person;
    }

    private static <T> T saveEntity(EntityManager em, T entity){
        System.out.println("Start saving");

        em.getTransaction().begin();
        T savedEntity = em.merge(entity);
        em.getTransaction().commit();

        System.out.println("Saving completed->" + savedEntity);

        return savedEntity;
    }



}