package dev.azdanov.demodomain.entities.employee;

import dev.azdanov.demodomain.entities.AggregateRoot;
import dev.azdanov.demodomain.entities.employee.events.EmployeeEvent;
import java.util.ArrayDeque;
import java.util.Deque;

public interface EmployeeAggregateRoot extends AggregateRoot<EmployeeEvent> {

  ArrayDeque<EmployeeEvent> events = new ArrayDeque<>();

  default void recordEvent(EmployeeEvent event) {
    events.add(event);
  }

  default Deque<EmployeeEvent> releaseEvents() {
    return events.clone();
  }
}
