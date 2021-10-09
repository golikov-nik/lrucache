package ru.ifmo.rain.golikov.lrucache;

import java.util.*;

public class LinkedLRUCache<K, V> extends LRUCache<K, V> {
  private final Map<K, LRUEntryList<K, V>.LRUNode> data;
  private final LRUEntryList<K, V> entries;

  public LinkedLRUCache(final int capacity) {
    super(capacity);

    data = new HashMap<>(capacity);
    entries = new LRUEntryList<>();
  }

  @Override
  public void put(final K key, final V value) {
    removeKey(key);
    if (data.size() == capacity) {
      removeKey(entries.first());
    }

    data.put(key, entries.add(key, value));
  }

  private void removeKey(final K key) {
    var node = data.get(key);
    if (node == null) {
      return;
    }

    node.remove();
    data.remove(key);
  }

  @Override
  public Optional<V> get(final K key) {
    return Optional.ofNullable(data.get(key)).map(node -> {
      var value = node.getValue();
      removeKey(key);
      put(key, value);
      return value;
    });
  }
}
