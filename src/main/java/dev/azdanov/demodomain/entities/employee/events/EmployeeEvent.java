package dev.azdanov.demodomain.entities.employee.events;

import dev.azdanov.demodomain.entities.employee.Id;

public abstract class EmployeeEvent {

  public final Id id;

  protected EmployeeEvent(Id id) {
    this.id = id;
  }

  public Id getId() {
    return id;
  }
}
