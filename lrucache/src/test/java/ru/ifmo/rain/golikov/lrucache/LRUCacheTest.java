package ru.ifmo.rain.golikov.lrucache;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.*;
 
public class LRUCacheTest {
  private static final int CAPACITY = 4;
  private static final int BIG = 5 * CAPACITY + 5;
  private LRUCache<Integer, String> cache;

  @Before
  public void before() {
    setCapacity(CAPACITY);
  }

  @Test
  public void testZeroCapacity() {
    testInvalidCapacity(0);
  }

  @Test
  public void testNegativeCapacity() {
    testInvalidCapacity(-1);
  }

  @Test
  public void testSinglePut() {
    assertMissing(1);
    putCache(1, 4);
  }

  @Test
  public void testGetCapacity() {
    checkCapacity();
    for (int i = 0; i < BIG; i++) {
      putCache(i, i);
      assertThat(cache.getCapacity()).isEqualTo(CAPACITY);
    }
  }

  @Test
  public void testGetMissing() {
    assertMissing(1);
  }

  @Test
  public void testOverflow() {
    for (int i = 0; i < CAPACITY; i++) {
      assertMissing(i);
      putCache(i, i);
    }
    putCache(CAPACITY, CAPACITY);
    assertMissing(0);
  }

  @Test
  public void testLRU() {
    setCapacity(3);
    putCache(0, 0);
    putCache(1, 1);
    putCache(2, 2);
    assertValueEquals(0, 0);
    putCache(3, 3);
    assertValueEquals(2, 2);
    assertValueEquals(0, 0);
    assertValueEquals(3, 3);
    putCache(4, 4);
    assertMissing(2);
  }

  private void putCache(int key, int value) {
    cache.put(key, Integer.toString(value));
    assertValueEquals(key, value);
  }

  private void testInvalidCapacity(final int capacity) {
    try {
      setCapacity(capacity);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (final IllegalArgumentException e) {
      assertThat(e).hasMessage("Capacity must be positive");
    }
  }

  private void setCapacity(final int capacity) {
    cache = new LinkedLRUCache<>(capacity);
  }

  private void checkCapacity() {
    assertThat(cache.getCapacity()).isEqualTo(CAPACITY);
  }

  private void assertMissing(final int key) {
    assertFalse(cache.containsKey(key));
    assertThat(cache.get(key)).isEmpty();
  }

  private void assertValueEquals(final int key, final int value) {
    assertThat(cache.get(key)).hasValue(Integer.toString(value));
    assertTrue(cache.containsKey(key));
  }
}
