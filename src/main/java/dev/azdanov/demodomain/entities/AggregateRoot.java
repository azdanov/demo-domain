package dev.azdanov.demodomain.entities;

import java.util.Deque;

public interface AggregateRoot<E> {

  void recordEvent(E event);

  Deque<E> releaseEvents();
}
