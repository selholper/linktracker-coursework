package ru.selholper.coursework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.selholper.coursework.models.Link;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {
    @Query("SELECT link FROM Link link WHERE link.type = :type")
    List<Link> findByType(String type);
}
