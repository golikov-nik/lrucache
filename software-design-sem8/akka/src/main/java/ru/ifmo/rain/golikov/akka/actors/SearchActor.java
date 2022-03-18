package ru.ifmo.rain.golikov.akka.actors;

import akka.actor.AbstractActor;
import com.google.gson.JsonParser;
import ru.ifmo.rain.golikov.akka.http.UrlReader;
import ru.ifmo.rain.golikov.akka.query.SearchResult;

import java.util.*;

public class SearchActor extends AbstractActor {
  private final String host;
  private final int port;
  private final UrlReader reader = new UrlReader();

  SearchActor(String host, int port) {
    this.host = host;
    this.port = port;
  }

  SearchActor(String host) {
    this(host, 80);
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(String.class, s -> sender().tell(makeResult(s), self())).build();
  }

  private List<SearchResult> makeResult(final String s) {
    var json = JsonParser.parseString(reader.readAsText(createURL(s)));
    var responseObject = json.getAsJsonObject().get("response").getAsJsonObject();
    var responseItems = responseObject.get("items").getAsJsonArray();
    var result = new ArrayList<SearchResult>();
    for (final var x : responseItems) {
      result.add(new SearchResult(host, x.getAsString()));
    }
    return result;
  }

  private String createURL(final String s) {
    return "http://%s:%d/%s".formatted(host, port, s);
  }
}
