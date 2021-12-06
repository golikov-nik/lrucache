package ru.ifmo.rain.golikov.mvc.dao;

import org.springframework.stereotype.Service;
import ru.ifmo.rain.golikov.mvc.model.Todo;
import ru.ifmo.rain.golikov.mvc.model.TodoList;
import ru.ifmo.rain.golikov.mvc.repository.TodoListRepository;
import ru.ifmo.rain.golikov.mvc.repository.TodoRepository;

import java.util.*;

@Service
public class TodoJpaDao implements TodoDao {
  private final TodoRepository todoRepository;
  private final TodoListRepository todoListRepository;

  public TodoJpaDao(TodoRepository todoRepository, TodoListRepository todoListRepository) {
    this.todoRepository = todoRepository;
    this.todoListRepository = todoListRepository;
  }

  @Override
  public void addTodo(final long todoListId, final Todo todo) {
    todoListRepository.findById(todoListId).ifPresent(l -> {
      todo.setList(l);
      todoRepository.save(todo);
    });
  }

  @Override
  public void markTodoDone(final long todoId) {
    todoRepository.findById(todoId).ifPresent(todo -> {
      todo.setDone(true);
      todoRepository.save(todo);
    });
  }

  @Override
  public void addTodoList(final TodoList todoList) {
    todoListRepository.save(todoList);
  }

  @Override
  public void deleteTodoList(final long todoListId) {
    todoListRepository.deleteById(todoListId);
  }

  @Override
  public List<TodoList> getTodoLists() {
    return todoListRepository.findAll();
  }
}
