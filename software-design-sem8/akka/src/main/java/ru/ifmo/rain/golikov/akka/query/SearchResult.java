package ru.ifmo.rain.golikov.akka.query;

public class SearchResult {
  String host;
  String text;

  public SearchResult(String host, String text) {
    this.host = host;
    this.text = text;
  }

  @Override
  public String toString() {
    return "[%s]: %s".formatted(host, text);
  }
}
