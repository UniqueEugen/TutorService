package kurenkov.tutorservice.services;

import kurenkov.tutorservice.repositories.OrderRepository;
import kurenkov.tutorservice.entities.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository ordersRepository;

    public OrderService(OrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Order> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return ordersRepository.save(order);
    }

    public void deleteOrder(Long id) {
        ordersRepository.deleteById(id);
    }
}
