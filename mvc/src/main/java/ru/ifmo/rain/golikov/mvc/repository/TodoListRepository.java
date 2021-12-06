package ru.ifmo.rain.golikov.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.rain.golikov.mvc.model.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
