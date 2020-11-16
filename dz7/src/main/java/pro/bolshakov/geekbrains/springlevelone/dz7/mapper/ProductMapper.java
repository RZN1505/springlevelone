package pro.bolshakov.geekbrains.springlevelone.dz7.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Product;
import pro.bolshakov.geekbrains.springlevelone.dz7.dto.ProductDto;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto dto);

    @InheritInverseConfiguration
    ProductDto fromProduct(Product product);

    List<Product> toProductList(List<ProductDto> productDtos);

    List<ProductDto> fromProductList(List<Product> products);

}
