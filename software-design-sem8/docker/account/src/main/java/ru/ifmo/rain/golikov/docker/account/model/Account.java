package ru.ifmo.rain.golikov.docker.account.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String login;
  private double money;

  @ElementCollection
  private Map<String, Long> stocks = new HashMap<>();

  public Account() {
  }

  public Account(final String login) {
    this.login = login;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(final String login) {
    this.login = login;
  }

  public void addMoney(final double amount) {
    money += amount;
  }

  public double getMoney() {
    return money;
  }

  public void setMoney(final double money) {
    this.money = money;
  }

  public Map<String, Long> getStocks() {
    return stocks;
  }

  public void removeMoney(final double amount) {
    if (money < amount) {
      throw new IllegalArgumentException();
    }
    money -= amount;
  }

  public void addStocks(final String company, final double currentPrice, final long amount) {
    double spend = currentPrice * amount;
    if (money < spend) {
      throw new IllegalArgumentException();
    }
    money -= spend;
    stocks.put(company, stocks.getOrDefault(company, 0L) + amount);
  }

  public void removeStocks(final String company, final double currentPrice, final long amount) {
    long current = stocks.getOrDefault(company, 0L);
    if (current < amount) {
      throw new IllegalArgumentException();
    }
    stocks.put(company, current - amount);
    money += currentPrice * amount;
  }
}
