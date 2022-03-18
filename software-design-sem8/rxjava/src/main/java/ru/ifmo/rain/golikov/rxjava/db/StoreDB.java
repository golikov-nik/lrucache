package ru.ifmo.rain.golikov.rxjava.db;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.Document;
import ru.ifmo.rain.golikov.rxjava.model.Currency;
import ru.ifmo.rain.golikov.rxjava.model.Product;
import ru.ifmo.rain.golikov.rxjava.model.User;
import rx.Observable;

public class StoreDB {
  private final MongoClient client = MongoClients.create("mongodb://localhost:27017");
  private final MongoDatabase database = client.getDatabase("rxjava");
  private final MongoCollection<Document> users = database.getCollection("users");
  private final MongoCollection<Document> products = database.getCollection("products");

  public Observable<String> registerUser(final User user) {
    return users.insertOne(user.toDocument()).map(s -> "Successfully registered.");
  }

  public Observable<String> addProduct(final Product product) {
    return products.insertOne(product.toDocument()).map(s -> "Successfully added.");
  }

  public Observable<String> listProducts(final long userId) {
    return findUserById(userId).map(User::getCurrency).flatMap(currency ->
            products.find().toObservable().map(Product::new).collect(StringBuilder::new, (sb, x) -> {
              sb.append("<h1>");
              sb.append(x.getName());
              sb.append(" for ");
              sb.append(String.format("%.3f", Currency.convert(x.getCurrency(), currency, x.getPrice())));
              sb.append(" ");
              sb.append(currency);
              sb.append("</h1>");
              sb.append("\n");
            }).map(StringBuilder::toString)
    );
  }

  private Observable<User> findUserById(final long userId) {
    return users.find(Filters.eq("id", userId)).first().map(User::new);
  }
}
