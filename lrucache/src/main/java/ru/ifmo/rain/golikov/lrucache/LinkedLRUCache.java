package ru.ifmo.rain.golikov.lrucache;

import org.apache.commons.lang.Validate;

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
  public void putImpl(final K key, final V value) {
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
      putImpl(key, value);
      return value;
    });
  }

  @Override
  protected int sizeImpl() {
    Validate.isTrue(data.size() == entries.size(), "Map and list size differ");
    return data.size();
  }
}
