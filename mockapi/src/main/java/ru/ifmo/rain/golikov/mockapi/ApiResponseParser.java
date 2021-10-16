package ru.ifmo.rain.golikov.mockapi;

import com.google.gson.JsonParser;

import java.time.Instant;
import java.util.*;

public class ApiResponseParser {

  public List<Instant> parseResponse(final String response) {
    var json = JsonParser.parseString(response);
    var responseObject = json.getAsJsonObject().get("response").getAsJsonObject();
    var responseItems = responseObject.get("items").getAsJsonArray();
    var instantList = new ArrayList<Instant>(responseItems.size());
    for (var item : responseItems) {
      long timestamp = item.getAsJsonObject().get("date").getAsLong();
      instantList.add(Instant.ofEpochSecond(timestamp));
    }
    return instantList;
  }
}
