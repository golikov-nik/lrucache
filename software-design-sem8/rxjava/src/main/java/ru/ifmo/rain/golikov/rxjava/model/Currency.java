package ru.ifmo.rain.golikov.rxjava.model;

public class Currency {
  public static final Currency USD = new Currency(1, "USD");
  public static final Currency RUB = new Currency(104.98, "RUB");
  public static final Currency EUR = new Currency(0.91, "EUR");
  private final double ratio;
  private final String name;

  public Currency(final double ratio, final String name) {
    this.ratio = ratio;
    this.name = name;
  }

  public static double convert(Currency from, Currency to, double price) {
    return price / from.ratio * to.ratio;
  }

  public static Currency byName(final String currency) {
    return switch (currency) {
      case "USD" -> USD;
      case "RUB" -> RUB;
      case "EUR" -> EUR;
      default -> throw new IllegalArgumentException("Wrong currency: %s".formatted(currency));
    };
  }

  @Override
  public String toString() {
    return name;
  }
}
