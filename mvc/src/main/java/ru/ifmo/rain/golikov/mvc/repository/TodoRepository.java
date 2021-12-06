package ru.ifmo.rain.golikov.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.rain.golikov.mvc.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
