package pro.bolshakov.geekbrains.dz2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.bolshakov.geekbrains.dz2.domain.Product;

@Repository
public interface ProductJpaDAO extends JpaRepository<Product, Long> {
    /*@Modifying(clearAutomatically = true)
    @Query("update Product p set p.title = :title,p.price = :price  where p.id = :id")
    void updateById (@Param(value = "id") Long id,
                 @Param(value = "title") String title,
                 @Param(value = "price") Double price);

    @Modifying(clearAutomatically = true)
    @Query("delete from Product p  where p.id = :id")
    void deleteById (@Param(value = "id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("select * from Product p  where p.id = :id")
    Product getById (@Param(value = "id") Long id);*/

}
