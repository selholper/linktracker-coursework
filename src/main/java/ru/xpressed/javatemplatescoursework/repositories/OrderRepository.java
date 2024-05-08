package ru.xpressed.javatemplatescoursework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xpressed.javatemplatescoursework.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}

