package ru.ifmo.rain.golikov.lrucache;

import org.apache.commons.lang.Validate;

class LRUEntryList<K, V> {
  LRUNode head = new LRUNode();
  LRUNode tail = head;

  K first() {
    Validate.isTrue(head != tail, "Empty list");

    return head.next.key;
  }

  LRUNode add(final K key, final V value) {
    return tail = new LRUNode(key, value, tail, head);
  }

  class LRUNode {
    final private K key;
    final private V value;
    private LRUNode prev, next;

    private LRUNode() {
      key = null;
      value = null;
      prev = this;
      next = this;
    }

    private LRUNode(final K key, final V value, final LRUNode prev, final LRUNode next) {
      this.key = key;
      this.value = value;
      prev.next = this;
      next.prev = this;
      this.prev = prev;
      this.next = next;

      checkAlive();
    }

    V getValue() {
      checkAlive();
      return value;
    }

    void remove() {
      checkAlive();

      if (tail == this) {
        tail = prev;
      }

      prev.next = next;
      next.prev = prev;
      next = prev = null;
    }

    private void checkAlive() {
      Validate.isTrue(prev.next == this, "Malformed node: prev.next != this");
      Validate.isTrue(next.prev == this, "Malformed node: next.prev != this");
      Validate.isTrue(this != head, "Node is fake");
    }
  }
}
