package ru.ifmo.rain.golikov.rxjava.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import ru.ifmo.rain.golikov.rxjava.model.Product;
import ru.ifmo.rain.golikov.rxjava.db.StoreDB;
import ru.ifmo.rain.golikov.rxjava.model.User;
import rx.Observable;

import java.util.*;

public class StoreServer {
  final private StoreDB database = new StoreDB();

  public Observable<String> process(final HttpServerRequest<ByteBuf> req) {
    return switch (req.getDecodedPath().substring(1)) {
      case "register" -> registerUser(req.getQueryParameters());
      case "add-product" -> addProduct(req.getQueryParameters());
      case "list-products" -> listProducts(req.getQueryParameters());
      default -> Observable.just("Invalid request, try again.");
    };
  }

  private Observable<String> listProducts(final Map<String, List<String>> parameters) {
    return database.listProducts(getLongParameter(parameters, "id"));
  }

  private Observable<String> addProduct(final Map<String, List<String>> parameters) {
    final var id = getLongParameter(parameters, "id");
    final var name = getParameter(parameters, "name");
    final var price = getLongParameter(parameters, "price");
    final var currency = getParameter(parameters, "currency");
    return database.addProduct(new Product(id, name, price, currency));
  }

  private Observable<String> registerUser(final Map<String, List<String>> parameters) {
    final var id = getLongParameter(parameters, "id");
    final var login = getParameter(parameters, "login");
    final var currency = getParameter(parameters, "currency");
    return database.registerUser(new User(id, login, currency));
  }

  private long getLongParameter(final Map<String, List<String>> parameters, final String id) {
    return Long.parseLong(getParameter(parameters, id));
  }

  private String getParameter(final Map<String, List<String>> parameters, String name) {
    var result = parameters.get(name);
    if (result == null || result.size() != 1) {
      throw new RuntimeException("Missing parameter: " + name);
    }
    return result.get(0);
  }
}
