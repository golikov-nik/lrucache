package ru.ifmo.rain.golikov.mockapi.http;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.UncheckedIOException;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;
import static ru.ifmo.rain.golikov.mockapi.TestUtils.withStubServer;

/**
 * @author akirakozov
 */
public class UrlReaderWIthStubServerTest {
  private static final int PORT = 32453;
  private final UrlReader urlReader = new UrlReader();

  @Test
  public void readAsText() {
    withStubServer(PORT, s -> {
      whenHttp(s)
              .match(method(Method.GET), startsWithUri("/ping"))
              .then(stringContent("pong"));

      String result = urlReader.readAsText("http://localhost:" + PORT + "/ping");

      Assert.assertEquals("pong\n", result);
    });
  }

  @Test(expected = UncheckedIOException.class)
  public void readAsTextWithNotFoundError() {
    withStubServer(PORT, s -> {
      whenHttp(s)
              .match(method(Method.GET), startsWithUri("/ping"))
              .then(status(HttpStatus.NOT_FOUND_404));

      urlReader.readAsText("http://localhost:" + PORT + "/ping");
    });
  }
}