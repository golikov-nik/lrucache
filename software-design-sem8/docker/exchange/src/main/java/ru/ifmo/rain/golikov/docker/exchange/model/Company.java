package ru.ifmo.rain.golikov.docker.exchange.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private long stockAmount;
  private String name;
  private double stockPrice;

  public Company() {

  }

  public Company(final String name, final long stockAmount, final double stockPrice) {
    this.name = name;
    this.stockAmount = stockAmount;
    this.stockPrice = stockPrice;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public long getStockAmount() {
    return stockAmount;
  }

  public void setStockAmount(final long amount) {
    this.stockAmount = amount;
  }

  public double getStockPrice() {
    return stockPrice;
  }

  public void setStockPrice(final double price) {
    this.stockPrice = price;
  }

  public void addStocks(final long amount) {
    stockAmount += amount;
  }

  public void buyStocks(final long amount) {
    if (amount > stockAmount) {
      throw new IllegalArgumentException();
    }
    stockAmount -= amount;
  }
}
