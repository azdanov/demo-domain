package dev.azdanov.demodomain.entities.employee;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Status(StatusValue value, LocalDateTime date) implements Serializable {

  public boolean isActive() {
    return value.equals(StatusValue.ACTIVE);
  }

  public boolean isArchived() {
    return value.equals(StatusValue.ARCHIVED);
  }
}
