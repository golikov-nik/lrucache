package ru.ifmo.rain.golikov.lrucache;

import org.apache.commons.lang.Validate;

class LRUEntryList<K, V> {
  LRUNode head = new LRUNode();
  LRUNode tail = head;
  int curSize = 0;

  K first() {
    Validate.isTrue(head != tail, "Empty list");

    return head.next.key;
  }

  LRUNode add(final K key, final V value) {
    tail = new LRUNode(key, value, tail, head);
    return tail;
  }

  int size() {
    assert curSize == calculateSize() : "Stored size differs from calculated";
    return curSize;
  }

  private int calculateSize() {
    int result = 0;
    LRUNode cur = head.next;
    while (cur != head) {
      result++;
      cur = cur.next;
    }
    return result;
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

      curSize++;
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

      curSize--;
      prev.next = next;
      next.prev = prev;
      next = prev = null;
    }

    private void checkAlive() {
      Validate.isTrue(prev.next == this, "Malformed node: prev.next != this");
      Validate.isTrue(next.prev == this, "Malformed node: next.prev != this");
      Validate.isTrue(this != head, "Node is fake");
      Validate.isTrue(curSize > 0, "Node is alive, but size=0");
    }
  }
}
