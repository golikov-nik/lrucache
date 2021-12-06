package ru.ifmo.rain.golikov.mvc.dao;

import ru.ifmo.rain.golikov.mvc.model.Todo;
import ru.ifmo.rain.golikov.mvc.model.TodoList;

import java.util.*;

public interface TodoDao {
  void addTodo(long todoListId, Todo todo);
  void markTodoDone(long todoId);

  void addTodoList(TodoList todoList);
  void deleteTodoList(long todoListId);

  List<TodoList> getTodoLists();
}
