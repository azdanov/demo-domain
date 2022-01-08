package dev.azdanov.demodomain.entities.employee.events;


import dev.azdanov.demodomain.entities.employee.Id;
import dev.azdanov.demodomain.entities.employee.Phone;

public final class EmployeePhoneRemoved extends EmployeeEvent {

  public final Phone phone;

  public EmployeePhoneRemoved(Id id, Phone phone) {
    super(id);
    this.phone = phone;
  }
}
