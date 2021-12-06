package ru.ifmo.rain.golikov.mvc.model;

import javax.persistence.*;

@Entity
public class Todo {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private long id;
  private String name;
  private boolean done;

  @ManyToOne
  private TodoList list;

  public TodoList getList() {
    return list;
  }

  public void setList(TodoList list) {
    this.list = list;
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(final boolean done) {
    this.done = done;
  }

  @Override
  public String toString() {
    return "Todo{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", done=" + done +
            ", list=" + list +
            '}';
  }
}
