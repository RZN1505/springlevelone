package pro.bolshakov.geekbrains.dz6;

import pro.bolshakov.geekbrains.dz6.domain.Product;

import javax.persistence.EntityManager;

public class InitData {
    private static Product PRODUCT_1 = new Product();
    private static Product PRODUCT_2 = new Product();
    private static Product PRODUCT_3 = new Product();
    private static Product PRODUCT_4 = new Product();
    private static Product PRODUCT_5 = new Product();

    static {
        //Product
        {
            PRODUCT_1.setTitle("Pineapple");
            PRODUCT_1.setPrice(200d);

            PRODUCT_2.setTitle("Eggs");
            PRODUCT_2.setPrice(70d);

            PRODUCT_3.setTitle("Potato");
            PRODUCT_3.setPrice(40d);

            PRODUCT_4.setTitle("Tomato");
            PRODUCT_4.setPrice(200d);

            PRODUCT_5.setTitle("Salad");
            PRODUCT_5.setPrice(60d);
        }
    }

    public static void initData(EntityManager em){

        initProduct(em);
    }

    private static void initProduct(EntityManager em) {
        em.getTransaction().begin();

        System.out.println("Product initialized");
        PRODUCT_1 = em.merge(PRODUCT_1);
        PRODUCT_2 = em.merge(PRODUCT_2);
        PRODUCT_3 = em.merge(PRODUCT_3);
        PRODUCT_4 = em.merge(PRODUCT_4);
        PRODUCT_5 = em.merge(PRODUCT_5);

        em.getTransaction().commit();
    }

    public static Product getProduct1() {
        return PRODUCT_1;
    }

    public static Product getProduct2() {
        return PRODUCT_2;
    }

    public static Product getProduct3() {
        return PRODUCT_3;
    }

    public static Product getProduct4() {
        return PRODUCT_4;
    }

    public static Product getProduct5() {
        return PRODUCT_5;
    }
}