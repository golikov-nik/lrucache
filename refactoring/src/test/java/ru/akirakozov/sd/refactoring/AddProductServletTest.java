package ru.akirakozov.sd.refactoring;

import org.junit.Test;

public class AddProductServletTest extends ServletTest {
  @Test
  public void testSingleAdd() {
    addProduct(IPHONE_6);
  }

  @Test
  public void testMultipleAdd() {
    addProducts(PRODUCT_LIST);
  }
}
