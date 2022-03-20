package ru.ifmo.rain.golikov.docker.account.model;

public class Stock {
  private final String name;
  private final long amount;
  private final double price;

  public Stock(final String name, final long amount, final double price) {
    this.name = name;
    this.amount = amount;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public long getAmount() {
    return amount;
  }
}
