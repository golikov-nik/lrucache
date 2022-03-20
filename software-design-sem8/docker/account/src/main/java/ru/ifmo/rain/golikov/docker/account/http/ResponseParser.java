package ru.ifmo.rain.golikov.docker.account.http;

import com.google.gson.JsonParser;
import ru.ifmo.rain.golikov.docker.account.model.Stock;

public class ResponseParser {
  public Stock parseStock(final String jsonString) {
    var json = JsonParser.parseString(jsonString).getAsJsonObject();
    return new Stock(json.get("name").getAsString(), json.get("stockAmount").getAsLong(), json.get("stockPrice").getAsDouble());
  }
}
