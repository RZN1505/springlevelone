package pro.bolshakov.geekbrains.springlevelone.dz7.dto;

import java.util.List;

public class OrderDto {

    private Long id;
    private List<ProductDto> orderedProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductDto> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<ProductDto> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
