package lesson5.repository;

import lesson5.domain.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductJpqlDAOImpl implements ProductDAO {

    private final EntityManager em;

    public ProductJpqlDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("SELECT a from Product a", Product.class).getResultList();
    }

    @Override
    public Product findById(Long id) {
        return em.createQuery("SELECT a from Product a where a.id = :id", Product.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public void save(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    @Override
    public void update(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Product product) {
        em.getTransaction().begin();
        em.remove(product);
        em.getTransaction().commit();
    }
}
