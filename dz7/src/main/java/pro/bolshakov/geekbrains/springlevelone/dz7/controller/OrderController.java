package pro.bolshakov.geekbrains.springlevelone.dz7.controller;

import org.springframework.web.bind.annotation.*;
import pro.bolshakov.geekbrains.springlevelone.dz7.dto.OrderDto;
import pro.bolshakov.geekbrains.springlevelone.dz7.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id){
        return orderService.findById(id);
    }

    @GetMapping
    public List<OrderDto> list(){
        return orderService.findAll();
    }

    @PostMapping
    public OrderDto save(@RequestBody OrderDto dto){
        return orderService.save(dto);
    }
}
