package ru.ifmo.rain.golikov.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import ru.ifmo.rain.golikov.akka.actors.QueryActor;
import ru.ifmo.rain.golikov.akka.query.SearchResult;
import scala.concurrent.Await;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings("unchecked")
public class Main {
  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("MySystem");
    ActorRef queryActor = system.actorOf(Props.create(QueryActor.class), "query-actor");

    final var mainTimeout = Timeout.create(Duration.ofSeconds(QueryActor.TIMEOUT.duration().toSeconds() + 2));
    try (Scanner in = new Scanner(System.in)) {
      ((List<SearchResult>) Await.result(Patterns.ask(queryActor, in.next(), mainTimeout),
                    mainTimeout.duration())).forEach(System.out::println);
      system.terminate();
    } catch (InterruptedException | TimeoutException e) {
      e.printStackTrace();
    }
  }
}
