package ru.ifmo.rain.golikov.mvc.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class TodoList {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private long id;
  private String name;
  @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
  private List<Todo> items;

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public List<Todo> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "TodoList{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", items=" + items +
            '}';
  }
}
