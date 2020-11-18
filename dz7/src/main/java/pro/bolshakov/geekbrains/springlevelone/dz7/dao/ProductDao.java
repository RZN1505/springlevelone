package pro.bolshakov.geekbrains.springlevelone.dz7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetween(double priceFrom, double priceTo);
    @Query("update Product p set p.title = :title, p.price = :price where p.id=:id")
    @Modifying(clearAutomatically = true)
    @Transactional
    public void updateCols(@Param("id") long id,
                          @Param("title") String title,
                          @Param("price") double price);
}
