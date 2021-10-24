package ru.akirakozov.sd.refactoring;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class GetProductServletTest extends ServletTest {

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
}
