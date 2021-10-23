package ru.akirakozov.sd.refactoring;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetProductServletTest extends ServletTest {
  public static final String LINE_END = "</br>";

  @Test
  public void getSingle() {
    addProduct(IPHONE_6);
    testProducts(Set.of(IPHONE_6));
  }

  @Test
  public void getMultiple() {
    addProducts(PRODUCT_LIST);
    testProducts(Set.copyOf(PRODUCT_LIST));
  }

  private void testProducts(final Set<Product> products) {
    assertEquals(getProducts(), products);
  }

  private Set<Product> getProducts() {
    Set<Product> products = new HashSet<>();
    for (var line : getLines(makeRequest("get-products"))) {
      assertTrue(line.endsWith(LINE_END));
      var items = line.substring(0, line.length() - LINE_END.length()).split("\t");
      assertEquals(items.length, 2);
      products.add(new Product(items[0], Integer.parseInt(items[1])));
    }
    return products;
  }
}