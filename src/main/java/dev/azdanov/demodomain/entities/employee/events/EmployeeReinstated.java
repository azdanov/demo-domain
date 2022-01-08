package dev.azdanov.demodomain.entities.employee.events;

import dev.azdanov.demodomain.entities.employee.Id;
import java.time.LocalDateTime;

public final class EmployeeReinstated extends EmployeeEvent {

  public final LocalDateTime reinstated;

  public EmployeeReinstated(Id id, LocalDateTime reinstated) {
    super(id);
    this.reinstated = reinstated;
  }
}
