package ru.ifmo.rain.golikov.lrucache;

import org.apache.commons.lang.Validate;

import java.util.*;

public abstract class LRUCache<K, V> {
  protected final int capacity;

  protected LRUCache(final int capacity) {
    Validate.isTrue(capacity > 0, "Capacity must be positive");
    this.capacity = capacity;
  }

  //   Puts given key into the cache, associated with given value.
  public abstract void put(final K key, final V value);

  //  Returns value associated with given key.
  public abstract Optional<V> get(final K key);

  //  Returns whether this key is present in cache.
  public boolean containsKey(final K key) {
    return get(key).isPresent();
  }

  //  Returns the maximum number of keys this cache can store simultaneously.
  public int getCapacity() {
    return capacity;
  }
}
