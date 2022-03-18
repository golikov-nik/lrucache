package ru.ifmo.rain.golikov.akka.actors;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.xebialabs.restito.semantics.Condition;
import org.glassfish.grizzly.http.Method;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ifmo.rain.golikov.akka.query.SearchResult;
import scala.concurrent.Await;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static org.junit.Assert.assertEquals;
import static ru.ifmo.rain.golikov.akka.TestUtils.withStubServer;
import static ru.ifmo.rain.golikov.akka.actors.QueryActor.TIMEOUT;

public class SearchActorTest {
  public static final int PORT = 56630;
  ActorSystem system;

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
      final var host = "google.localhost";
      final var query = "icpc";
      whenHttp(s).match(method(Method.GET), Condition.startsWithUri("/%s".formatted(query)))
              .then(stringContent(makeJSON("a", "b", "c")));

      final var actor = system.actorOf(Props.create(SearchActor.class, host, PORT));
      try {
        final List<SearchResult> response = (List<SearchResult>) Await.result(Patterns.ask(actor, query, TIMEOUT), TIMEOUT.duration());
        final List<SearchResult> expected = new ArrayList<>();
        expected.add(new SearchResult(host, "a"));
        expected.add(new SearchResult(host, "b"));
        expected.add(new SearchResult(host, "c"));
        checkResults(response, expected);
      } catch (TimeoutException | InterruptedException e) {
        e.printStackTrace();
        Assert.fail();
      }
    });
  }

  public static void checkResults(final List<SearchResult> response, final List<SearchResult> expected) {
    assertEquals(toStrings(response), toStrings(expected));
  }

  private static List<String> toStrings(final List<SearchResult> response) {
    return response.stream().map(SearchResult::toString).sorted().collect(Collectors.toList());
  }

  private String makeJSON(final String a, final String b, final String c) {
    return """
    {"response":{"items":["%s", "%s", "%s"]}}
  """.formatted(a, b, c);
  }
}
