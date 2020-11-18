package pro.bolshakov.geekbrains.springlevelone.dz7.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pro.bolshakov.geekbrains.springlevelone.dz7.domain.Order;
import pro.bolshakov.geekbrains.springlevelone.dz7.dto.OrderDto;

import java.util.List;

@Mapper(uses = {ProductMapper.class})
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderedProducts", target = "products")
    Order toOrder(OrderDto dto);

    List<Order> toOrderList(List<OrderDto> orders);

    @InheritInverseConfiguration
    OrderDto fromOrder(Order order);

    List<OrderDto> fromOrderList(List<Order> orders);

}
