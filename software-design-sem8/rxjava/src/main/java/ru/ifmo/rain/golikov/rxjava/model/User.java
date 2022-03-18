package ru.ifmo.rain.golikov.rxjava.model;

import org.bson.Document;

import java.util.*;

public class User {
  private final long id;
  public final String login;
  public final Currency currency;

  public User(final long id, final String login, final Currency currency) {
    this.id = id;
    this.login = login;
    this.currency = currency;
  }

  public User(final Document document) {
    this(document.getLong("id"), document.getString("login"), document.getString("currency"));
  }

  public User(final long id, final String login, final String currency) {
    this(id, login, Currency.byName(currency));
  }

  public Document toDocument() {
    return new Document(Map.of("id", id, "login", login, "currency", currency.toString()));
  }

  public Currency getCurrency() {
    return currency;
  }
}
