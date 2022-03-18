package ru.ifmo.rain.golikov.akka;

import com.xebialabs.restito.server.StubServer;

import java.util.function.*;

public class TestUtils {

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
