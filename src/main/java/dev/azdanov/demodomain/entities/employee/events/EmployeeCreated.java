package dev.azdanov.demodomain.entities.employee.events;

import dev.azdanov.demodomain.entities.employee.Id;
import java.time.LocalDateTime;

public final class EmployeeCreated extends EmployeeEvent {

  public final LocalDateTime created;

  public EmployeeCreated(Id id, LocalDateTime created) {
    super(id);
    this.created = created;
  }
}
