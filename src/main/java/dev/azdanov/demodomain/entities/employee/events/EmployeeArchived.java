package dev.azdanov.demodomain.entities.employee.events;

import dev.azdanov.demodomain.entities.employee.Id;
import java.time.LocalDateTime;

public final class EmployeeArchived extends EmployeeEvent {

  public final LocalDateTime archived;

  public EmployeeArchived(Id id, LocalDateTime archived) {
    super(id);
    this.archived = archived;
  }
}
