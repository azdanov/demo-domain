package dev.azdanov.demodomain.entities.employee.events;

import dev.azdanov.demodomain.entities.employee.Id;
import dev.azdanov.demodomain.entities.employee.Name;

public final class EmployeeRenamed extends EmployeeEvent {

  public final Name name;

  public EmployeeRenamed(Id id, Name name) {
    super(id);
    this.name = name;
  }
}
