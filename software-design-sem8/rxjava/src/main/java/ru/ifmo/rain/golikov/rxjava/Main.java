package ru.ifmo.rain.golikov.rxjava;

import io.reactivex.netty.protocol.http.server.HttpServer;
import org.apache.log4j.BasicConfigurator;
import ru.ifmo.rain.golikov.rxjava.server.StoreServer;

public class Main {
  public static void main(String[] args) {
    BasicConfigurator.configure();
    StoreServer server = new StoreServer();
    HttpServer.newServer(8080).start((req, resp) -> resp.writeString(server.process(req))).awaitShutdown();
  }
}
