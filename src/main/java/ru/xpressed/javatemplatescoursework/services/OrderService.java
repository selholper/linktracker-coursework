package ru.xpressed.javatemplatescoursework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xpressed.javatemplatescoursework.models.Order;
import ru.xpressed.javatemplatescoursework.repositories.OrderRepository;

import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }

    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order getById(Integer id) {
        return orderRepository.getById(id);
    }
}
