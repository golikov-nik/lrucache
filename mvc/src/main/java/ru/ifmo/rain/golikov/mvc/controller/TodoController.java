package ru.ifmo.rain.golikov.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ifmo.rain.golikov.mvc.dao.TodoDao;
import ru.ifmo.rain.golikov.mvc.model.Todo;
import ru.ifmo.rain.golikov.mvc.model.TodoList;

import java.util.*;

@Controller
public class TodoController {
  private final TodoDao todoDao;

  public TodoController(final TodoDao todoDao) {
    this.todoDao = todoDao;
  }

  @GetMapping("/get-todos")
  public String getTodos(ModelMap map) {
    prepareModelMap(map, todoDao.getTodoLists());
    return "index";
  }

  @PostMapping("/add-todoList")
  public String addTodoList(@ModelAttribute("todoList") TodoList todoList) {
    todoDao.addTodoList(todoList);
    return "redirect:/get-todos";
  }

  @PostMapping("/delete-todoList")
  public String deleteTodoList(@ModelAttribute("todoListId") long todoListId) {
    todoDao.deleteTodoList(todoListId);
    return "redirect:/get-todos";
  }

  @PostMapping("/add-todo")
  public String addTodo(@ModelAttribute("todoListId") long todoListId, @ModelAttribute("todo") Todo todo) {
    todoDao.addTodo(todoListId, todo);
    return "redirect:/get-todos";
  }

  @PostMapping("/mark-todo-done")
  public String markTodoDone(@ModelAttribute("todoId") long todoId) {
    todoDao.markTodoDone(todoId);
    return "redirect:/get-todos";
  }

  private void prepareModelMap(ModelMap map, List<TodoList> todoLists) {
    map.addAttribute("todoLists", todoLists);
    map.addAttribute("todoList", new TodoList());
    map.addAttribute("todo", new Todo());
  }
}
