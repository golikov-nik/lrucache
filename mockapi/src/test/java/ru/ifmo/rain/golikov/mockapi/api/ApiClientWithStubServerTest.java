package ru.ifmo.rain.golikov.mockapi.api;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.*;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.ifmo.rain.golikov.mockapi.TestUtils.makeInstants;
import static ru.ifmo.rain.golikov.mockapi.TestUtils.withStubServer;

public class ApiClientWithStubServerTest {
  public static final int PORT = 56630;
  public static final String HOST = "http://localhost:" + PORT;
  public static final String API_KEY = "API_KEY";
  private ApiClient client;

  private static final String RESPONSE = """
    {"response":
      {
        "items":
          [
            {"date": 241},
            {"date": 566},
            {"date": 288}
          ]
      }
    }        
  """;

  @Before
  public void setUp() {
    client = new ApiClient(HOST, API_KEY);
  }

  @Test
  public void testGetPosts() {
    withStubServer(PORT, s -> {
      whenHttp(s)
              .match(method(Method.GET), startsWithUri("/method"))
              .then(stringContent(RESPONSE));

      var hashtag = "#food";
      var startTime = Instant.ofEpochSecond(239);
      var endTime = Instant.ofEpochSecond(566);
      var result = client.getPosts(hashtag, startTime, endTime);
      assertThat(result).isEqualTo(makeInstants(List.of(241L, 566L, 288L)));
    });
  }
}
