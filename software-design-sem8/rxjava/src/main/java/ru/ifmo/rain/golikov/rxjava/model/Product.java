package ru.ifmo.rain.golikov.rxjava.model;

import org.bson.Document;

import java.util.*;

public class Product {
  private final long id;
  private final String name;
  private final long price;
  private final Currency currency;

  public Product(final long id, final String name, final long price, final Currency currency) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.currency = currency;
  }

  public Product(final long id, final String name, final long price, final String currency) {
    this(id, name, price, Currency.byName(currency));
  }

  public Product(final Document document) {
    this(document.getLong("id"), document.getString("name"), document.getLong("price"), document.getString("currency"));
  }

  public Document toDocument() {
    return new Document(Map.of("id", id, "name", name, "price", price, "currency", currency.toString()));
  }

  public String getName() {
    return name;
  }

  public long getPrice() {
    return price;
  }

  public Currency getCurrency() {
    return currency;
  }
}
