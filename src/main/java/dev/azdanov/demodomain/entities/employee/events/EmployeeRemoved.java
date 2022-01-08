package dev.azdanov.demodomain.entities.employee.events;

import dev.azdanov.demodomain.entities.employee.Id;

public final class EmployeeRemoved extends EmployeeEvent {

  public EmployeeRemoved(Id id) {
    super(id);
  }
}
