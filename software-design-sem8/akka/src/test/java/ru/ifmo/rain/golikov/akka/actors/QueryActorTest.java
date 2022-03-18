package ru.ifmo.rain.golikov.akka.actors;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.xebialabs.restito.semantics.Condition;
import org.glassfish.grizzly.http.Method;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ifmo.rain.golikov.akka.query.SearchResult;
import scala.concurrent.Await;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.delay;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static ru.ifmo.rain.golikov.akka.TestUtils.withStubServer;
import static ru.ifmo.rain.golikov.akka.actors.SearchActorTest.checkResults;

public class QueryActorTest {
  public static final int PORT = 56630;
  ActorSystem system;
  final private Timeout TIMEOUT = Timeout.create(Duration.ofSeconds(QueryActor.TIMEOUT.duration().toSeconds() + 2));

  @Before
  public void beforeTest() {
    system = ActorSystem.create("TestSystem");
  }

  @After
  public void afterTest() {
    system.terminate();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testSuccessful() {
    withStubServer(PORT, s -> {
      final var query = "icpc";
      final var HOSTS = List.of("google.localhost", "yandex.localhost", "bing.localhost");

      for (final var h : HOSTS) {
        whenHttp(s).match(method(Method.GET), Condition.url("http://%s:%d/%s".formatted(h, PORT, query)))
                .then(stringContent(makeJSON(h + "-a", h + "-b", h + "-c")));
      }
      final var actor = system.actorOf(Props.create(QueryActor.class, HOSTS, PORT));
      try {
        final List<SearchResult> response = (List<SearchResult>) Await.result(Patterns.ask(actor, query, TIMEOUT), TIMEOUT.duration());
        List<SearchResult> expected = new ArrayList<>();
        for (final var h : HOSTS) {
          expected.add(new SearchResult(h, h + "-a"));
          expected.add(new SearchResult(h, h + "-b"));
          expected.add(new SearchResult(h, h + "-c"));
        }
        checkResults(response, expected);
      } catch (TimeoutException | InterruptedException e) {
        e.printStackTrace();
        Assert.fail();
      }
    });
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testDelay() {
    withStubServer(PORT, s -> {
      final var query = "icpc";
      final var HOSTS = List.of("google.localhost", "yandex.localhost", "bing.localhost");

      for (final var h : HOSTS) {
        if (h.startsWith("bing")) {
          whenHttp(s).match(method(Method.GET), Condition.url("http://%s:%d/%s".formatted(h, PORT, query)))
                  .then(delay(5000));
        } else {
          whenHttp(s).match(method(Method.GET), Condition.url("http://%s:%d/%s".formatted(h, PORT, query)))
                  .then(stringContent(makeJSON(h + "-a", h + "-b", h + "-c")));
        }
      }
      final var actor = system.actorOf(Props.create(QueryActor.class, HOSTS, PORT));
      try {
        final List<SearchResult> response = (List<SearchResult>) Await.result(Patterns.ask(actor, query, TIMEOUT), TIMEOUT.duration());
        List<SearchResult> expected = new ArrayList<>();
        for (final var h : HOSTS.subList(0, 2)) {
          expected.add(new SearchResult(h, h + "-a"));
          expected.add(new SearchResult(h, h + "-b"));
          expected.add(new SearchResult(h, h + "-c"));
        }
        checkResults(response, expected);
      } catch (TimeoutException | InterruptedException e) {
        e.printStackTrace();
        Assert.fail();
      }
    });
  }

  private String makeJSON(final String a, final String b, final String c) {
    return """
    {"response":{"items":["%s", "%s", "%s"]}}
  """.formatted(a, b, c);
  }
}
