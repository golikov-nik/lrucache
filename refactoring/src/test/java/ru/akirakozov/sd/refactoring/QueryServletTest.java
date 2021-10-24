package ru.akirakozov.sd.refactoring;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryServletTest extends ServletTest {
  @Test
  public void testMin() {
    testMinMax("min", PRODUCT_LIST.get(0));
  }

  @Test
  public void testMax() {
    testMinMax("max", PRODUCT_LIST.get(2));
  }

  @Test
  public void testSum() {
    testSumCount("sum", "Summary price: ", 6);
  }

  @Test
  public void testCount() {
    testSumCount("count", "Number of products: ", 3);
  }

  private void testMinMax(final String type, final Product product) {
    String productString = getResponse(type, "<h1>Product with %s price: </h1>".formatted(type));
    assertEquals(readProduct(productString), product);
  }

  private void testSumCount(final String type, final String header, final int expected) {
    assertEquals(getResponse(type, header), String.valueOf(expected));
  }

  @Test
  public void testUnknownQuery() {
    assertEquals(makeQuery("lalala"), "Unknown command: lalala\n");
  }

  private String getResponse(final String command, final String header) {
    addProducts(PRODUCT_LIST);
    var response = getLines(makeQuery(command));
    assertEquals(response.size(), 2);
    assertEquals(response.get(0), header);
    return response.get(1);
  }
}
