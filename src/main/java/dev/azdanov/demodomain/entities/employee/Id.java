package dev.azdanov.demodomain.entities.employee;

import java.io.Serializable;
import java.util.UUID;

public record Id(String id) implements Serializable {

  public static Id next() {
    return new Id(UUID.randomUUID().toString());
  }
}
