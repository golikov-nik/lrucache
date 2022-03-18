package ru.ifmo.rain.golikov.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import ru.ifmo.rain.golikov.akka.query.SearchResult;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class QueryActor extends AbstractActor {
  private static final List<String> DEFAULT_HOSTS = List.of("google.com", "yandex.ru", "bing.com");
  public static final Timeout TIMEOUT = Timeout.create(Duration.ofSeconds(3));
  private final List<String> hosts;
  private final int port;

  QueryActor(List<String> hosts, int port) {
    this.hosts = hosts;
    this.port = port;
  }

  QueryActor(List<String> hosts) {
    this(hosts, 80);
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(String.class, s -> sender().tell(makeQuery(s), self())).build();
  }

  @SuppressWarnings("unchecked")
  private List<SearchResult> makeQuery(final String query) {
    List<Future<Object>> children = new ArrayList<>();
    for (final var h : hosts) {
      children.add(Patterns.ask(context().actorOf(Props.create(SearchActor.class, h, port)), query, TIMEOUT));
    }
    System.err.println("Initialized children");
    return children.stream().flatMap(x -> {
      try {
        return ((List<SearchResult>) Await.result(x, TIMEOUT.duration())).stream();
      } catch (TimeoutException | InterruptedException e) {
        return Stream.of();
      }
    }).collect(Collectors.toList());
  }

  QueryActor() {
    this(DEFAULT_HOSTS);
  }
}
