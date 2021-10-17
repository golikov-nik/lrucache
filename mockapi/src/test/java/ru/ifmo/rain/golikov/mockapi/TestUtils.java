package ru.ifmo.rain.golikov.mockapi;

import com.xebialabs.restito.server.StubServer;

import java.time.Instant;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class TestUtils {
  public static List<Instant> makeInstants(final List<Long> timestamps) {
    return timestamps
            .stream()
            .map(Instant::ofEpochSecond)
            .collect(Collectors.toList());
  }

  public static void withStubServer(int port, Consumer<StubServer> callback) {
    StubServer stubServer = null;
    try {
      stubServer = new StubServer(port).run();
      callback.accept(stubServer);
    } finally {
      if (stubServer != null) {
        stubServer.stop();
      }
    }
  }
}
